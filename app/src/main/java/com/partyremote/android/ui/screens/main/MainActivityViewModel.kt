package com.partyremote.android.ui.screens.main

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.partyremote.android.models.IPartySessionModel
import com.partyremote.android.ui.base.BaseViewModel

class MainActivityViewModel(app: Application): BaseViewModel(app), IPartySessionModel{
    companion object {
        fun create(context: ViewModelStoreOwner): MainActivityViewModel {
            return ViewModelProvider(context)[MainActivityViewModel::class.java]
        }
    }
}