package org.sorakun.soradisplay.natureremo;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import android.os.Handler;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class DevicesRequestRunnable
        implements Runnable, Response.ErrorListener {

    private final Fragment fragment;
    private final Handler handler;

    private final static int repeatMinutes = 5;

    public DevicesRequestRunnable(Fragment f) {
        fragment = f;
        handler = new Handler();
    }

    public void firstRun() {
        handler.post(this);
    }

    @Override
    public void run() {
        sendRequest();

        // Repeat this the same runnable code block again another 2 seconds
        handler.postDelayed(this, repeatMinutes * 60 * 1000);
    }

    private void sendRequest() {
        Response.Listener<JSONArray> listener = (Response.Listener<JSONArray>)fragment;
        if (listener != null) {

            String url = "https://api.nature.global/1/devices";

            //creating json request for the NatureRemo sensor
            JsonArrayRequest request = new JsonArrayRequest(
                    Request.Method.GET,
                    url,
                    null,
                    listener,
                    this
            ) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<>();
                    params.put("Authorization", "Bearer VO-oNV-ZqrpaVXHXny3hO6vWgcR7wY7a4jQseo82EpE.f0M8KiqPvKSjv8EJL3KXIyf4MnurGdZDnq0naJmlg8M");
                    return params;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(fragment.getContext());
            queue.add(request);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}
