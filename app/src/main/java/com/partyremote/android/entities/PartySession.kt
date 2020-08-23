package com.partyremote.android.entities

import java.util.*

data class PartySession(
    val id: UUID?,
    val title: String?,
    val author: String?,
    val currentSong: String?,
    val ownerPassword: String?,
    val queuePassword: String?,
    val songsCount: Int?
)