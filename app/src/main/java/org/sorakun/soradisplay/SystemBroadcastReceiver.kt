package org.sorakun.soradisplay

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import java.text.SimpleDateFormat
import java.util.*

abstract class SystemBroadcastReceiver : BroadcastReceiver() {

    private var date = Date()
    private val dateFormat by lazy { SimpleDateFormat("MMM dd (EE)", Locale.getDefault()) }

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action

        val currentDate = Date()

        if ((action == Intent.ACTION_TIME_CHANGED || action == Intent.ACTION_DATE_CHANGED || action == Intent.ACTION_TIMEZONE_CHANGED) && !isSameDay(currentDate)) {
        //if (action == Intent.ACTION_TIME_TICK) {
            onDayChanged(printDate(currentDate))
        } else if (action == Intent.ACTION_POWER_DISCONNECTED) {
            onChargeStateChanged(false)
        } else if (action == Intent.ACTION_POWER_CONNECTED) {
            onChargeStateChanged(true)
        }
    }

    private fun isSameDay(currentDate: Date) = dateFormat.format(currentDate) == dateFormat.format(date)

    fun printDate(toBeDisplayed: Date) : String {
        date = toBeDisplayed
        return dateFormat.format(toBeDisplayed)
    }

    /**
     * function to call when the date has changed
     */
    open fun onDayChanged(format: String) {
    }

    /**
     * function to call when the charging state has changed
     */
    open fun onChargeStateChanged(charging : Boolean) {
    }

    companion object {

        /**
         * Create the [IntentFilter] for the [SystemBroadcastReceiver].
         *
         * @return The [IntentFilter]
         */
        fun getIntentFilter() = IntentFilter().apply {
            addAction(Intent.ACTION_TIME_TICK)
            addAction(Intent.ACTION_TIMEZONE_CHANGED)
            addAction(Intent.ACTION_TIME_CHANGED)
            addAction(Intent.ACTION_DATE_CHANGED)
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
        }
    }
}