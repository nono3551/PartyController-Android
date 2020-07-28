package com.nonko.partyremote.repositories.server.client

import sk.backbone.android.shared.repositories.server.client.IHttpResponseWrapper

data class HttpResponse<Type>(val response: Type?, val items: Type?): IHttpResponseWrapper<Type> {
    override fun getResult(): Type? {
        return response ?: items
    }
}