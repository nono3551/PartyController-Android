package com.partyremote.android.models

import com.partyremote.android.entities.PartySession
import com.partyremote.android.repositories.server.PartySessionRepository
import sk.backbone.android.shared.models.IModel

interface IPartySessionModel : IModel<ModelsProvider> {
    val serverRunnable get() = PartySessionRepository(context)

    suspend fun getDummyPartySession(): PartySession? {
        return serverRunnable.getDummyPartySession()
    }

    suspend fun joinPartySession(id: String?, queuePassword: String?): PartySession? {
        return serverRunnable.joinPartySession(id, queuePassword)

    }
}