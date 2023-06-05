package org.sorakun.soradisplay.weather.visualcrossing

import android.content.Context
import android.widget.ImageView
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.picasso.Picasso
import org.sorakun.soradisplay.Util
import org.sorakun.soradisplay.databinding.FragmentTodayWeatherBinding
import org.sorakun.soradisplay.databinding.FragmentWeatherForecastBinding

@JsonClass(generateAdapter = true)
data class ForecastRecord(
    @Json(name = "address")
    var address: String = "", // Tokyo
    @Json(name = "alerts")
    var alerts: List<Any> = listOf(),
    @Json(name = "currentConditions")
    var currentConditions: CurrentConditions = CurrentConditions(),
    @Json(name = "days")
    var days: List<Day> = listOf(),
    @Json(name = "description")
    var description: String = "", // Similar temperatures continuing with a chance of rain Thursday, Friday & Saturday.
    @Json(name = "latitude")
    var latitude: Double = 0.0, // 35.6841
    @Json(name = "longitude")
    var longitude: Double = 0.0, // 139.809
    @Json(name = "queryCost")
    var queryCost: Int = 0, // 1
    @Json(name = "resolvedAddress")
    var resolvedAddress: String = "", // Tokyo, Japan
    @Json(name = "timezone")
    var timezone: String = "", // Asia/Tokyo
    @Json(name = "tzoffset")
    var tzoffset: Int = 0 // 9
) {
    @JsonClass(generateAdapter = true)
    data class CurrentConditions(
        @Json(name = "cloudcover")
        var cloudcover: Double = 0.0, // 34.5
        @Json(name = "conditions")
        var conditions: String = "", // Partially cloudy
        @Json(name = "datetime")
        var datetime: String = "", // 20:00:00
        @Json(name = "datetimeEpoch")
        var datetimeEpoch: Int = 0, // 1685876400
        @Json(name = "dew")
        var dew: Double = 0.0, // 17.6
        @Json(name = "feelslike")
        var feelslike: Double = 0.0, // 21.7
        @Json(name = "humidity")
        var humidity: Double = 0.0, // 77.8
        @Json(name = "icon")
        var icon: String = "", // partly-cloudy-night
        @Json(name = "moonphase")
        var moonphase: Double = 0.0, // 0.5
        @Json(name = "precip")
        var precip: Int = 0, // 0
        @Json(name = "precipprob")
        var precipprob: Int = 0, // 0
        @Json(name = "preciptype")
        var preciptype: Any? = null, // null
        @Json(name = "pressure")
        var pressure: Double = 0.0, // 1006.3
        @Json(name = "snow")
        var snow: Int = 0, // 0
        @Json(name = "snowdepth")
        var snowdepth: Int = 0, // 0
        @Json(name = "solarenergy")
        var solarenergy: Int = 0, // 0
        @Json(name = "solarradiation")
        var solarradiation: Int = 0, // 0
        @Json(name = "source")
        var source: String = "", // obs
        @Json(name = "sunrise")
        var sunrise: String = "", // 04:25:41
        @Json(name = "sunriseEpoch")
        var sunriseEpoch: Int = 0, // 1685820341
        @Json(name = "sunset")
        var sunset: String = "", // 18:52:40
        @Json(name = "sunsetEpoch")
        var sunsetEpoch: Int = 0, // 1685872360
        @Json(name = "temp")
        var temp: Double = 0.0, // 21.7
        @Json(name = "uvindex")
        var uvindex: Int = 0, // 0
        @Json(name = "visibility")
        var visibility: Double = 0.0, // 10.7
        @Json(name = "winddir")
        var winddir: Int = 0, // 192
        @Json(name = "windgust")
        var windgust: Any? = null, // null
        @Json(name = "windspeed")
        var windspeed: Double = 0.0 // 20.7
    )

    @JsonClass(generateAdapter = true)
    data class Day(
        @Json(name = "cloudcover")
        var cloudcover: Double = 0.0, // 56.6
        @Json(name = "conditions")
        var conditions: String = "", // Partially cloudy
        @Json(name = "datetime")
        var datetime: String = "", // 2023-06-04
        @Json(name = "datetimeEpoch")
        var datetimeEpoch: Int = 0, // 1685804400
        @Json(name = "description")
        var description: String = "", // Becoming cloudy in the afternoon.
        @Json(name = "dew")
        var dew: Double = 0.0, // 14.6
        @Json(name = "feelslike")
        var feelslike: Double = 0.0, // 22.1
        @Json(name = "feelslikemax")
        var feelslikemax: Double = 0.0, // 25.9
        @Json(name = "feelslikemin")
        var feelslikemin: Double = 0.0, // 18.2
        @Json(name = "hours")
        var hours: List<Hour> = listOf(),
        @Json(name = "humidity")
        var humidity: Double = 0.0, // 62.9
        @Json(name = "icon")
        var icon: String = "", // partly-cloudy-day
        @Json(name = "moonphase")
        var moonphase: Double = 0.0, // 0.5
        @Json(name = "precip")
        var precip: Double = 0.0, // 0.7
        @Json(name = "precipcover")
        var precipcover: Double = 0.0, // 12.5
        @Json(name = "precipprob")
        var precipprob: Double = 0.0, // 9.5
        @Json(name = "preciptype")
        var preciptype: List<String>? = null,
        @Json(name = "pressure")
        var pressure: Double = 0.0, // 1007.1
        @Json(name = "severerisk")
        var severerisk: Int = 0, // 10
        @Json(name = "snow")
        var snow: Int = 0, // 0
        @Json(name = "snowdepth")
        var snowdepth: Int = 0, // 0
        @Json(name = "solarenergy")
        var solarenergy: Double = 0.0, // 25.9
        @Json(name = "solarradiation")
        var solarradiation: Double = 0.0, // 299.8
        @Json(name = "source")
        var source: String = "", // comb
        @Json(name = "sunrise")
        var sunrise: String = "", // 04:25:41
        @Json(name = "sunriseEpoch")
        var sunriseEpoch: Int = 0, // 1685820341
        @Json(name = "sunset")
        var sunset: String = "", // 18:52:40
        @Json(name = "sunsetEpoch")
        var sunsetEpoch: Int = 0, // 1685872360
        @Json(name = "temp")
        var temp: Double = 0.0, // 22.1
        @Json(name = "tempmax")
        var tempmax: Double = 0.0, // 25.9
        @Json(name = "tempmin")
        var tempmin: Double = 0.0, // 18.2
        @Json(name = "uvindex")
        var uvindex: Int = 0, // 10
        @Json(name = "visibility")
        var visibility: Double = 0.0, // 12.2
        @Json(name = "winddir")
        var winddir: Double = 0.0, // 200.1
        @Json(name = "windgust")
        var windgust: Double = 0.0, // 42.5
        @Json(name = "windspeed")
        var windspeed: Double = 0.0 // 20.5
    ) {
        @JsonClass(generateAdapter = true)
        data class Hour(
            @Json(name = "cloudcover")
            var cloudcover: Double = 0.0, // 89.6
            @Json(name = "conditions")
            var conditions: String = "", // Partially cloudy
            @Json(name = "datetime")
            var datetime: String = "", // 00:00:00
            @Json(name = "datetimeEpoch")
            var datetimeEpoch: Int = 0, // 1685804400
            @Json(name = "dew")
            var dew: Double = 0.0, // 9.7
            @Json(name = "feelslike")
            var feelslike: Double = 0.0, // 19.3
            @Json(name = "humidity")
            var humidity: Double = 0.0, // 53.52
            @Json(name = "icon")
            var icon: String = "", // partly-cloudy-night
            @Json(name = "precip")
            var precip: Double = 0.0, // 0.1
            @Json(name = "precipprob")
            var precipprob: Double = 0.0, // 9.5
            @Json(name = "preciptype")
            var preciptype: List<String>? = null,
            @Json(name = "pressure")
            var pressure: Double = 0.0, // 1005.2
            @Json(name = "severerisk")
            var severerisk: Int = 0, // 10
            @Json(name = "snow")
            var snow: Int = 0, // 0
            @Json(name = "snowdepth")
            var snowdepth: Int = 0, // 0
            @Json(name = "solarenergy")
            var solarenergy: Double = 0.0, // 0.4
            @Json(name = "solarradiation")
            var solarradiation: Double = 0.0, // 209.3
            @Json(name = "source")
            var source: String = "", // obs
            @Json(name = "temp")
            var temp: Double = 0.0, // 19.3
            @Json(name = "uvindex")
            var uvindex: Int = 0, // 0
            @Json(name = "visibility")
            var visibility: Double = 0.0, // 10.7
            @Json(name = "winddir")
            var winddir: Double = 0.0, // 145.6
            @Json(name = "windgust")
            var windgust: Double = 0.0, // 30.2
            @Json(name = "windspeed")
            var windspeed: Double = 0.0 // 14.9
        )
    }

    companion object {
        fun callPicasso(c: Context?, name: String, icon: ImageView?) {
            val resname = name.replace("-", "_")
            val resid = icon?.resources?.getIdentifier("visualcrossing_$resname", "drawable", c?.packageName)
            if (resid != null && resid != 0) {
                Picasso.get().load(resid).into(icon)
            }
        }
    }

    fun updateTodayViews(c: Context?, binding: FragmentTodayWeatherBinding) {
        // if lastupdated timestamp hasnt changed then dont update
        if (currentConditions.datetime != "" && currentConditions.datetime.compareTo(
                java.lang.String.valueOf(binding.lastupdated.text)
            ) == 0
        ) {
            // lastupdated in this record is the same as the one in binding
            // no need to update
            return
        }
        binding.lastupdated.text = currentConditions.datetime
        binding.dayLocation.text = address
        binding.temperature.text = Util.printF("%d°c", currentConditions.temp.toInt())
        binding.temperature.setTextColor(Util.getTemperatureColor(currentConditions.temp))
        binding.condition.text = Util.printF("%s", currentConditions.conditions)
        binding.currentIcon.adjustViewBounds = true
        binding.currentIcon.minimumWidth = 60
        binding.currentIcon.minimumHeight = 60
        //new Util.DownloadImageTask(binding.currentIcon).execute(current.condition.icon);
        callPicasso(c, currentConditions.icon, binding.currentIcon)
        binding.label1.text = "Feels:"
        binding.value1.text = Util.printF("%d°c", currentConditions.feelslike.toInt())
        binding.value1.setTextColor(Util.getTemperatureColor(currentConditions.feelslike))
        binding.label2.text = "Humidity:"
        binding.value2.text = Util.printF("%d%%", currentConditions.humidity.toInt())
        binding.value2.setTextColor(Util.getHumidityColor(currentConditions.humidity))
        //binding.label3.setText("Rain:");
        //binding.value3.setText(Util.printF("%dmm", current.precipMm.intValue()));
        binding.label3.text = "Wind (km/h):"
        binding.value3.text = Util.printF(
            "%d",
            currentConditions.windspeed.toInt()
        )
    }

    /**
     * get the list of current day's hours , filtered
     * by last updated epoch
     * @return a list of Hour object
     */
    fun getForecastedHours(): ArrayList<Day.Hour> {
        val today: List<Day.Hour> = days[0].hours
        var firstHour: Day.Hour? = null
        val results = ArrayList<Day.Hour>()
        for (hour in today) {
            if (currentConditions.datetimeEpoch < hour.datetimeEpoch) {
                results.add(hour)
            } else {
                firstHour = hour
            }
        }
        if (firstHour != null) {
            results.add(0, firstHour)
        }
        val tomorrow: List<Day.Hour> = days[1].hours
        for (hour in tomorrow) {
            if (currentConditions.datetimeEpoch < hour.datetimeEpoch) {
                results.add(hour)
            }
        }
        return results
    }

    fun isReady(): Boolean {
        return days.isNotEmpty()
    }

    fun updateFutureViews(binding: FragmentWeatherForecastBinding) {
        // if lastupdated timestamp hasnt changed then dont update
        if (currentConditions.datetime != "" && currentConditions.datetime.compareTo(
                java.lang.String.valueOf(binding.lastupdated.getText())
            ) == 0
        ) {
            // lastupdated in this record is the same as the one in binding
            // no need to update
            return
        }
        binding.lastupdated.setText(currentConditions.datetime)
        binding.weekLocation.setText(address)
    }
}