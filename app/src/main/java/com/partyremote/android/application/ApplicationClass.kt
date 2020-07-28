package com.partyremote.android.application

import android.app.Application
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging

class ApplicationClass : Application(){
    override fun onCreate() {
        super.onCreate()

        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("Application", "Could not get firebase token", task.exception)
                return@OnCompleteListener
            }
        })

        FirebaseMessaging.getInstance().isAutoInitEnabled = true
    }
}