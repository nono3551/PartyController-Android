package com.partyremote.android.models

import com.partyremote.android.entities.Settings
import com.partyremote.android.repositories.shared_preferences.SettingsSharedPreferences
import sk.backbone.android.shared.models.IModel

interface ISettingsModel : IModel<ModelsProvider> {
    private val settingsSharedPreferences get() = SettingsSharedPreferences(context)

    fun setSettings(settings: Settings){
        settingsSharedPreferences.setSettings(settings)
        currentSettings = settings
    }

    fun getSettings(): Settings? {
        if(currentSettings == null){
            currentSettings = settingsSharedPreferences.getSettings()
        }

        return currentSettings
    }

    fun isUsernameSet() = getSettings()?.username != null
    fun shouldShowNotifications() = getSettings()?.showNotifications != null

    companion object {
        private var currentSettings: Settings? = null
    }
}