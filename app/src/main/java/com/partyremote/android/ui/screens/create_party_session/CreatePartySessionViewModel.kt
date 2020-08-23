package com.partyremote.android.ui.screens.create_party_session

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.partyremote.android.models.IPartySessionModel
import com.partyremote.android.ui.base.BaseViewModel

class CreatePartySessionViewModel(app: Application): BaseViewModel(app), IPartySessionModel{
    companion object {
        fun create(context: ViewModelStoreOwner): CreatePartySessionViewModel {
            return ViewModelProvider(context)[CreatePartySessionViewModel::class.java]
        }
    }
}