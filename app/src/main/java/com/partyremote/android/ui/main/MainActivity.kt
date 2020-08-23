package com.partyremote.android.ui.main

import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.partyremote.android.R
import com.partyremote.android.ui.base.ToolbarActivity
import com.partyremote.android.ui.create_party_session.CreatePartySessionActivity
import kotlinx.android.synthetic.main.activity_main.*
import sk.backbone.android.shared.utils.convertDensityPointsToPixels
import sk.backbone.android.shared.utils.setSafeOnClickListener

class MainActivity : ToolbarActivity() {
    override fun getRightView(): View? {
        return ImageView(this).apply {
            layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)

            val padding = 5.convertDensityPointsToPixels(this@MainActivity)

            setPadding(padding, padding, padding, padding)

            setImageResource(R.drawable.ic_baseline_add)
            setSafeOnClickListener {
                CreatePartySessionActivity.startActivity(this@MainActivity)
            }
        }
    }

    override fun getCenterView(): View? {
        return TextView(this).apply {
            text = "RECENT SESSIONS"
            setTextColor(getColor(android.R.color.white))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 19f)
            setTypeface(typeface, Typeface.BOLD)

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