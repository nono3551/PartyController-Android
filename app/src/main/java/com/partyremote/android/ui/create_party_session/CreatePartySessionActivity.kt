package com.partyremote.android.ui.create_party_session

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import com.partyremote.android.R
import com.partyremote.android.ui.base.ToolbarActivity

class CreatePartySessionActivity : ToolbarActivity() {

    override fun getCenterView(): View? {
        return TextView(this).apply {
            text = "New party session"
            setTextColor(getColor(android.R.color.white))
        }
    }

    override fun getActivityLayoutId(): Int? {
        return R.layout.activity_create_party_session
    }

    companion object {
        fun startActivity(context: Context){
            context.startActivity(Intent(context, CreatePartySessionActivity::class.java))
        }
    }
}