package com.directionfinding.util

import android.graphics.Point
import android.util.Log
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

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
        return altitude
    }
    fun calRSSIDistance(rssi : Int) : Int {
        //val temp: Float = 10f
        val distance = 10.0.pow((-59.0-(rssi.toDouble())) / (10.0*2.0))
        Log.d("sqrt", "$rssi, $distance")
        return (distance * 1000).toInt()

    }
    fun calRescuerPosition(width: Int, height: Int, distance: Int, degree: Double = 0.0): Point {
        val maxLength = 5000
        val radians = degree * Math.PI / 180
        val y = (height * distance) / maxLength
        val oriPoint = Point(0, -(y / 2))
        val x1 = oriPoint.x * cos(radians) - oriPoint.y * sin(radians)
        val y1 = oriPoint.x * sin(radians) + oriPoint.y * cos(radians)
        return Point(x1.toInt(), y1.toInt())
    }
}