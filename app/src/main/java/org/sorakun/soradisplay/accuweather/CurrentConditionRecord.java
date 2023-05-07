package org.sorakun.soradisplay.accuweather;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.sorakun.soradisplay.R;

import java.util.Locale;

public class CurrentConditionRecord {
    private Integer iconCode;
    private Double temperature;
    private Double realFeel;
    private Double humidity;
    private Double windSpeed;
    private Double windGust;

    public CurrentConditionRecord(JSONObject object) throws JSONException {
        iconCode = object.getInt("WeatherIcon");
        setTemperature(object.getJSONObject("Temperature"));
        humidity = object.getDouble("RelativeHumidity");
        setRealFeel(object.getJSONObject("RealFeelTemperature"));
        setWindSpeed(object.getJSONObject("Wind"));
        setWindGust(object.getJSONObject("WindGust"));
    }

    private void setTemperature(JSONObject object) throws JSONException {
        temperature = object.getJSONObject("Metric").getDouble("Value");
    }

    private void setRealFeel(JSONObject object) throws JSONException {
        realFeel = object.getJSONObject("Metric").getDouble("Value");
    }

    private void setWindSpeed(JSONObject object) throws JSONException {
        windSpeed = object.getJSONObject("Speed").getJSONObject("Metric").getDouble("Value");
    }

    private void setWindGust(JSONObject object) throws JSONException {
        windGust = object.getJSONObject("Speed").getJSONObject("Metric").getDouble("Value");
    }

    public void updateViews(
            Context context,
            ImageView iconDisplay,
            TextView tempDisplay,
            TextView realFeelDisplay,
            TextView humidDisplay,
            TextView windSpeedDisplay,
            TextView windGustDisplay) {
        tempDisplay.setText(String.format(Locale.getDefault(), "%d\u2103", temperature.intValue()));
        humidDisplay.setText(String.format(Locale.getDefault(), "%d%%", humidity.intValue()));
        realFeelDisplay.setText(String.format(Locale.getDefault(), "%d\u2103", realFeel.intValue()));
        windSpeedDisplay.setText(String.format(Locale.getDefault(), "%dkm/h", windSpeed.intValue()));
        windGustDisplay.setText(String.format(Locale.getDefault(), "%dkm/h", windGust.intValue()));

        Resources resources = context.getResources();
        String imageName = "@drawable/weather" + iconCode;
        final int resourceId = resources.getIdentifier(imageName, "drawable",
                context.getPackageName());
        iconDisplay.setImageDrawable(resources.getDrawable(resourceId));
    }
}
