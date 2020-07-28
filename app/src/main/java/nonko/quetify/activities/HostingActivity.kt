package nonko.quetify.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_host.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import nonko.quetify.R
import nonko.quetify.Utils
import nonko.quetify.entities.Party
import nonko.quetify.messaging.MessagingService

class HostingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)

        launch(UI) {
            MessagingService.initializeFirebaseToken()

            val party = Party.getLastHostedParty(applicationContext)
            if (party != null) {

                hosted_session_name_edit_text.setText(party.name)
                hosted_session_secret_edit_text.setText(party.secret)

                Utils.initializeSpotifyRemote(applicationContext, suspend { onInitializationFailure() })

                Toast.makeText(applicationContext, "Hosting was started automatically.", Toast.LENGTH_LONG).show()
            }
        }

        update_hosting_session_button.setOnClickListener{
            launch (UI){
                FirebaseMessaging.getInstance().isAutoInitEnabled = true
                val party = Party.getLastHostedParty(applicationContext)
                party?.name = hosted_session_name_edit_text.text.toString()
                party?.updateSecret(applicationContext, hosted_session_secret_edit_text.text.toString())
            }
        }

        initialize_hosting_session_button.setOnClickListener {
            launch(UI) {
                MessagingService.initializeFirebaseToken()

                Party.createNewParty(hosted_session_name_edit_text.text.toString(), hosted_session_secret_edit_text.text.toString(), applicationContext)
                Utils.initializeSpotifyRemote(applicationContext, suspend { onInitializationFailure() })
            }
        }
    }

    private suspend fun onInitializationFailure(){
        withContext(UI) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this@HostingActivity, android.R.style.Theme_Material_Dialog_Alert)
            builder.setTitle("Error")
                    .setMessage("Could not comunicate with local Spotify services. Check if Spotify is running or you are using correct application. Do you wan to try again?")
                    .setPositiveButton("Yes") { _, _ -> launch(UI) { Utils.initializeSpotifyRemote(applicationContext, suspend { onInitializationFailure() }) } }
                    .setOnCancelListener { finish() }.setIcon(android.R.drawable.ic_dialog_alert)
                    .setCancelable(true)
                    .show()
        }
    }

    companion object {
        fun startActivity(context: Context){
            context.startActivity(Intent(context, HostingActivity::class.java))
        }
    }
}