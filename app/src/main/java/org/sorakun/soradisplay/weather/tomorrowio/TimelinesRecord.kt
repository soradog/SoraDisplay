package org.sorakun.soradisplay.weather.tomorrowio


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TimelinesRecord(
    @Json(name = "data")
    var `data`: Data = Data()
) {
    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "timelines")
        var timelines: List<Timeline> = listOf(),
        @Json(name = "warnings")
        var warnings: List<Warning> = listOf()
    ) {
        @JsonClass(generateAdapter = true)
        data class Timeline(
            @Json(name = "endTime")
            var endTime: String = "", // 2023-07-28T06:00:00+09:00
            @Json(name = "intervals")
            var intervals: List<Interval> = listOf(),
            @Json(name = "startTime")
            var startTime: String = "", // 2023-07-23T06:00:00+09:00
            @Json(name = "timestep")
            var timestep: String = "" // 1d
        ) {
            @JsonClass(generateAdapter = true)
            data class Interval(
                @Json(name = "startTime")
                var startTime: String = "", // 2023-07-23T06:00:00+09:00
                @Json(name = "values")
                var values: Values = Values()
            ) {
                @JsonClass(generateAdapter = true)
                data class Values(
                    @Json(name = "humidity")
                    var humidity: Double = 0.0, // 95.73
                    @Json(name = "precipitationProbability")
                    var precipitationProbability: Int = 0, // 0
                    @Json(name = "temperature")
                    var temperature: Double = 0.0, // 33.4
                    @Json(name = "temperatureApparent")
                    var temperatureApparent: Double = 0.0, // 36.06
                    @Json(name = "temperatureMax")
                    var temperatureMax: Double = 0.0, // 33.4
                    @Json(name = "temperatureMin")
                    var temperatureMin: Double = 0.0, // 23.05
                    @Json(name = "weatherCode")
                    var weatherCode: Int = 0, // 1000
                    @Json(name = "windSpeed")
                    var windSpeed: Double = 0.0 // 4.19
                )
            }
        }

        @JsonClass(generateAdapter = true)
        data class Warning(
            @Json(name = "code")
            var code: Int = 0, // 246009
            @Json(name = "message")
            var message: String = "", // The timestep is not supported in full for the time range requested.
            @Json(name = "meta")
            var meta: Meta = Meta(),
            @Json(name = "type")
            var type: String = "" // Missing Time Range
        ) {
            @JsonClass(generateAdapter = true)
            data class Meta(
                @Json(name = "from")
                var from: String = "", // -12h
                @Json(name = "timestep")
                var timestep: String = "", // current
                @Json(name = "to")
                var to: String = "" // +12h
            )
        }
    }
}