package com.floortracking.util

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
        private const val PREFERENCES_NAME = "floortracking"
        private const val KEY_ALiGN_ALTITUDE = "align_altitude"
        private const val KEY_SCENE_FIRE_NAME = "scene_firename"
        private const val KEY_GROUND_FLOOR = "ground_floor"
        private const val KEY_GROUND_HEIGHT = "ground_height"
        private const val KEY_MIDDLE_FLOOR = "middle_floor"
        private const val KEY_MIDDLE_HEIGHT = "middle_height"
        private const val KEY_UNDER_GROUND_FLOOR = "under_ground_floor"
        private const val KEY_UNDER_GROUND_HEIGHT = "under_ground_height"

        private val Context.preferences by AppPreferencesDelegate()

        fun makePreferences(context: Context): SharedPreferences {
            return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        }
    }

    var alignAltitude
        get() = applicationContext.preferences.getFloat(KEY_ALiGN_ALTITUDE, -9999f)
        set(value) {
            applicationContext.preferences.edit {
                putFloat(KEY_ALiGN_ALTITUDE, value)
                commit()
            }
        }

    var sceneFireName
        get() = applicationContext.preferences.getString(KEY_SCENE_FIRE_NAME, "")
        set(value) {
            applicationContext.preferences.edit {
                putString(KEY_SCENE_FIRE_NAME, value)
                commit()
            }
        }

    var groundFloor
        get() = applicationContext.preferences.getInt(KEY_GROUND_FLOOR, 0)
        set(value) {
            applicationContext.preferences.edit {
                putInt(KEY_GROUND_FLOOR, value)
                commit()
            }
        }

    var groundHeight
        get() = applicationContext.preferences.getFloat(KEY_GROUND_HEIGHT, 0f)
        set(value) {
            applicationContext.preferences.edit {
                putFloat(KEY_GROUND_HEIGHT, value)
                commit()
            }
        }

    var middleFloor
        get() = applicationContext.preferences.getInt(KEY_MIDDLE_FLOOR, 0)
        set(value) {
            applicationContext.preferences.edit {
                putInt(KEY_MIDDLE_FLOOR, value)
                commit()
            }
        }

    var middleHeight
        get() = applicationContext.preferences.getFloat(KEY_MIDDLE_HEIGHT, 0f)

        set(value) {
            applicationContext.preferences.edit {
                putFloat(KEY_MIDDLE_HEIGHT, value)
                commit()
            }
        }

    var underGroundFloor
        get() = applicationContext.preferences.getInt(KEY_UNDER_GROUND_FLOOR, 0)
        set(value) {
            applicationContext.preferences.edit {
                putInt(KEY_UNDER_GROUND_FLOOR, value)
                commit()
            }
        }

    var underGroundHeight
        get() = applicationContext.preferences.getFloat(KEY_UNDER_GROUND_HEIGHT, 0f)
        set(value) {
            applicationContext.preferences.edit {
                putFloat(KEY_UNDER_GROUND_HEIGHT, value)
                commit()
            }
        }

    fun isValidData() : Boolean {
        if (alignAltitude == -9999f || groundFloor <= 0 || groundHeight <= 0.0f ||
            middleFloor <= 0 || middleHeight <= 0.0f ||
            underGroundFloor <= 0 || underGroundHeight <= 0.0f)
            return false
        return true
    }
/*
    var filemanager: HashMap<String, HashMap<String, String>>?
        get() {
            applicationContext.preferences.getString(KEY_FILE_DOWNLOADMANAGER, null)?.run {
                val gson = Gson()
                val type: Type =
                    object : TypeToken<HashMap<String, HashMap<String, String>>>() {}.type

                val deserializeMap: HashMap<String, HashMap<String, String>> =
                    gson.fromJson(this, type)
                return deserializeMap
            }
            return null
        }
        set(value) {
            val gson = Gson()
            val serializeString = gson.toJson(value)
            applicationContext.preferences.edit {
                putString(KEY_FILE_DOWNLOADMANAGER, serializeString)
                commit()
            }
        }*/
}