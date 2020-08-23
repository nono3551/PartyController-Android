package com.partyremote.android.ui.create_party_session

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import com.partyremote.android.R
import com.partyremote.android.entities.PartySession
import com.partyremote.android.execution_handling.Executor
import com.partyremote.android.ui.base.ToolbarActivity
import sk.backbone.android.shared.utils.setSafeOnClickListener

class CreatePartySessionActivity : ToolbarActivity() {

    override fun getCenterView(): View? {
        return TextView(this).apply {
            text = "CREATE SESSION"
            setTextColor(getColor(android.R.color.white))
            setSafeOnClickListener {

            }
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