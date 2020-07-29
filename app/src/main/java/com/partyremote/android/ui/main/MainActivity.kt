package com.partyremote.android.ui.main

import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import com.partyremote.android.R
import com.partyremote.android.ui.base.ToolbarActivity
import com.partyremote.android.ui.create_party_session.CreatePartySessionActivity
import sk.backbone.android.shared.utils.setSafeOnClickListener

class MainActivity : ToolbarActivity() {
    override fun getCenterView(): View? {
        return ImageView(this).apply {
            layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            setImageResource(android.R.drawable.ic_input_add)
            setSafeOnClickListener {
                CreatePartySessionActivity.startActivity(this@MainActivity)
            }
        }
    }

    override fun getActivityLayoutId(): Int? {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}