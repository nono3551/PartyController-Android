package com.partyremote.android.ui.components

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import com.partyremote.android.R
import kotlinx.android.synthetic.main.ui_common_error_text.view.*
import sk.backbone.android.shared.ui.components.StateSavingLinearLayout

class CommonErrorText : StateSavingLinearLayout {
    var value: String?
        get() = this.ui_common_error_text_text_view.text.toString()
        set(value) {
            this.ui_common_error_text_text_view.text = Editable.Factory.getInstance().newEditable(value ?: "")
        }

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        inflate(context, R.layout.ui_common_error_text, this)
    }
}