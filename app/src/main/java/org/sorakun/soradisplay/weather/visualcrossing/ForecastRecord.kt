package org.sorakun.soradisplay.weather.visualcrossing

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.sorakun.soradisplay.weather.ForecastRecordBase

@JsonClass(generateAdapter = true)
data class ForecastRecord (
    @Json(name = "address")
    override var address: String = "", // Tokyo
    @Json(name = "alerts")
    override var alerts: List<Any> = listOf(),
    @Json(name = "currentConditions")
    var currentConditions: CurrentConditions = CurrentConditions(),
    @Json(name = "days")
    var days: List<Day> = listOf(),
    @Json(name = "description")
    var description: String = "", // Similar temperatures continuing with a chance of rain Thursday, Friday & Saturday.
    @Json(name = "latitude")
    override var latitude: Double = 0.0, // 35.6841
    @Json(name = "longitude")
    override var longitude: Double = 0.0, // 139.809
    @Json(name = "queryCost")
    var queryCost: Int = 0, // 1
    @Json(name = "resolvedAddress")
    var resolvedAddress: String = "", // Tokyo, Japan
    @Json(name = "timezone")
    var timezone: String = "", // Asia/Tokyo
    @Json(name = "tzoffset")
    var tzoffset: Int = 0, // 9
) : ForecastRecordBase {

    @JsonClass(generateAdapter = true)
    data class CurrentConditions(
        @Json(name = "cloudcover")
        var cloudcover: Double = 0.0, // 34.5
        @Json(name = "conditions")
        override var conditions: String = "", // Partially cloudy
        @Json(name = "datetime")
        override var datetime: String = "", // 20:00:00
        @Json(name = "datetimeEpoch")
        override var datetimeEpoch: Int = 0, // 1685876400
        @Json(name = "dew")
        var dew: Double = 0.0, // 17.6
        @Json(name = "feelslike")
        override var feelslike: Double = 0.0, // 21.7
        @Json(name = "humidity")
        override var humidity: Double = 0.0, // 77.8
        @Json(name = "icon")
        override var icon: String = "", // partly-cloudy-night
        @Json(name = "moonphase")
        var moonphase: Double = 0.0, // 0.5
        // data surface problem? got a null for precip once, not sure why
        // @Json(name = "precip")
        // var precip: Double = 0.0, // 0
        @Json(name = "precipprob")
        override var precipprob: Double = 0.0, // 0
        @Json(name = "preciptype")
        var preciptype: Any? = null, // null
        @Json(name = "pressure")
        var pressure: Double = 0.0, // 1006.3
        @Json(name = "snow")
        var snow: Double = 0.0, // 0
        @Json(name = "snowdepth")
        var snowdepth: Double = 0.0, // 0
        @Json(name = "solarenergy")
        var solarenergy: Double = 0.0, // 0
        @Json(name = "solarradiation")
        var solarradiation: Double = 0.0, // 0
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
        override var temp: Double = 0.0, // 21.7
        @Json(name = "uvindex")
        var uvindex: Double = 0.0, // 0
        @Json(name = "visibility")
        var visibility: Double = 0.0, // 10.7
        @Json(name = "winddir")
        var winddir: Double = 0.0, // 192
        @Json(name = "windgust")
        var windgust: Any? = null, // null
        @Json(name = "windspeed")
        override var windspeed: Double = 0.0 // 20.7
    ) : ForecastRecordBase.CurrentConditionsBase

    @JsonClass(generateAdapter = true)
    data class Day(
        @Json(name = "cloudcover")
        var cloudcover: Double = 0.0, // 56.6
        @Json(name = "conditions")
        override var conditions: String = "", // Partially cloudy
        @Json(name = "datetime")
        override var datetime: String = "", // 2023-06-04
        @Json(name = "datetimeEpoch")
        override var datetimeEpoch: Int = 0, // 1685804400
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
        override var humidity: Double = 0.0, // 62.9
        @Json(name = "icon")
        override var icon: String = "", // partly-cloudy-day
        @Json(name = "moonphase")
        var moonphase: Double = 0.0, // 0.5
        @Json(name = "precip")
        var precip: Double = 0.0, // 0.7
        @Json(name = "precipcover")
        var precipcover: Double = 0.0, // 12.5
        @Json(name = "precipprob")
        override var precipprob: Double = 0.0, // 9.5
        @Json(name = "preciptype")
        var preciptype: List<String>? = null,
        @Json(name = "pressure")
        var pressure: Double = 0.0, // 1007.1
        @Json(name = "severerisk")
        var severerisk: Double = 0.0, // 10
        @Json(name = "snow")
        var snow: Double = 0.0, // 0
        @Json(name = "snowdepth")
        var snowdepth: Double = 0.0, // 0
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
        override var temp: Double = 0.0, // 22.1
        @Json(name = "tempmax")
        override var tempmax: Double = 0.0, // 25.9
        @Json(name = "tempmin")
        override var tempmin: Double = 0.0, // 18.2
        @Json(name = "uvindex")
        var uvindex: Double = 0.0, // 10
        @Json(name = "visibility")
        var visibility: Double = 0.0, // 12.2
        @Json(name = "winddir")
        var winddir: Double = 0.0, // 200.1
        @Json(name = "windgust")
        var windgust: Double = 0.0, // 42.5
        @Json(name = "windspeed")
        override var windspeed: Double = 0.0 // 20.5
    ) : ForecastRecordBase.DayBase {

        @JsonClass(generateAdapter = true)
        data class Hour(
            @Json(name = "cloudcover")
            var cloudcover: Double = 0.0, // 89.6
            @Json(name = "conditions")
            override var conditions: String = "", // Partially cloudy
            @Json(name = "datetime")
            override var datetime: String = "", // 00:00:00
            @Json(name = "datetimeEpoch")
            override var datetimeEpoch: Int = 0, // 1685804400
            @Json(name = "dew")
            var dew: Double = 0.0, // 9.7
            @Json(name = "feelslike")
            var feelslike: Double = 0.0, // 19.3
            @Json(name = "humidity")
            override var humidity: Double = 0.0, // 53.52
            @Json(name = "icon")
            override var icon: String = "", // partly-cloudy-night
            @Json(name = "precip")
            var precip: Double = 0.0, // 0.1
            @Json(name = "precipprob")
            override var precipprob: Double = 0.0, // 9.5
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
            override var temp: Double = 0.0, // 19.3
            @Json(name = "uvindex")
            var uvindex: Int = 0, // 0
            @Json(name = "visibility")
            var visibility: Double = 0.0, // 10.7
            @Json(name = "winddir")
            var winddir: Double = 0.0, // 145.6
            @Json(name = "windgust")
            var windgust: Double = 0.0, // 30.2
            @Json(name = "windspeed")
            override var windspeed: Double = 0.0 // 14.9
        ) : ForecastRecordBase.DayBase.HourBase
    }

    override fun getForecastedHours(): List<ForecastRecordBase.DayBase.HourBase> {
        val today: List<ForecastRecordBase.DayBase.HourBase> = days[0].hours
        var firstHour: ForecastRecordBase.DayBase.HourBase? = null
        val results = ArrayList<ForecastRecordBase.DayBase.HourBase>()
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
        val tomorrow: List<ForecastRecordBase.DayBase.HourBase> = days[1].hours
        for (hour in tomorrow) {
            if (currentConditions.datetimeEpoch < hour.datetimeEpoch) {
                results.add(hour)
            }
        }
        return results
    }

    override fun getForecastedDays(): List<ForecastRecordBase.DayBase> {
        return days
    }

    override fun getCurrentConditions(): ForecastRecordBase.CurrentConditionsBase {
        return currentConditions
    }

    override fun isReady(): Boolean {
        return days.isNotEmpty()
    }
}