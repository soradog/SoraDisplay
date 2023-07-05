package org.sorakun.soradisplay.natureremo

import android.content.Context
import android.os.Handler
import androidx.activity.viewModels
import androidx.preference.PreferenceManager
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.sorakun.soradisplay.FullscreenActivity

class DevicesRequestRunnable(context: Context, private val viewModel: DeviceRecordViewModel) : Runnable,
    Response.ErrorListener {
    private val handler: Handler = Handler()
    private val requestQueue = Volley.newRequestQueue(context)
    private val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)

    fun firstRun() {
        handler.post(this)
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
        if (response != null && response.length() > 0) {
            viewModel.set(response.getJSONObject(0))
        }
    }

    override fun onErrorResponse(error: VolleyError) {}
}