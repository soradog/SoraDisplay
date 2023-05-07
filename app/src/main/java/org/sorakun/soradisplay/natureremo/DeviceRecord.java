package org.sorakun.soradisplay.natureremo;

import org.json.JSONException;
import org.json.JSONObject;
import org.sorakun.soradisplay.databinding.FragmentClockBinding;

import java.util.Locale;

public class DeviceRecord {
    private String name;
    private Double temperature;
    private Double humidity;
    private Double brightness;

    public DeviceRecord(JSONObject object) throws JSONException {
        JSONObject newestEvents = object.getJSONObject("newest_events");
        setTemperature(newestEvents.getJSONObject("te"));
        setHumidity(newestEvents.getJSONObject("hu"));
        setBrightness(newestEvents.getJSONObject("il"));
        name = object.getString("name");
    }

    public void updateViews(FragmentClockBinding binding) {
        binding.sensorTemperature.setText(String.format(
                Locale.getDefault(),
                "%d°c", getTemperature().intValue()));
        binding.sensorHumidity.setText(String.format(
                Locale.getDefault(),
                "%d%%", getHumidity().intValue()));
        binding.remoLocation.setText(name);
    }

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
