package com.partyremote.android.repositories.server

import android.content.Context
import com.android.volley.Request
import com.partyremote.android.entities.PartySession
import java.util.*

class PartySessionRepository(context: Context) : ServerRepository(context) {
    suspend fun joinPartySession(sessionId: String?, queuePassword: String?): PartySession? {
        return buildAndExecuteRequest(Request.Method.GET, null, "partysession/${sessionId}", mutableMapOf("queuePassword" to queuePassword))
    }

    suspend fun getDummyPartySession(): PartySession?{
        return buildAndExecuteRequest(Request.Method.GET, null, "partysession")
    }
}