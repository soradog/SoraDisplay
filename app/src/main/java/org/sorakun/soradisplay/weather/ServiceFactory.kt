package org.sorakun.soradisplay.weather

import android.content.Context
import android.widget.ImageView
import com.squareup.moshi.Moshi
import com.squareup.picasso.Picasso
import org.json.JSONObject
import org.sorakun.soradisplay.FullscreenActivity
import org.sorakun.soradisplay.weather.visualcrossing.ForecastRecord
import org.sorakun.soradisplay.weather.visualcrossing.GetForecastRunnable

class ServiceFactory {
    companion object {
        fun forecastRecordFromJSON(record: JSONObject?) : ForecastRecordBase? {
            val moshi = Moshi.Builder().build()
            val jsonAdapter = moshi.adapter(ForecastRecord::class.java)
            return jsonAdapter.fromJson(record.toString())
        }

        fun initForecastRecord() : ForecastRecordBase {
            return ForecastRecord()
        }

        fun setIcon(c: Context?, name: String, icon: ImageView?) {
            val resname = name.replace("-", "_")
            val resid = icon?.resources?.getIdentifier("visualcrossing_$resname", "drawable", c?.packageName)
            if (resid != null && resid != 0) {
                Picasso.get().load(resid).into(icon)
            }
        }

        fun requestRunnable(context: Context, viewModel: ForecastRecordViewModel) : GetForecastRunnableBase {
            return GetForecastRunnable(context, viewModel)
        }
    }
}