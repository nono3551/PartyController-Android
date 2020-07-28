package com.partyremote.android.application

import android.content.Context
import sk.backbone.android.shared.application.IService
import com.partyremote.android.models.ModelsProvider

interface BaseService : IService<ModelsProvider> {
    override val modelsProvider: ModelsProvider
        get() = object : ModelsProvider {
            override val context: Context
                get() = this@BaseService.context
    }
}