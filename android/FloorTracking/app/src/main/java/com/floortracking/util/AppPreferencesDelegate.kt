package com.floortracking.util

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class AppPreferencesDelegate: ReadOnlyProperty<Context, SharedPreferences> {
    private val lock = Any()
    private var INSTANCE: SharedPreferences? = null
    override fun getValue(thisRef: Context, property: KProperty<*>): SharedPreferences {
        return INSTANCE ?: synchronized(lock) {
            if(INSTANCE == null) {
                val applicationContext = thisRef.applicationContext
                INSTANCE = AppPreferences.makePreferences(applicationContext)
            }
            INSTANCE!!
        }
    }
}