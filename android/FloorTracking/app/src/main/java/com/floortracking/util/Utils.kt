package com.floortracking.util

import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import kotlin.math.pow

object Utils {
    fun isValidFloorValue(strNum: String, oriText: String) : String? {
        if (strNum.contains(".")) {
            return oriText
        }
        try {
            strNum.toIntOrNull()?.run {
                if (this >= 1000) return oriText
            }

            strNum.toFloatOrNull()?.run {
                if (this >= 1000f) return oriText
            }

        } catch (e: NumberFormatException) {
            return oriText
        }
        return null
    }
    fun isValidValue(strNum: String, oriText: String) : String? {
        try {
            //val num =
            strNum.toIntOrNull()?.run {
                if (this >= 100) return oriText
            }

            strNum.toFloatOrNull()?.run {
                if (this >= 100f) return oriText
            }

        } catch (e: NumberFormatException) {

        }

        if (strNum != "." && strNum.contains(".")) {
            val split = strNum.split(".")
            if (split.size >= 2) {
                if (split[0].isEmpty()) {
                    return "0.${split[1]}"
                }

                if (split[1].isEmpty()) {

                }

                try {
                    val integer = split[0].toInt()
                    if (integer >= 100) {
                        return oriText
                    }
                    val decimal = split[1].toInt()
                    if (decimal >= 100) {
                        return oriText
                    }
                } catch (e: NumberFormatException) {

                }
            }
        }
        return null
    }
}