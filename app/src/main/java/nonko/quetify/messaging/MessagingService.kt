package nonko.quetify.messaging

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.experimental.launch
import nonko.quetify.Utils
import nonko.quetify.entities.Party
import nonko.quetify.entities.QueueRequest
import org.json.JSONException
import org.json.JSONObject
import kotlin.coroutines.experimental.suspendCoroutine

class MessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        Log.v("INFO", "Message received and processing")

        val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            //deprecated in API 26
            v.vibrate(500)
        }

        val remoteData = remoteMessage?.data
        if (remoteData != null && !remoteData.isEmpty()) {
            val operation = Gson().fromJson<String>(remoteData[operationKey], String::class.java)

            if(queueSongOperationKey == operation) {
                val request = Gson().fromJson<QueueRequest>(remoteData[queueRequestKey], QueueRequest::class.java)
                Party.processQueueRequest(request, applicationContext)
            }
        }
    }

    override fun onNewToken(token: String?) {
        super.onNewToken(token)
        MessagingToken = token
        launch {
            Party.getLastHostedParty(applicationContext)?.messagingToken = token
        }
    }

    companion object {
        private const val queueRequestKey = "QueueRequest"
        private const val queueSongOperationKey = "QueueSong"
        private const val operationKey = "Operation"

        var MessagingToken: String? = null

        suspend fun initializeFirebaseToken() {
            return suspendCoroutine { continuation ->
                FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener { instanceIdResult ->
                    MessagingToken = instanceIdResult.token
                    continuation.resume(Unit)
                }
            }
        }

        fun send(queueRequest: QueueRequest, context: Context) {
            val queue = Volley.newRequestQueue(context)
            val url = "https://us-central1-quetify.cloudfunctions.net/sendQueueRequest"

            val gson = GsonBuilder().create()
            val json = gson.toJson(queueRequest)
            var jsonObj: JSONObject? = null

            try {
                jsonObj = JSONObject(json)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, url, jsonObj, Response.Listener { }, Response.ErrorListener {
                // Todo: Handle error
            })

            queue.add(jsonObjectRequest)
        }

        fun unregister() {
            Thread(Runnable {
                FirebaseMessaging.getInstance().isAutoInitEnabled = false
                FirebaseInstanceId.getInstance().deleteInstanceId()
            }).start()
        }
    }
}
