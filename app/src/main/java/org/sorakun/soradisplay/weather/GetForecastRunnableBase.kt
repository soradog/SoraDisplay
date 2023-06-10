package org.sorakun.soradisplay.weather

import android.os.Handler
import androidx.preference.PreferenceManager
import org.sorakun.soradisplay.FullscreenActivity

open class GetForecastRunnableBase(private val activity: FullscreenActivity) : Runnable {
    private val handler: Handler = Handler()

    fun firstRun() {
        handler.post(this)
    }

    private var repeatMinutes: Int = 0
    private var enabled: Boolean = false
    var apiKey: String? = null
    var location: String? = null

    override fun run() {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(activity)
        enabled = sharedPref.getBoolean("weatherapi", false)
        apiKey = sharedPref.getString("weather_sync_api_key", null)
        val syncfreq = sharedPref.getString("weather_sync_time", "10")
        location = sharedPref.getString("weather_location_name", "Tokyo")
        repeatMinutes = syncfreq!!.toInt()
        if (enabled && apiKey != null && repeatMinutes > 0 && location != null) {
            sendRequest()
            // Repeat this the same runnable code block again another 2 seconds
            handler.postDelayed(this, (repeatMinutes * 60 * 1000).toLong())
        }
    }

    open fun sendRequest() {
    }
}