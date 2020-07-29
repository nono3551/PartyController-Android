package com.partyremote.android.repositories.server

import android.content.Context
import com.android.volley.Request

class PartySessionRepository(context: Context) : ServerRepository(context) {
    suspend fun getPartySession(): Any? {
        return buildAndExecuteRequest(Request.Method.GET, null, "partysession")
    }
}