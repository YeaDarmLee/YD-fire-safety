package com.directionfinding.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Singleton
class AppPreferences(
    @ApplicationContext private val applicationContext: Context
) {
    companion object {
        private const val PREFERENCES_NAME = "directionFinding"
        private const val KEY_BLUETOOH_PERMISSION_REQ_COUNT = "KEY_BLUETOOH_PERMISSION_REQ_COUNT"

        private val Context.preferences by AppPreferencesDelegate()

        fun makePreferences(context: Context): SharedPreferences {
            return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        }
    }

    var blPermissionReqCount
        get() = applicationContext.preferences.getInt(KEY_BLUETOOH_PERMISSION_REQ_COUNT, 0)
        set(value) {
            applicationContext.preferences.edit {
                putInt(KEY_BLUETOOH_PERMISSION_REQ_COUNT, value)
                commit()
            }
        }

}