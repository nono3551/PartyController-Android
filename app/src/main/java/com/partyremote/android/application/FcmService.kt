package com.partyremote.android.application

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.partyremote.android.R
import com.partyremote.android.ui.main.MainActivity
import sk.backbone.android.shared.execution.Scopes

class FcmService : FirebaseMessagingService(), BaseService {
    private val scopes = Scopes()
    override val context: Context get() = this

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onDestroy() {
        scopes.cancelJobs()
        super.onDestroy()
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val channel = "Default"
        val manager = NotificationManagerCompat.from(applicationContext)
        val vibrationPattern = longArrayOf(200, 200)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(channel, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT)
            notificationChannel.description = "Channel description"
            notificationChannel.enableLights(true)
            notificationChannel.vibrationPattern = vibrationPattern
            notificationChannel.enableVibration(true)
            manager.createNotificationChannel(notificationChannel)
        }

        val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(Intent(this@FcmService, MainActivity::class.java))
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val notification: Notification = NotificationCompat.Builder(this, channel).apply {
            setContentTitle("Song added")
            setContentText("User <> added song to queue")
            setSmallIcon(R.drawable.ic_launcher_foreground)
            setVibrate(vibrationPattern)
            setContentIntent(resultPendingIntent)
        }.build()

        manager.notify(remoteMessage.messageId.hashCode(), notification)
    }
}