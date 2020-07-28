package com.partyremote.android.models

import android.content.Context
import sk.backbone.android.shared.models.IModelsProvider

interface ModelsProvider : IModelsProvider {
    val partySessionModel: IPartySessionModel
        get() =  object : IPartySessionModel {
            override val context: Context get() = this@ModelsProvider.context
            override val modelsProvider: ModelsProvider get() = this@ModelsProvider
        }
}