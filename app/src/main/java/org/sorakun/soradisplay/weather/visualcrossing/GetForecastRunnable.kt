package org.sorakun.soradisplay.weather.visualcrossing

import android.content.Context
import android.util.Log
import androidx.preference.PreferenceManager
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import org.sorakun.soradisplay.weather.ForecastRecordViewModel
import org.sorakun.soradisplay.weather.GetForecastRunnableBase
import java.lang.Exception

class GetForecastRunnable (context: Context, viewModel: ForecastRecordViewModel) : Runnable,
    GetForecastRunnableBase(context, viewModel) {

    private val requestQueue = Volley.newRequestQueue(context)
    private val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
    private var responseCount : Int = 0

    override fun sendRequest() {

        //val url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/$location?unitGroup=metric&key=$apiKey&contentType=json"
        //val url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/$location?unitGroup=metric&key=HVGWEGYKE6JRT3G57Z4FVVEGZ&contentType=json"
            //"http://api.weatherapi.com/v1/forecast.json?key=5f4e6392424947e58a2135620230605&q=Tokyo&days=10&aqi=no&alerts=no"
        var url = "http://$server:7000/weather"
        Log.i("SoraDisplay", "GetForecastRunnable:sendRequest $url")

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
        requestQueue.add(request)
    }

    private fun onErrorResponse(error: VolleyError?) {
        responseCount++
        Log.i("SoraDisplay", "GetForecastRunnable:responseCount:$responseCount")
        Log.e("SoraDisplay", "GetForecastRunnable:Failed with error msg:" + error?.message)
        Log.e("SoraDisplay", "GetForecastRunnable:Error StackTrace:" + error!!.stackTrace)
        try {
            val htmlBodyBytes = error.networkResponse.data
            Log.e("SoraDisplay", String(htmlBodyBytes), error)
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }

    private fun onResponseJSONObject(response: JSONObject?) {
        responseCount++
        Log.i("SoraDisplay", "GetForecastRunnable:responseCount:$responseCount")
        try {
            viewModel.set(response)
        } catch (e: Exception) {
            Log.e(
                "SoraDisplay",
                "GetForecastRunnable: ${e.message}"
            )
            e.printStackTrace()
        }
    }
}