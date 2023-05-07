package org.sorakun.soradisplay.accuweather;

import android.content.Context;
import android.os.Handler;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sorakun.soradisplay.natureremo.DeviceRecord;

import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.Fragment;

public class CurrentConditionRunnable
        implements Runnable, Response.ErrorListener {

    private String locationKey;

    private Fragment fragment;
    private Handler handler;

    private final int repeatMinutes = 60;

    public CurrentConditionRunnable(Fragment f) {
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

        if (locationKey == null || locationKey.length() == 0) {
            String url = "http://dataservice.accuweather.com/locations/v1/cities/ipaddress?apikey=596FmOGXl808cPtSbZajf5Iw6rgDjdtH";

            //creating json request for the NatureRemo sensor
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    this::onLocationResponse,
                    this
            );
            RequestQueue queue = Volley.newRequestQueue(fragment.getContext());
            queue.add(request);
        } else {
            sendCurrentConditionRequest();
        }
    }

    private void sendCurrentConditionRequest() {

        Response.Listener<JSONArray> listender = (Response.Listener<JSONArray>)fragment;
        if (listender != null) {

            String url = "http://dataservice.accuweather.com/currentconditions/v1/" + locationKey + "?apikey=596FmOGXl808cPtSbZajf5Iw6rgDjdtH&details=true";

            //creating json request for the NatureRemo sensor
            JsonArrayRequest request = new JsonArrayRequest(
                    Request.Method.GET,
                    url,
                    null,
                    listender,
                    this
            );
            RequestQueue queue = Volley.newRequestQueue(fragment.getContext());
            queue.add(request);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    public void onLocationResponse(JSONObject response) {
        try {
            locationKey = response.getString("Key");
            sendCurrentConditionRequest();

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
