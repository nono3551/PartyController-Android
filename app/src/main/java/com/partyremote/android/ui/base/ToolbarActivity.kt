package com.partyremote.android.ui.base

import android.view.View
import com.partyremote.android.R
import kotlinx.android.synthetic.main.ui_toolbar.*
import kotlinx.android.synthetic.main.ui_toolbar.view.*
import sk.backbone.android.shared.ui.screens.BaseSharedToolbarActivity

abstract class ToolbarActivity : BaseSharedToolbarActivity(){
    open fun getLeftView() : View? = null
    open fun getCenterView() : View? = null
    open fun getRightView() : View? = null

    override fun getToolbarLayoutId(): Int {
        return R.layout.ui_toolbar
    }

    override fun getToolbarViewId(): Int {
        return R.id.common_toolbar
    }

    override fun refreshToolbar(){
        common_toolbar.toolbar_left_placeholder.removeAllViews()
        common_toolbar.toolbar_center_placeholder.removeAllViews()
        common_toolbar.toolbar_right_placeholder.removeAllViews()

        getLeftView()?.let { common_toolbar.toolbar_left_placeholder.addView(it) }
        getCenterView()?.let { common_toolbar.toolbar_center_placeholder.addView(it) }
        getRightView()?.let { common_toolbar.toolbar_right_placeholder.addView(it) }
    }
}