package com.partyremote.android.repositories.shared_preferences

import android.content.Context
import com.partyremote.android.entities.Settings
import sk.backbone.android.shared.repositories.shared_preferences.SharedPreferencesDataProvider

class SettingsSharedPreferences(context: Context) : SharedPreferencesDataProvider("SETTINGS_SHARED_PREFERENCES", context) {
    private val settingsKey = "SETTINGS"

    fun setSettings(user: Settings?) {
        storeValue(settingsKey, user)
    }

    fun getSettings(): Settings? {
        return getValue(settingsKey)
    }
}