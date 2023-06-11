package org.sorakun.soradisplay.natureremo;

import org.json.JSONException;
import org.json.JSONObject;

public class DeviceRecord {
    private String name;
    private Double temperature;
    private Double humidity;
    private Double brightness;

    public DeviceRecord() {}
    public void update(JSONObject object) throws JSONException {
        JSONObject newestEvents = object.getJSONObject("newest_events");
        setTemperature(newestEvents.getJSONObject("te"));
        setHumidity(newestEvents.getJSONObject("hu"));
        setBrightness(newestEvents.getJSONObject("il"));
        name = object.getString("name");
    }

    public Boolean isReady() {
        return temperature != null;
    }
    public String getName() { return name; }

    private void setBrightness(JSONObject object) throws JSONException {
        brightness = object.getDouble("val");
    }

    public Double getBrightness() {
        return brightness;
    }

    private void setHumidity(JSONObject object) throws JSONException {
        humidity = object.getDouble("val");
    }

    public Double getHumidity() {
        return humidity;
    }

    private void setTemperature(JSONObject object) throws JSONException {
        temperature = object.getDouble("val");
    }

    public Double getTemperature() {
        return temperature;
    }
}

