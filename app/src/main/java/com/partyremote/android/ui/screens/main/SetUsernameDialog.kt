package com.partyremote.android.ui.screens.main

import android.app.AlertDialog
import android.content.Context
import android.widget.LinearLayout
import android.widget.Toast
import com.partyremote.android.R
import com.partyremote.android.entities.Settings
import kotlinx.android.synthetic.main.dialog_set_username.view.*
import sk.backbone.android.shared.utils.setSafeOnClickListener

class SetUsernameDialog(private val activity: MainActivity) : AlertDialog(activity) {
    init {
        init(context)
    }

    private fun init(context: Context) {
        val view = LinearLayout.inflate(context, R.layout.dialog_set_username, null)

        view.dialog_set_username_button.setSafeOnClickListener {
            val username = view.dialog_set_username_edit_text?.text

            if(!username.isNullOrEmpty()){
                activity.viewModel.setSettings(Settings((username.toString()), true))
                this@SetUsernameDialog.dismiss()
            }
            else {
                Toast.makeText(context, context.getString(R.string.error_empty_username), Toast.LENGTH_LONG).show()
            }

        }

        setView(view)
    }
}