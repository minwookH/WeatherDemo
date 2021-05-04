package com.minwook.weatherdemo.utils

class WeatherUtil {

    companion object {

        private fun getAbbreviation(state: String): String {
            return when (state) {
                "Snow" -> "sn"
                "Sleet" -> "sl"
                "Hail" -> "h"
                "Thunderstorm" -> "t"
                "Heavy Rain" -> "hr"
                "Light Rain" -> "lr"
                "Showers" -> "s"
                "Heavy Cloud" -> "hc"
                "Light Cloud" -> "lc"
                "Clear" -> "c"
                else -> ""
            }
        }

        fun getWeatherImagePath(state: String): String {
            return "/static/img/weather/png/64/${getAbbreviation(state)}.png"
        }
    }
}