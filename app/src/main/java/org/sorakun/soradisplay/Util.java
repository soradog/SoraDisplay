package org.sorakun.soradisplay;

import android.graphics.Color;

public class Util {
    public static int Blue = Color.HSVToColor(new float[] {(float)192.0, (float)100.0, (float)100.0});
    public static int Yellow = Color.HSVToColor(new float[] {(float)45.0, (float)100.0, (float)100.0});
    public static int Red = Color.HSVToColor(new float[] {(float)19.0, (float)100.0, (float)100.0});
    public static int getTemperatureColor(Double temp) {
        if (temp > 30.0) return Red; // reddish
        if (temp > 25.0) return Yellow; // yellowish
        if (temp < 15.0) return Blue; // blueish
        return Color.WHITE;
    }
    public static int getHumidityColor(Double percent) {
        if (percent > 60.0) return Blue; // blueish
        if (percent < 30.0) return Yellow; // yellowish
        return Color.WHITE;
    }

    public static int getChanceOfRainColor(Double percent) {
        if (percent > 50.0) return Blue; // blueish
        return Color.WHITE;
    }
}
