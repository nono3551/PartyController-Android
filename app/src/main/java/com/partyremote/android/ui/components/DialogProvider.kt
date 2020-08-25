package com.partyremote.android.ui.components

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.partyremote.android.R
import sk.backbone.parent.execution.IExecutorDialogProvider

class DialogProvider: IExecutorDialogProvider {
    fun showYesNoDialog(context: Context, title: String, message: String, onYesClicked: (() -> Unit)? = null, onNoClicked: (() -> Unit)? = null, cancellable: Boolean = false): AlertDialog {
        context.apply {
            val builder = AlertDialog.Builder(this)
            builder.setCancelable(cancellable)
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setPositiveButton(R.string.app_name){ _, _ ->
                onYesClicked?.invoke()
            }
            builder.setNegativeButton(R.string.app_name){ _, _ ->
                onNoClicked?.invoke()
            }

            return builder.create().apply {
                show()
            }
        }
    }

    override fun showErrorDialog(context: Context, message: String, neutralButtonAction: (() -> Unit)?) {
        context.apply {
            val builder = AlertDialog.Builder(this)
            builder.setCancelable(true)
            builder.setTitle(getString(R.string.app_name))
            builder.setMessage(message)
            builder.setNegativeButton(R.string.app_name){ _, _ ->
                neutralButtonAction?.invoke()
            }

            builder.create().apply {
                show()
            }
        }
    }

    override fun showLoadingDialog(context: Context): AlertDialog? {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setCancelable(false)

        builder.setView(R.layout.ui_common_progress_dialog)
        return builder.create().apply {
            show()
        }
    }
}