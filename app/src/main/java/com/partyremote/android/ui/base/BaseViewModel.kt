package com.partyremote.android.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.partyremote.android.models.ModelsProvider

abstract class BaseViewModel(override val context: Application) : AndroidViewModel(context), ModelsProvider {
    val modelsProvider: ModelsProvider get() = this
}