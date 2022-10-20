package com.floortracking.util

import android.util.Log
import kotlin.math.pow

object MathUtils {
    fun parseInt(strNum: String?) : Int {
        return try {
            strNum?.toInt()?: 0
        } catch (e: java.lang.NumberFormatException) {
            0
        }
    }

    fun parseFloat(strNum: String?): Float {
        strNum?.let {

            it.toFloatOrNull()?.run {
                return this
            }
            it.toIntOrNull()?.run {
                return this.toFloat()
            }

            if (it != "." && it.contains(".")) {
                val split = it.split(".")
                if (split.size >= 2) {
                    if (split[0].isEmpty()) {
                        val temp = "0.${split[1]}"
                        return temp.toFloatOrNull()?:0f
                    }
                    if(split[1].isEmpty()) {
                        val temp = "${split[0]}.0"
                        return temp.toFloatOrNull()?:0f
                    }
                }
            }
        }
        return 0f
    }

    fun calAltitude(seaLevel: Float, pressure: Float): Float {

        if (seaLevel <= 0 || pressure <= 0) {
            return 0f
        }
        val altitude = (44300 * (1 - (pressure / seaLevel).toDouble().pow(1.0 / 5.255))).toFloat()
        Log.d("prepre", "$altitude")
        return altitude
    }
}