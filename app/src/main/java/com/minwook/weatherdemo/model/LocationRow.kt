package com.minwook.weatherdemo.model

data class LocationRow(
    val local: String,
    val today: ConsolidatedWeather,
    val tommorow: ConsolidatedWeather
)