package org.sorakun.soradisplay.weather

interface ForecastRecordBase {
    var address: String // Tokyo
    var alerts: List<Any>
    var latitude: Double // 35.6841
    var longitude: Double // 139.809

    interface CurrentConditionsBase {
        var conditions: String // Partially cloudy
        var datetime: String // 20:00:00
        var datetimeEpoch: Int // 1685876400
        var feelslike: Double // 21.7
        var humidity: Double // 77.8
        var icon: String // partly-cloudy-night
        var precipprob: Double // 0
        var temp: Double // 21.7
        var windspeed: Double // 20.7
    }

    interface DayBase {
        var conditions: String // Partially cloudy
        var datetime: String // 2023-06-04
        var datetimeEpoch: Int // 1685804400
        var humidity: Double // 62.9
        var icon: String // partly-cloudy-day
        var precipprob: Double // 9.5
        var temp: Double // 22.1
        var tempmax: Double // 25.9
        var tempmin: Double // 18.2
        var windspeed: Double // 20.5

        interface HourBase {
            var conditions: String // Partially cloudy
            var datetime: String // 00:00:00
            var datetimeEpoch: Int // 1685804400
            var humidity: Double // 53.52
            var icon: String // partly-cloudy-night
            var precipprob: Double // 9.5
            var temp: Double // 19.3
            var windspeed: Double // 14.9
        }
    }

    /**
     * get the list of current day's hours , filtered
     * by last updated epoch
     * @return a list of Hour object
     */
    fun getForecastedHours() : List<DayBase.HourBase>

    fun getForecastedDays() : List<DayBase>

    fun getCurrentConditions() : CurrentConditionsBase

    fun isReady(): Boolean
}