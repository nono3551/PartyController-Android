package com.partyremote.android.models

import com.partyremote.android.entities.PartySession
import com.partyremote.android.repositories.server.PartySessionRepository
import sk.backbone.android.shared.models.IModel
import java.util.*

interface IPartySessionModel : IModel<ModelsProvider> {
    val serverRunnable get() = PartySessionRepository(context)

    suspend fun getPartySession(id: UUID): PartySession? {
        return serverRunnable.getPartySession(id)
    }

    suspend fun getDummyPartySession(): PartySession? {
        return serverRunnable.getDummyPartySession()
    }
}