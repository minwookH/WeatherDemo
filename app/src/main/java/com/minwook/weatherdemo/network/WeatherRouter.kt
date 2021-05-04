package com.minwook.weatherdemo.network

import com.minwook.weatherdemo.model.Location
import com.minwook.weatherdemo.model.LocationSearch
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class WeatherRouter : RetrofitCreator() {

    companion object {
        fun api(): WeatherAPI {
            return create(WeatherAPI::class.java)
        }
    }

    interface WeatherAPI {
        @GET("api/location/search/")
        fun getLocationSearch(@Query("query") query: String): Single<ArrayList<LocationSearch>>

        @GET("api/location/{woeid}/")
        fun getLocation(@Path("woeid") woeid: Int): Single<Location>
    }
}