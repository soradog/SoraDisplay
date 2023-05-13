package org.sorakun.soradisplay;

import android.graphics.Color;
import android.widget.ImageView;

import java.util.Locale;
import com.squareup.picasso.Picasso;

public class Util {
    public static int Blue = Color.HSVToColor(new float[] {(float)192.0, (float)100.0, (float)100.0});
    public static int Yellow = Color.HSVToColor(new float[] {(float)45.0, (float)100.0, (float)100.0});
    public static int Red = Color.HSVToColor(new float[] {(float)19.0, (float)100.0, (float)100.0});
    public static int getTemperatureColor(Double temp) {
        if (temp > 29.0) return Red; // reddish
        if (temp > 24.0) return Yellow; // yellowish
        if (temp < 18.0) return Blue; // blueish
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

    /*public static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                // use larger icons from weatherapi.com
                urldisplay = urldisplay.replace("64x64", "128x128");
                // insert http prefix if needed
                if (urldisplay.substring(0, 2).compareTo("//") == 0) {
                    urldisplay = "http:" + urldisplay;
                }
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }*/

    public static void callPicasso(String url, ImageView icon) {
        url = url.replace("64x64", "128x128");
        // insert http prefix if needed
        if (url.substring(0, 2).compareTo("//") == 0) {
            url = "http:" + url;
        }
        Picasso.get().load(url).into(icon);
    }

    public static String printF(String format, Object... objects) {
        return String.format(Locale.getDefault(), format, objects);
    }

}
