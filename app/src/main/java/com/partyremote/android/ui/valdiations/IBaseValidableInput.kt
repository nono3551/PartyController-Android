package com.partyremote.android.ui.valdiations

import android.view.ViewGroup
import com.partyremote.android.ui.components.CommonErrorText
import sk.backbone.parent.ui.validations.IValidableInput
import sk.backbone.parent.ui.validations.ValidationError

interface IBaseValidableInput<ValueType> : IValidableInput<ValueType> {
    fun getValidationsViewHolder(): ViewGroup?

    override fun onValid() {
        getValidationsViewHolder()?.removeAllViews()
    }

    override fun onInvalid(errors: List<ValidationError>) {
        val messages = errors.map(::parseErrorMessage)
        setErrors(messages)
    }

    fun setErrors(errors: List<String>) {
        val errorsHolder = getValidationsViewHolder()

        errorsHolder?.removeAllViews()
        for (error in errors){
            getContext()?.let { context -> errorsHolder?.addView(CommonErrorText(context).apply { this.value = error }) }
        }
    }

    private fun parseErrorMessage(error: ValidationError): String{
        return when(error){
            ValidationError.CAN_NOT_BE_EMPTY -> "1"
            ValidationError.MUST_BE_CHECKED -> "2"
            ValidationError.MUST_BE_GREATER_THAN_ONE -> "3"
            ValidationError.MUST_BE_NUMERIC -> "4"
            ValidationError.INVALID_EMAIL_FORMAT -> "5"
        }
    }
}