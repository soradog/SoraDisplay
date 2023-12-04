package org.sorakun.soradisplay.weather.tomorrowio


import android.app.Application
import android.content.res.Resources
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.sorakun.soradisplay.weather.ForecastRecordBase
import org.sorakun.soradisplay.weather.ServiceFactory
import java.io.BufferedReader
import java.io.InputStreamReader

@JsonClass(generateAdapter = true)
data class ForecastRecord(
    @Json(name = "location")
    var location: Location = Location(),
    @Json(name = "timelines")
    var timelines: Timelines = Timelines()
) : ForecastRecordBase {

    @JsonClass(generateAdapter = true)
    data class Location(
        @Json(name = "lat")
        var lat: Double = 0.0, // 35.681266784668
        @Json(name = "lon")
        var lon: Double = 0.0, // 139.757659912109
        @Json(name = "name")
        var name: String = "", // 東京都, 日本
        @Json(name = "type")
        var type: String = "" // administrative
    )

    @JsonClass(generateAdapter = true)
    data class Timelines(
        @Json(name = "daily")
        var daily: List<Daily> = listOf(),
        @Json(name = "hourly")
        var hourly: List<Hourly> = listOf()
    ) {
        @JsonClass(generateAdapter = true)
        data class Daily(
            @Json(name = "time")
            var time: String = "", // 2023-06-10T00:00:00Z
            @Json(name = "values")
            var values: Values = Values(),
            override var conditions: String = ServiceFactory.getWeatherCondition(values.weatherCodeMax),
            override var datetime: String = time,
            override var datetimeEpoch: Int = 0,
            override var humidity: Double = values.humidityAvg,
            override var icon: String = values.weatherCodeMax.toString(),
            override var precipprob: Double = values.precipitationProbabilityAvg,
            override var temp: Double = values.temperatureAvg,
            override var tempmax: Double = values.temperatureMax,
            override var tempmin: Double = values.temperatureMin,
            override var windspeed: Double = values.windSpeedAvg,
        ) : ForecastRecordBase.DayBase {

            @JsonClass(generateAdapter = true)
            data class Values(
                @Json(name = "cloudBaseAvg")
                var cloudBaseAvg: Double = 0.0, // 2.19
                @Json(name = "cloudBaseMax")
                var cloudBaseMax: Double = 0.0, // 6.31
                @Json(name = "cloudBaseMin")
                var cloudBaseMin: Double = 0.0, // 0.2
                @Json(name = "cloudCeilingAvg")
                var cloudCeilingAvg: Double = 0.0, // 2.25
                @Json(name = "cloudCeilingMax")
                var cloudCeilingMax: Double = 0.0, // 6.62
                @Json(name = "cloudCeilingMin")
                var cloudCeilingMin: Double = 0.0, // 0.24
                @Json(name = "cloudCoverAvg")
                var cloudCoverAvg: Double = 0.0, // 77.46
                @Json(name = "cloudCoverMax")
                var cloudCoverMax: Double = 0.0, // 100
                @Json(name = "cloudCoverMin")
                var cloudCoverMin: Double = 0.0, // 36.72
                @Json(name = "dewPointAvg")
                var dewPointAvg: Double = 0.0, // 19.89
                @Json(name = "dewPointMax")
                var dewPointMax: Double = 0.0, // 21.31
                @Json(name = "dewPointMin")
                var dewPointMin: Double = 0.0, // 19.1
                @Json(name = "evapotranspirationAvg")
                var evapotranspirationAvg: Double = 0.0, // 0.103
                @Json(name = "evapotranspirationMax")
                var evapotranspirationMax: Double = 0.0, // 0.466
                @Json(name = "evapotranspirationMin")
                var evapotranspirationMin: Double = 0.0, // 0.014
                @Json(name = "evapotranspirationSum")
                var evapotranspirationSum: Double = 0.0, // 2.479
                @Json(name = "freezingRainIntensityAvg")
                var freezingRainIntensityAvg: Double = 0.0, // 0
                @Json(name = "freezingRainIntensityMax")
                var freezingRainIntensityMax: Double = 0.0, // 0
                @Json(name = "freezingRainIntensityMin")
                var freezingRainIntensityMin: Double = 0.0, // 0
                @Json(name = "humidityAvg")
                var humidityAvg: Double = 0.0, // 82.94
                @Json(name = "humidityMax")
                var humidityMax: Double = 0.0, // 97.69
                @Json(name = "humidityMin")
                var humidityMin: Double = 0.0, // 83.17
                @Json(name = "iceAccumulationAvg")
                var iceAccumulationAvg: Double = 0.0, // 0
                @Json(name = "iceAccumulationLweAvg")
                var iceAccumulationLweAvg: Double = 0.0, // 0
                @Json(name = "iceAccumulationLweMax")
                var iceAccumulationLweMax: Double = 0.0, // 0
                @Json(name = "iceAccumulationLweMin")
                var iceAccumulationLweMin: Double = 0.0, // 0
                @Json(name = "iceAccumulationLweSum")
                var iceAccumulationLweSum: Double = 0.0, // 0
                @Json(name = "iceAccumulationMax")
                var iceAccumulationMax: Double = 0.0, // 0
                @Json(name = "iceAccumulationMin")
                var iceAccumulationMin: Double = 0.0, // 0
                @Json(name = "iceAccumulationSum")
                var iceAccumulationSum: Double = 0.0, // 0
                @Json(name = "moonriseTime")
                var moonriseTime: String = "", // 2023-06-10T15:07:06Z
                @Json(name = "moonsetTime")
                var moonsetTime: String = "", // 2023-06-10T01:38:47Z
                @Json(name = "precipitationProbabilityAvg")
                var precipitationProbabilityAvg: Double = 0.0, // 13.5
                @Json(name = "precipitationProbabilityMax")
                var precipitationProbabilityMax: Double = 0.0, // 70
                @Json(name = "precipitationProbabilityMin")
                var precipitationProbabilityMin: Double = 0.0, // 0
                @Json(name = "pressureSurfaceLevelAvg")
                var pressureSurfaceLevelAvg: Double = 0.0, // 1011.37
                @Json(name = "pressureSurfaceLevelMax")
                var pressureSurfaceLevelMax: Double = 0.0, // 1013.28
                @Json(name = "pressureSurfaceLevelMin")
                var pressureSurfaceLevelMin: Double = 0.0, // 1009.29
                @Json(name = "rainAccumulationAvg")
                var rainAccumulationAvg: Double = 0.0, // 0.77
                @Json(name = "rainAccumulationLweAvg")
                var rainAccumulationLweAvg: Double = 0.0, // 0.77
                @Json(name = "rainAccumulationLweMax")
                var rainAccumulationLweMax: Double = 0.0, // 5.49
                @Json(name = "rainAccumulationLweMin")
                var rainAccumulationLweMin: Double = 0.0, // 0.02
                @Json(name = "rainAccumulationMax")
                var rainAccumulationMax: Double = 0.0, // 5.49
                @Json(name = "rainAccumulationMin")
                var rainAccumulationMin: Double = 0.0, // 0.02
                @Json(name = "rainAccumulationSum")
                var rainAccumulationSum: Double = 0.0, // 18.39
                @Json(name = "rainIntensityAvg")
                var rainIntensityAvg: Double = 0.0, // 0.54
                @Json(name = "rainIntensityMax")
                var rainIntensityMax: Double = 0.0, // 4.36
                @Json(name = "rainIntensityMin")
                var rainIntensityMin: Double = 0.0, // 0.02
                @Json(name = "sleetAccumulationAvg")
                var sleetAccumulationAvg: Double = 0.0, // 0
                @Json(name = "sleetAccumulationLweAvg")
                var sleetAccumulationLweAvg: Double = 0.0, // 0
                @Json(name = "sleetAccumulationLweMax")
                var sleetAccumulationLweMax: Double = 0.0, // 0
                @Json(name = "sleetAccumulationLweMin")
                var sleetAccumulationLweMin: Double = 0.0, // 0
                @Json(name = "sleetAccumulationLweSum")
                var sleetAccumulationLweSum: Double = 0.0, // 0
                @Json(name = "sleetAccumulationMax")
                var sleetAccumulationMax: Double = 0.0, // 0
                @Json(name = "sleetAccumulationMin")
                var sleetAccumulationMin: Double = 0.0, // 0
                @Json(name = "sleetIntensityAvg")
                var sleetIntensityAvg: Double = 0.0, // 0
                @Json(name = "sleetIntensityMax")
                var sleetIntensityMax: Double = 0.0, // 0
                @Json(name = "sleetIntensityMin")
                var sleetIntensityMin: Double = 0.0, // 0
                @Json(name = "snowAccumulationAvg")
                var snowAccumulationAvg: Double = 0.0, // 0
                @Json(name = "snowAccumulationLweAvg")
                var snowAccumulationLweAvg: Double = 0.0, // 0
                @Json(name = "snowAccumulationLweMax")
                var snowAccumulationLweMax: Double = 0.0, // 0
                @Json(name = "snowAccumulationLweMin")
                var snowAccumulationLweMin: Double = 0.0, // 0
                @Json(name = "snowAccumulationLweSum")
                var snowAccumulationLweSum: Double = 0.0, // 0
                @Json(name = "snowAccumulationMax")
                var snowAccumulationMax: Double = 0.0, // 0
                @Json(name = "snowAccumulationMin")
                var snowAccumulationMin: Double = 0.0, // 0
                @Json(name = "snowAccumulationSum")
                var snowAccumulationSum: Double = 0.0, // 0
                @Json(name = "snowIntensityAvg")
                var snowIntensityAvg: Double = 0.0, // 0
                @Json(name = "snowIntensityMax")
                var snowIntensityMax: Double = 0.0, // 0
                @Json(name = "snowIntensityMin")
                var snowIntensityMin: Double = 0.0, // 0
                @Json(name = "sunriseTime")
                var sunriseTime: String = "", // 2023-06-09T19:36:00Z
                @Json(name = "sunsetTime")
                var sunsetTime: String = "", // 2023-06-10T09:44:00Z
                @Json(name = "temperatureApparentAvg")
                var temperatureApparentAvg: Double = 0.0, // 22.53
                @Json(name = "temperatureApparentMax")
                var temperatureApparentMax: Double = 0.0, // 26.5
                @Json(name = "temperatureApparentMin")
                var temperatureApparentMin: Double = 0.0, // 20.13
                @Json(name = "temperatureAvg")
                var temperatureAvg: Double = 0.0, // 22.53
                @Json(name = "temperatureMax")
                var temperatureMax: Double = 0.0, // 26.5
                @Json(name = "temperatureMin")
                var temperatureMin: Double = 0.0, // 20.13
                @Json(name = "uvHealthConcernAvg")
                var uvHealthConcernAvg: Double = 0.0, // 0
                @Json(name = "uvHealthConcernMax")
                var uvHealthConcernMax: Double = 0.0, // 1
                @Json(name = "uvHealthConcernMin")
                var uvHealthConcernMin: Double = 0.0, // 0
                @Json(name = "uvIndexAvg")
                var uvIndexAvg: Double = 0.0, // 1
                @Json(name = "uvIndexMax")
                var uvIndexMax: Double = 0.0, // 4
                @Json(name = "uvIndexMin")
                var uvIndexMin: Double = 0.0, // 0
                @Json(name = "visibilityAvg")
                var visibilityAvg: Double = 0.0, // 11.22
                @Json(name = "visibilityMax")
                var visibilityMax: Double = 0.0, // 11.6
                @Json(name = "visibilityMin")
                var visibilityMin: Double = 0.0, // 1.68
                @Json(name = "weatherCodeMax")
                var weatherCodeMax: Int = 0, // 1001
                @Json(name = "weatherCodeMin")
                var weatherCodeMin: Double = 0.0, // 1001
                @Json(name = "windDirectionAvg")
                var windDirectionAvg: Double = 0.0, // 152.03
                @Json(name = "windGustAvg")
                var windGustAvg: Double = 0.0, // 1.62
                @Json(name = "windGustMax")
                var windGustMax: Double = 0.0, // 4.63
                @Json(name = "windGustMin")
                var windGustMin: Double = 0.0, // 2.36
                @Json(name = "windSpeedAvg")
                var windSpeedAvg: Double = 0.0, // 0.66
                @Json(name = "windSpeedMax")
                var windSpeedMax: Double = 0.0, // 3.9
                @Json(name = "windSpeedMin")
                var windSpeedMin: Double = 0.0 // 0.77
            )
        }

        @JsonClass(generateAdapter = true)
        data class Hourly(
            @Json(name = "time")
            var time: String = "", // 2023-06-10T12:00:00Z
            @Json(name = "values")
            var values: Values = Values(),
            override var conditions: String = ServiceFactory.getWeatherCondition(values.weatherCode),
            override var datetime: String = time,
            override var datetimeEpoch: Int = 0,
            override var humidity: Double = values.humidity,
            override var icon: String = values.weatherCode.toString(),
            override var precipprob: Double = values.precipitationProbability,
            override var temp: Double = values.temperature,
            override var windspeed: Double = values.windSpeed
        ) : ForecastRecordBase.DayBase.HourBase {

            @JsonClass(generateAdapter = true)
            data class Values(
                @Json(name = "cloudBase")
                var cloudBase: Double = 0.0, // 0.43
                @Json(name = "cloudCeiling")
                var cloudCeiling: Double? = null, // 0.43
                @Json(name = "cloudCover")
                var cloudCover: Double = 0.0, // 88.48
                @Json(name = "dewPoint")
                var dewPoint: Double = 0.0, // 19.5
                @Json(name = "evapotranspiration")
                var evapotranspiration: Double = 0.0, // 0.038
                @Json(name = "freezingRainIntensity")
                var freezingRainIntensity: Double = 0.0, // 0
                @Json(name = "humidity")
                var humidity: Double = 0.0, // 90.89
                @Json(name = "iceAccumulation")
                var iceAccumulation: Int? = null, // 0
                @Json(name = "iceAccumulationLwe")
                var iceAccumulationLwe: Int? = null, // 0
                @Json(name = "precipitationProbability")
                var precipitationProbability: Double = 0.0, // 0
                @Json(name = "pressureSurfaceLevel")
                var pressureSurfaceLevel: Double = 0.0, // 1013.28
                @Json(name = "rainAccumulation")
                var rainAccumulation: Double = 0.0, // 0.25
                @Json(name = "rainAccumulationLwe")
                var rainAccumulationLwe: Double? = null, // 0.25
                @Json(name = "rainIntensity")
                var rainIntensity: Double = 0.0, // 0.25
                @Json(name = "sleetAccumulation")
                var sleetAccumulation: Double = 0.0, // 0
                @Json(name = "sleetAccumulationLwe")
                var sleetAccumulationLwe: Int? = null, // 0
                @Json(name = "sleetIntensity")
                var sleetIntensity: Double = 0.0, // 0
                @Json(name = "snowAccumulation")
                var snowAccumulation: Double = 0.0, // 0
                @Json(name = "snowAccumulationLwe")
                var snowAccumulationLwe: Int? = null, // 0
                @Json(name = "snowIntensity")
                var snowIntensity: Double = 0.0, // 0
                @Json(name = "temperature")
                var temperature: Double = 0.0, // 21.19
                @Json(name = "temperatureApparent")
                var temperatureApparent: Double = 0.0, // 21.19
                @Json(name = "uvHealthConcern")
                var uvHealthConcern: Int? = null, // 0
                @Json(name = "uvIndex")
                var uvIndex: Int? = null, // 0
                @Json(name = "visibility")
                var visibility: Double = 0.0, // 13.87
                @Json(name = "weatherCode")
                var weatherCode: Int = 0, // 1001
                @Json(name = "windDirection")
                var windDirection: Double = 0.0, // 118.13
                @Json(name = "windGust")
                var windGust: Double = 0.0, // 1.48
                @Json(name = "windSpeed")
                var windSpeed: Double = 0.0 // 1.48
            )
        }
    }

    override var address: String
        get() = location.name
        set(value) {location.name = value}
    override var alerts: List<Any>
        get() = listOf()
        set(value) {}
    override var latitude: Double
        get() = location.lat
        set(value) {location.lat = value}
    override var longitude: Double
        get() = location.lon
        set(value) {location.lon = value}

    override fun getForecastedHours(): List<ForecastRecordBase.DayBase.HourBase> {
        return timelines.hourly
    }

    override fun getForecastedDays(): List<ForecastRecordBase.DayBase> {
        return timelines.daily
    }

    override fun getCurrentConditions(): ForecastRecordBase.CurrentConditionsBase {
        TODO("Not yet implemented")
    }

    override fun isReady(): Boolean {
        TODO("Not yet implemented")
    }
}
