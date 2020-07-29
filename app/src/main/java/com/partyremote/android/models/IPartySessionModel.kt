package com.partyremote.android.models

import com.partyremote.android.repositories.server.PartySessionRepository
import sk.backbone.android.shared.models.IModel

interface IPartySessionModel : IModel<ModelsProvider> {
    val serverRunnable get() = PartySessionRepository(context)

    suspend fun getParty(): Any? {
        return serverRunnable.getPartySession()
    }
}