package org.sorakun.soradisplay.natureremo;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.sorakun.soradisplay.FullscreenActivity;

import java.util.HashMap;
import java.util.Map;

import android.content.SharedPreferences;
import android.os.Handler;
import androidx.preference.PreferenceManager;

public class DevicesRequestRunnable
        implements Runnable, Response.ErrorListener {

    private final FullscreenActivity activity;
    private final Handler handler;

    public DevicesRequestRunnable(FullscreenActivity f) {
        activity = f;
        handler = new Handler();
    }

    public void firstRun() {
        handler.post(this);
    }

    private Integer repeatMinutes;
    private Boolean enabled;
    private String apiKey;

    @Override
    public void run() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
        enabled = sharedPref.getBoolean("natureremo", false);
        apiKey = sharedPref.getString("natureremo_sync_api_key", null);
        String value = sharedPref.getString("natureremo_sync_time", "5");
        repeatMinutes = Integer.parseInt(value);
        if (enabled && apiKey != null && repeatMinutes > 0) {
            sendRequest();
            // Repeat this the same runnable code block again another 2 seconds
            handler.postDelayed(this, repeatMinutes * 60 * 1000);
        }
    }

    private void sendRequest() {

        String url = "https://api.nature.global/1/devices";

        //creating json request for the NatureRemo sensor
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                activity::onResponseJSONArray,
                this
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
                String apiKey = sharedPref.getString("natureremo_sync_api_key", "");
                //params.put("Authorization", "Bearer VO-oNV-ZqrpaVXHXny3hO6vWgcR7wY7a4jQseo82EpE.f0M8KiqPvKSjv8EJL3KXIyf4MnurGdZDnq0naJmlg8M");
                params.put("Authorization", "Bearer " + apiKey);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(activity);
        queue.add(request);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}
