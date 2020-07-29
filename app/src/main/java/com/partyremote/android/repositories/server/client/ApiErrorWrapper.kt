package com.partyremote.android.repositories.server.client

import com.google.gson.annotations.SerializedName

data class ApiErrorWrapper(
    @SerializedName("errors")
    val error: String?
)