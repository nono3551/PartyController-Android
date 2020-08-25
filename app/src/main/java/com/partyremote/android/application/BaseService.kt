package com.partyremote.android.application

import android.content.Context
import com.partyremote.android.models.ModelsProvider
import sk.backbone.parent.application.IService

interface BaseService : IService<ModelsProvider> {
    override val modelsProvider: ModelsProvider
        get() = object : ModelsProvider {
            override val context: Context
                get() = this@BaseService.context
    }
}