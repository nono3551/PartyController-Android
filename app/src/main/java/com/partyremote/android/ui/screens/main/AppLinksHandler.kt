package com.partyremote.android.ui.screens.main

import android.content.Intent
import android.net.Uri

class AppLinksHandler {
    private val mapping = mapOf<Regex, ((Uri, MainActivity) -> Unit)>(
        "partyremoteapp\\.azurewebsites(\\.net|:/)/join/?\\b".toRegex() to ::handleJoinSession
    )

    private fun handleJoinSession(uri: Uri, activity: MainActivity){
        val id = uri.getQueryParameter("id")
        val queuePassword = uri.getQueryParameter("queuePassword")

        activity.joinPartySession(id, queuePassword)
    }

    fun handleIntent(intent: Intent?, activity: MainActivity){
        intent?.data?.let { link ->
            val linkLowerCase = link.toString().toLowerCase()
            for (entry in mapping){
                if(entry.key.containsMatchIn(linkLowerCase)){
                    entry.value(link, activity)
                    break
                }
            }
        }
    }
}