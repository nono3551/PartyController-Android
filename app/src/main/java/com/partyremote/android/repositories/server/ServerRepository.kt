package com.partyremote.android.repositories.server

import android.content.Context
import com.android.volley.toolbox.JsonRequest
import com.google.gson.ExclusionStrategy
import com.partyremote.android.BuildConfig
import sk.backbone.android.shared.repositories.server.BaseServerRepository
import sk.backbone.android.shared.repositories.server.client.JsonHttpRequest
import sk.backbone.android.shared.repositories.server.client.ITokensProvider

abstract class ServerRepository(context: Context): BaseServerRepository<Any?>(context, object : ITokensProvider<Any?>{ override fun getLocalTokens(): Any? { return null } }) {
    override val additionalHeadersProvider: (JsonRequest<*>) -> Map<String, String>
        get() = { _ ->
            val headers: MutableMap<String, String> = mutableMapOf()
            headers
        }

    protected suspend inline fun <reified Type>buildAndExecuteRequest(requestMethod: Int, body: Any?, endpoint: String, queryParameters: Map<String, String?>? = null, bodyExclusionStrategy: ExclusionStrategy? = null): Type? {
        return executeRequest { continuation ->
            JsonHttpRequest(
                continuation,
                requestMethod,
                schema,
                serverAddress,
                apiVersion,
                endpoint,
                queryParameters,
                body,
                { jsonObject -> parseResponse<Type>(jsonObject) },
                bodyExclusionStrategy,
                additionalHeadersProvider
            )
        }
    }

    companion object {
        const val serverAddress = BuildConfig.API_URL
        const val apiVersion = BuildConfig.API_VERSION
        const val schema = BuildConfig.API_SCHEMA
    }
}