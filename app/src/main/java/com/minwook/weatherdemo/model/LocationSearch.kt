package com.minwook.weatherdemo.model

data class LocationSearch(
    val title: String,
    val location_type: String,
    val woeid: Int,
    val latt_long: String,
    val distance: Int
)