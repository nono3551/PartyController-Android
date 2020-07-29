package com.partyremote.android.execution_handling

import android.content.Context
import com.partyremote.android.R
import com.partyremote.android.repositories.server.client.ApiErrorWrapper
import sk.backbone.android.shared.repositories.server.client.exceptions.*
import sk.backbone.android.shared.utils.jsonToObject

class ExceptionDescriptionProvider: IExceptionDescriptionProvider {
    override fun getDefaultErrorMessage(context: Context, throwable: Throwable): String {
        return context.getString(R.string.exception_unexpected_error)
    }

    override fun parseBadRequestException(
        context: Context,
        baseHttpException: BaseHttpException,
        responseBody: String?,
        statusCode: Int?
    ): String? {
        return responseBody?.jsonToObject<ApiErrorWrapper>()?.error
    }


    override fun parseAuthorizationException(
        context: Context,
        baseHttpException: BaseHttpException,
        responseBody: String?,
        statusCode: Int?
    ): String? {
        return responseBody?.jsonToObject<ApiErrorWrapper>()?.error
    }

    override fun parsePaymentException(
        context: Context,
        baseHttpException: BaseHttpException,
        responseBody: String?,
        statusCode: Int?
    ): String? {
        return (responseBody?.jsonToObject<ApiErrorWrapper>())?.error
    }

    override fun parseForbiddenException(
        context: Context,
        baseHttpException: BaseHttpException,
        responseBody: String?,
        statusCode: Int?
    ): String? {
        return responseBody?.jsonToObject<ApiErrorWrapper>()?.error
    }

    override fun parseNotFoundException(
        context: Context,
        baseHttpException: BaseHttpException,
        responseBody: String?,
        statusCode: Int?
    ): String? {
        return responseBody?.jsonToObject<ApiErrorWrapper>()?.error
    }

    override fun parseConflictException(
        context: Context,
        throwable: ConflictException,
        responseBody: String?,
        statusCode: Int?
    ): String? {
        return responseBody?.jsonToObject<ApiErrorWrapper>()?.error
    }

    override fun parseValidationException(
        context: Context,
        baseHttpException: BaseHttpException,
        responseBody: String?,
        statusCode: Int?
    ): String? {
        return (responseBody?.jsonToObject<ApiErrorWrapper>())?.error
    }

    override fun parseServerException(
        context: Context,
        baseHttpException: BaseHttpException,
        responseBody: String?,
        statusCode: Int?
    ): String? {
        return responseBody?.jsonToObject<ApiErrorWrapper>()?.error
    }

    override fun parseCommunicationException(
        context: Context,
        baseHttpException: BaseHttpException,
        responseBody: String?,
        statusCode: Int?
    ): String? {
        return context.getString(R.string.exception_no_connection)
    }
}