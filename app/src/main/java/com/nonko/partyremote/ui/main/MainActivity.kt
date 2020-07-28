package com.nonko.partyremote.ui.main

import android.os.Bundle
import com.nonko.partyremote.R
import com.nonko.partyremote.ui.base.ToolbarActivity

class MainActivity : ToolbarActivity() {
    override fun getActivityLayoutId(): Int? {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}