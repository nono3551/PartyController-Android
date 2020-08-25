package com.partyremote.android.execution_handling

import android.content.Context
import com.partyremote.android.R
import com.partyremote.android.repositories.server.client.ApiErrorWrapper
import sk.backbone.parent.repositories.server.client.exceptions.*
import sk.backbone.parent.utils.jsonToObject

class ExceptionDescriptionProvider: IExceptionDescriptionProvider {
    override fun getDefaultErrorMessage(context: Context, exception: Throwable): String {
        return context.getString(R.string.exception_unexpected_error)
    }

    override fun parseBadRequestException(
        context: Context,
        exception: BadRequestException,
        responseBody: String?,
        statusCode: Int?
    ): String? {
        return responseBody?.jsonToObject<ApiErrorWrapper>()?.error
    }


    override fun parseAuthorizationException(
        context: Context,
        exception: AuthorizationException,
        responseBody: String?,
        statusCode: Int?
    ): String? {
        return responseBody?.jsonToObject<ApiErrorWrapper>()?.error
    }

    override fun parsePaymentException(
        context: Context,
        exception: PaymentException,
        responseBody: String?,
        statusCode: Int?
    ): String? {
        return (responseBody?.jsonToObject<ApiErrorWrapper>())?.error
    }

    override fun parseForbiddenException(
        context: Context,
        exception: ForbiddenException,
        responseBody: String?,
        statusCode: Int?
    ): String? {
        return responseBody?.jsonToObject<ApiErrorWrapper>()?.error
    }

    override fun parseNotFoundException(
        context: Context,
        exception: NotFoundException,
        responseBody: String?,
        statusCode: Int?
    ): String? {
        return responseBody?.jsonToObject<ApiErrorWrapper>()?.error
    }

    override fun parseConflictException(
        context: Context,
        exception: ConflictException,
        responseBody: String?,
        statusCode: Int?
    ): String? {
        return responseBody?.jsonToObject<ApiErrorWrapper>()?.error
    }

    override fun parseValidationException(
        context: Context,
        exception: ValidationException,
        responseBody: String?,
        statusCode: Int?
    ): String? {
        return (responseBody?.jsonToObject<ApiErrorWrapper>())?.error
    }

    override fun parseServerException(
        context: Context,
        exception: ServerException,
        responseBody: String?,
        statusCode: Int?
    ): String? {
        return responseBody?.jsonToObject<ApiErrorWrapper>()?.error
    }

    override fun parseCommunicationException(
        context: Context,
        exception: CommunicationException,
        responseBody: String?,
        statusCode: Int?
    ): String? {
        return context.getString(R.string.exception_no_connection)
    }
}