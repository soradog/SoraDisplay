package org.sorakun.soradisplay.weatherapi;

import android.app.Activity;
import android.os.Handler;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.fragment.app.Fragment;

public class GetForecastRunnable implements Runnable {

    private Activity activity;
    private Handler handler;

    private final int repeatMinutes = 10;

    public GetForecastRunnable(Activity f) {
        activity = f;
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

            String url = "http://api.weatherapi.com/v1/forecast.json?key=5f4e6392424947e58a2135620230605&q=Tokyo&days=7&aqi=no&alerts=no";

            //creating json request for the NatureRemo sensor
            JsonRequest request = new JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    ForecastRecord::onResponse,
                    this::onErrorResponse
            );
            RequestQueue queue = Volley.newRequestQueue(activity);
            queue.add(request);
    }

    public void onErrorResponse(VolleyError error) {

    }
}
