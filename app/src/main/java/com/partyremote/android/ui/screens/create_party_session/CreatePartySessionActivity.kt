package com.partyremote.android.ui.screens.create_party_session

import android.content.Context
import android.content.Intent
import android.view.View
import com.partyremote.android.R
import com.partyremote.android.ui.base.ToolbarActivity

class CreatePartySessionActivity : ToolbarActivity() {

    override fun getCenterView(): View? {
        return createToolbarText(getString(R.string.create_party_session_create_session))
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