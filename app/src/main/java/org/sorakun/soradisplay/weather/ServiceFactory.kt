package org.sorakun.soradisplay.weather

import android.content.Context
import android.content.res.AssetManager
import android.widget.ImageView
import com.squareup.moshi.Moshi
import org.json.JSONObject
import org.sorakun.soradisplay.weather.visualcrossing.ForecastRecord
import org.sorakun.soradisplay.weather.visualcrossing.GetForecastRunnable
//import org.sorakun.soradisplay.weather.tomorrowio.ForecastRecord
//import org.sorakun.soradisplay.weather.tomorrowio.GetForecastRunnable
import java.io.BufferedReader
import java.io.InputStreamReader


class ServiceFactory {

    companion object {

        /* object for visual crossing */
        fun forecastRecordFromJSON(record: JSONObject?) : ForecastRecordBase? {
            val moshi = Moshi.Builder().build()
            val jsonAdapter = moshi.adapter(ForecastRecord::class.java)
            return jsonAdapter.fromJson(record.toString())
        }

        fun initForecastRecord() : ForecastRecordBase {
            return ForecastRecord()
        }

        fun setIcon(c: Context?, name: String, icon: ImageView?) {
            if (icon == null) return
            val context = c ?: icon.context
            val resname = name.replace("-", "_")
            val resid = context.resources.getIdentifier("visualcrossing_$resname", "drawable", context.packageName)
            if (resid != 0) {
                val drawable = androidx.appcompat.content.res.AppCompatResources.getDrawable(context, resid)
                icon.setImageDrawable(drawable)
            }
        }

        fun requestRunnable(context: Context, viewModel: ForecastRecordViewModel) : GetForecastRunnableBase {
            return GetForecastRunnable(context, viewModel)
        }

        /* object for tomrrow io */
        private lateinit var weatherCode : JSONObject

        fun setStaticData(asset: AssetManager) {
            val inputStream = asset.open("tomorrowio.weathercodes.json") //Jsonファイル
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            val str = bufferedReader.readText() //データ
            val jsonObject = JSONObject(str)
            weatherCode = jsonObject.getJSONObject("weatherCode")
        }

        fun getWeatherCondition(code: Int) : String {
            return weatherCode.getString(code.toString())
        }

        /*
        fun forecastRecordFromJSON(record: JSONObject?) : ForecastRecordBase? {
            val moshi = Moshi.Builder().build()
            val jsonAdapter = moshi.adapter(ForecastRecord::class.java)
            return jsonAdapter.fromJson(record.toString())
        }

        fun initForecastRecord() : ForecastRecordBase {
            return ForecastRecord()
        }

        fun setIcon(c: Context?, name: String, icon: ImageView?) {
            if (icon == null) return
            val context = c ?: icon.context
            val resname = name.replace("-", "_")
            val resid = context.resources.getIdentifier("visualcrossing_$resname", "drawable", context.packageName)
            if (resid != 0) {
                val drawable = androidx.appcompat.content.res.AppCompatResources.getDrawable(context, resid)
                icon.setImageDrawable(drawable)
            }
        }

        fun requestRunnable(context: Context, viewModel: ForecastRecordViewModel) : GetForecastRunnableBase {
            return GetForecastRunnable(context, viewModel)
        }*/
    }
}