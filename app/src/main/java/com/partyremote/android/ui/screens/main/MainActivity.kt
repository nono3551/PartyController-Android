package com.partyremote.android.ui.screens.main

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.graphics.drawable.toDrawable
import com.partyremote.android.R
import com.partyremote.android.entities.PartySession
import com.partyremote.android.execution_handling.Executor
import com.partyremote.android.ui.base.ToolbarActivity
import com.partyremote.android.ui.components.PartySessionView
import com.partyremote.android.ui.screens.create_party_session.CreatePartySessionActivity
import kotlinx.android.synthetic.main.activity_main.*
import sk.backbone.android.shared.utils.setSafeOnClickListener


class MainActivity : ToolbarActivity() {
    val viewModel by lazy {
        MainActivityViewModel.create(this)
    }

    override fun getRightView(): View? {
        return ImageView(this).apply {
            layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            setImageResource(R.drawable.ic_baseline_add)
            setSafeOnClickListener {
                CreatePartySessionActivity.startActivity(this@MainActivity)
            }
        }
    }

    override fun getCenterView(): View? {
        return createToolbarText(getString(R.string.main_activity_recent_sessions))
    }

    override fun getActivityLayoutId(): Int? {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(!viewModel.isUsernameSet()){
            SetUsernameDialog(this).show()
        }

        withExecutorParams {
            Executor<PartySession?>(it).apply {
                ioOperation = {
                    viewModel.getDummyPartySession()
                }
                uiOperationOnSuccess = {
                    main_activity_sessions_holder.addView(PartySessionView(this@MainActivity).apply {
                        it?.let { partySession ->
                            bindData(
                                partySession
                            )
                        }
                    })
                    main_activity_sessions_holder.addView(PartySessionView(this@MainActivity).apply {
                        it?.let { partySession ->
                            bindData(
                                partySession
                            )
                        }
                    })
                    main_activity_sessions_holder.addView(PartySessionView(this@MainActivity).apply {
                        it?.let { partySession ->
                            bindData(
                                partySession
                            )
                        }
                    })
                    main_activity_sessions_holder.addView(PartySessionView(this@MainActivity).apply {
                        it?.let { partySession ->
                            bindData(
                                partySession
                            )
                        }
                    })
                }
            }.execute()
        }

        AppLinksHandler().handleIntent(intent, this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        AppLinksHandler().handleIntent(intent, this)
    }

    fun joinPartySession(id: String?, queuePassword: String?) {
        withExecutorParams {
            Executor<PartySession?>(it).apply {
                ioOperation = {
                    viewModel.joinPartySession(id, queuePassword)
                }
                uiOperationOnSuccess = {
                    main_activity_sessions_holder.addView(PartySessionView(this@MainActivity).apply {
                        it?.let { partySession ->
                            bindData(
                                partySession
                            )
                        }
                    }, 0)
                }
            }
        }
    }
}