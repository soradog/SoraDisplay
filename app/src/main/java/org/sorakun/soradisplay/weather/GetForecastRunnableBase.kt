package org.sorakun.soradisplay.weather

import android.content.Context
import android.os.Handler
import androidx.preference.PreferenceManager
import org.json.JSONArray

open class GetForecastRunnableBase(context: Context, val viewModel: ForecastRecordViewModel) : Runnable {
    private val handler: Handler = Handler()
    private val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)

    fun firstRun() {
        handler.post(this)
    }

    fun pause() {
        handler.removeCallbacks(this)
    }

    private var repeatMinutes: Int = 20
    private var enabled: Boolean = false
    var server: String? = null
    var location: String? = null
    var isRunning = false

    override fun run() {
        isRunning = if (runAllowed()) {
            sendRequest()
            // Repeat this the same runnable code block again another 2 seconds
            handler.postDelayed(this, (repeatMinutes * 60 * 1000).toLong())
        } else {
            false
        }
    }

    private fun runAllowed() : Boolean {
        enabled = sharedPref.getBoolean("weatherapi", false)
        server = sharedPref.getString("weather_server_name", null)
        val syncfreq = sharedPref.getString("weather_sync_time", "20")
        location = sharedPref.getString("weather_location_name", "Tokyo")
        repeatMinutes = syncfreq!!.toInt()
        return (enabled && repeatMinutes > 0)
    }

    fun onResponseJSONArray(response: JSONArray?) {
        if (response != null && response.length() > 0) {
            viewModel.set(response.getJSONObject(0))
        }
    }

    open fun sendRequest() {
    }
}