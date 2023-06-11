package org.sorakun.soradisplay

import android.graphics.Color
import java.util.Locale

class Util {

    companion object {
        var Blue = Color.HSVToColor(floatArrayOf(192.0.toFloat(), 100.0.toFloat(), 100.0.toFloat()))
        var Yellow =
            Color.HSVToColor(floatArrayOf(45.0.toFloat(), 100.0.toFloat(), 100.0.toFloat()))
        var Red = Color.HSVToColor(floatArrayOf(19.0.toFloat(), 100.0.toFloat(), 100.0.toFloat()))

        fun getTemperatureColor(temp: Double): Int {
            if (temp > 29.0) return Red // reddish
            if (temp > 24.0) return Yellow // yellowish
            return if (temp < 18.0) Blue else Color.WHITE // blueish
        }

        fun getHumidityColor(percent: Double): Int {
            if (percent > 60.0) return Blue // blueish
            return if (percent < 30.0) Yellow else Color.WHITE // yellowish
        }

        fun getChanceOfRainColor(percent: Double): Int {
            return if (percent > 50.0) Blue else Color.WHITE // blueish
        }

        fun printF(format: String?, vararg objects: Any?): String {
            return String.format(Locale.getDefault(), format!!, *objects)
        }
    }
}