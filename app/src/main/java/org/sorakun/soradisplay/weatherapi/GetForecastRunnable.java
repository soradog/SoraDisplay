package org.sorakun.soradisplay.weatherapi;

import android.os.Handler;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.sorakun.soradisplay.FullscreenActivity;

public class GetForecastRunnable implements Runnable {

    private final FullscreenActivity activity;
    private final Handler handler;

    public GetForecastRunnable(FullscreenActivity f) {
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
        int repeatMinutes = 10;
        handler.postDelayed(this, repeatMinutes * 60 * 1000);
    }

    private void sendRequest() {

            String url = "http://api.weatherapi.com/v1/forecast.json?key=5f4e6392424947e58a2135620230605&q=Tokyo&days=10&aqi=no&alerts=no";

            //creating json request for the NatureRemo sensor
            JsonRequest<JSONObject> request = new JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    activity::onResponseJSONObject,
                    this::onErrorResponse
            );
            RequestQueue queue = Volley.newRequestQueue(activity);
            queue.add(request);
    }

    public void onErrorResponse(VolleyError error) {

    }
}
