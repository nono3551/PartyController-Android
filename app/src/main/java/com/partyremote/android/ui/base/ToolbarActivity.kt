package com.partyremote.android.ui.base

import android.graphics.Typeface
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import com.partyremote.android.R
import kotlinx.android.synthetic.main.ui_toolbar.*
import kotlinx.android.synthetic.main.ui_toolbar.view.*
import sk.backbone.parent.ui.screens.IToolbarActivity
import sk.backbone.parent.ui.screens.ParentActivity

abstract class ToolbarActivity : ParentActivity(), IToolbarActivity {
    open fun getLeftView() : View? = null
    open fun getCenterView() : View? = null
    open fun getRightView() : View? = null

    override fun getToolbarLayoutId(): Int {
        return R.layout.ui_toolbar
    }

    override fun getToolbarViewId(): Int {
        return R.id.common_toolbar
    }

    override fun setupToolbar(){
        common_toolbar.toolbar_left_placeholder.removeAllViews()
        common_toolbar.toolbar_center_placeholder.removeAllViews()
        common_toolbar.toolbar_right_placeholder.removeAllViews()

        getLeftView()?.let { common_toolbar.toolbar_left_placeholder.addView(it) }
        getCenterView()?.let { common_toolbar.toolbar_center_placeholder.addView(it) }
        getRightView()?.let { common_toolbar.toolbar_right_placeholder.addView(it) }
    }

    protected fun createToolbarText(value: String): TextView {
        return TextView(this).apply {
            text = value
            setTextColor(getColor(android.R.color.white))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            setTypeface(typeface, Typeface.BOLD)
        }
    }
}