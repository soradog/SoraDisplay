package org.sorakun.soradisplay.weather.visualcrossing

import android.os.Handler
import androidx.preference.PreferenceManager
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import org.sorakun.soradisplay.FullscreenActivity

class GetForecastRunnable(private val activity: FullscreenActivity) : Runnable {
    private val handler: Handler = Handler()

    fun firstRun() {
        handler.post(this)
    }

    private var repeatMinutes: Int = 0
    private var enabled: Boolean = false
    private var apiKey: String? = null
    private var location: String? = null

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

    private fun sendRequest() {
        val url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/$location?unitGroup=metric&key=$apiKey&contentType=json"
        //val url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/$location?unitGroup=metric&key=HVGWEGYKE6JRT3G57Z4FVVEGZ&contentType=json"
            //"http://api.weatherapi.com/v1/forecast.json?key=5f4e6392424947e58a2135620230605&q=Tokyo&days=10&aqi=no&alerts=no"

        //creating json request for the NatureRemo sensor
        val request: JsonRequest<JSONObject> = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response: JSONObject? ->
                activity.onResponseJSONObject(
                    response
                )
            }
        ) { error: VolleyError? ->
            onErrorResponse(
                error
            )
        }
        val queue = Volley.newRequestQueue(activity)
        queue.add(request)
    }

    fun onErrorResponse(error: VolleyError?) {}
}