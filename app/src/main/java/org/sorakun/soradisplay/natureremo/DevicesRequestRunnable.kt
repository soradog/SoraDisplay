package org.sorakun.soradisplay.natureremo

import android.content.Context
import android.os.Handler
import android.util.Log
import androidx.preference.PreferenceManager
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class DevicesRequestRunnable(context: Context, private val viewModel: DeviceRecordViewModel) : Runnable,
    Response.ErrorListener {
    private val handler: Handler = Handler()
    private val requestQueue = Volley.newRequestQueue(context)
    private val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
    private var responseCount : Int = 0

    fun firstRun() {
        handler.post(this)
    }

    fun pause() {
        handler.removeCallbacks(this)
    }

    override fun run() {
        val enabled = sharedPref.getBoolean("natureremo", false)
        val apiKey = sharedPref.getString("natureremo_sync_api_key", null)
        val value = sharedPref.getString("natureremo_sync_time", "5")
        val repeatMinutes = value!!.toInt()
        if (enabled && apiKey != null && repeatMinutes > 0) {
            sendRequest()
            // Repeat this the same runnable code block again another 2 seconds
            handler.postDelayed(this, (repeatMinutes * 60 * 1000).toLong())
        }
    }

    private fun sendRequest() {
        val url = "https://api.nature.global/1/devices"

        Log.i("SoraDisplay", "DevicesRequestRunnable:sendRequest")
        //creating json request for the NatureRemo sensor
        val request: JsonArrayRequest = object : JsonArrayRequest(
            Method.GET,
            url,
            null,
            Response.Listener { response: JSONArray? -> onResponseJSONArray(response) },
            this
        ) {
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                val apiKey = sharedPref.getString("natureremo_sync_api_key", "")
                //params.put("Authorization", "Bearer VO-oNV-ZqrpaVXHXny3hO6vWgcR7wY7a4jQseo82EpE.f0M8KiqPvKSjv8EJL3KXIyf4MnurGdZDnq0naJmlg8M");
                params["Authorization"] = "Bearer $apiKey"
                return params
            }
        }
        requestQueue.add(request)
    }

    fun onResponseJSONArray(response: JSONArray?) {
        responseCount++
        Log.i("SoraDisplay", "DevicesRequestRunnable:responseCount:$responseCount")
        if (response != null && response.length() > 0) {
            viewModel.set(response.getJSONObject(0))
        }
    }

    override fun onErrorResponse(error: VolleyError) {
        responseCount++
        Log.i("SoraDisplay", "DevicesRequestRunnable:responseCount:$responseCount")
        Log.e("SoraDisplay", "DevicesRequestRunnable:Failed with error msg:" + error?.message)
        Log.e("SoraDisplay", "DevicesRequestRunnable:Error StackTrace:" + error!!.stackTrace)
        try {
            val htmlBodyBytes = error.networkResponse.data
            Log.e("SoraDisplay", String(htmlBodyBytes), error)
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }
}