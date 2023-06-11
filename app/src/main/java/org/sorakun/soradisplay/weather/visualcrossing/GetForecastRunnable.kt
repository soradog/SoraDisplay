package org.sorakun.soradisplay.weather.visualcrossing

import android.util.Log
import androidx.activity.viewModels
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import org.sorakun.soradisplay.FullscreenActivity
import org.sorakun.soradisplay.weather.ForecastRecordViewModel
import org.sorakun.soradisplay.weather.GetForecastRunnableBase

class GetForecastRunnable (private val activity: FullscreenActivity) : Runnable,
    GetForecastRunnableBase(activity) {

    override fun sendRequest() {
        val url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/$location?unitGroup=metric&key=$apiKey&contentType=json"
        //val url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/$location?unitGroup=metric&key=HVGWEGYKE6JRT3G57Z4FVVEGZ&contentType=json"
            //"http://api.weatherapi.com/v1/forecast.json?key=5f4e6392424947e58a2135620230605&q=Tokyo&days=10&aqi=no&alerts=no"

        //creating json request for the NatureRemo sensor
        val request: JsonRequest<JSONObject> = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response: JSONObject? ->
                onResponseJSONObject(
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

    private fun onErrorResponse(error: VolleyError?) {
        Log.e("GetForecastRunnable", "Error: ${error?.localizedMessage}")
    }

    private fun onResponseJSONObject(response: JSONObject?) {
        val viewModel by activity.viewModels<ForecastRecordViewModel>()
        viewModel.set(response)
    }
}