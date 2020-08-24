package com.partyremote.android.ui.components

import android.content.Context
import android.content.res.ColorStateList
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.TextView
import com.partyremote.android.R
import com.partyremote.android.ui.valdiations.IBaseValidableInput
import kotlinx.android.synthetic.main.ui_common_text_input.view.*
import sk.backbone.android.shared.ui.components.StateSavingLinearLayout
import sk.backbone.android.shared.ui.validations.IValidator
import sk.backbone.android.shared.ui.validations.ValidationError
import sk.backbone.android.shared.ui.validations.text_validation.TextInputValidation

open class CommonTextInput : StateSavingLinearLayout, IBaseValidableInput<String> {
    private var originalBackgroundTint : ColorStateList? = null

    private var onValueChanged: ((String) -> Unit)? = null
    private var inputValidation = TextInputValidation.NONE

    var text: String?
        get() = this.input_edit_text.text.toString()
        set(value) {
            this.input_edit_text.text = Editable.Factory.getInstance().newEditable(value ?: "")
        }

    constructor(context: Context) : super(context){
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        inflate(context, R.layout.ui_common_text_input, this)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.CommonTextInput)

        val validation = attributes.getInt(R.styleable.CommonTextInput_textValidation, 0)

        inputValidation = TextInputValidation.getEnumValueFromString(validation)!!

        header_text_view.text = attributes.getString(R.styleable.CommonTextInput_android_text)
        input_edit_text.hint = attributes.getString(R.styleable.CommonTextInput_android_hint)
        input_edit_text.inputType = attributes.getInt(R.styleable.CommonTextInput_android_inputType, InputType.TYPE_CLASS_TEXT)
        isEnabled = attributes.getBoolean(R.styleable.CommonTextInput_cti_enabled, true)

        val maxLength = attributes.getInt(R.styleable.CommonTextInput_android_maxLength, 0)

        if(maxLength > 0){
            input_edit_text.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
        }

        input_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(text: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                charSequence.let { editable -> onValueChanged?.let { function -> function(editable.toString()) } }
            }
        })

        if(!isEnabled){
            input_edit_text.isEnabled = false
            input_edit_text.setTextColor(context.getColor(R.color.app_green))
        }

        originalBackgroundTint = input_edit_text.backgroundTintList

        attributes.recycle()
    }

    fun setOnValueChangedListener(onValueChanged: (String) -> Unit) {
        this.onValueChanged = onValueChanged
    }

    override fun getValidator(): IValidator<String>? {
        return inputValidation.validator
    }

    override fun onValid() {
        super.onValid()
        context?.getColor(android.R.color.white)?.let { header_text_view.setTextColor(it) }
        input_edit_text.backgroundTintList = context?.getColorStateList(R.color.common_text_input_normal)
    }

    override fun getValidationsViewHolder(): ViewGroup {
        return validations_errors_holder
    }

    override fun getInputValue(): String? = text

    fun setOnEditorActionListener(listener: TextView.OnEditorActionListener?) {
        input_edit_text.setOnEditorActionListener(listener)
    }

    override fun onInvalid(errors: List<ValidationError>) {
        super.onInvalid(errors)

        context?.getColor(android.R.color.holo_red_dark)?.let { header_text_view.setTextColor(it) }
        input_edit_text.backgroundTintList = context?.getColorStateList(R.color.common_text_input_error)
    }
}