package com.minwook.weatherdemo.model

data class Location(
    val title: String,
    val location_type: String,
    val time: String,
    val sun_rise: String,
    val sun_set: String,
    val timezone_name: String,
    val parent: LocationParent,
    val sources: ArrayList<LocationSources>,
    val woeid: Int,
    val latt_long: String,
    val timezone: String,
    val consolidated_weather: ArrayList<ConsolidatedWeather>
)

data class LocationParent(
    val title: String,
    val location_type: String,
    val woeid: Int,
    val latt_long: String
)

data class LocationSources(
    val title: String,
    val slug: String,
    val url: String,
    val crawl_rate: Int
)

data class ConsolidatedWeather(
    val id: Float,
    val weather_state_name: String,
    val weather_state_abbr: String,
    val wind_direction_compass: String,
    val created: String,
    val applicable_date: String,
    val min_temp: Float,
    val max_temp: Float,
    val the_temp: Float,
    val wind_speed: Float,
    val wind_direction: Float,
    val air_pressure: Float,
    val humidity: Float,
    val visibility: Float,
    val predictability: Int
)