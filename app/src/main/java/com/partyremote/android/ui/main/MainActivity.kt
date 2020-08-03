package com.partyremote.android.ui.main

import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import com.partyremote.android.R
import com.partyremote.android.ui.base.ToolbarActivity
import com.partyremote.android.ui.create_party_session.CreatePartySessionActivity
import kotlinx.android.synthetic.main.activity_main.*
import sk.backbone.android.shared.utils.setSafeOnClickListener
import java.util.zip.Inflater

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



        layoutInflater.inflate(R.layout.ui_party_session_view, test).apply { this.layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)}
        layoutInflater.inflate(R.layout.ui_party_session_view, test).apply { this.layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)}
        layoutInflater.inflate(R.layout.ui_party_session_view, test).apply { this.layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)}
        layoutInflater.inflate(R.layout.ui_party_session_view, test).apply { this.layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)}
        layoutInflater.inflate(R.layout.ui_party_session_view, test).apply { this.layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)}
    }
}