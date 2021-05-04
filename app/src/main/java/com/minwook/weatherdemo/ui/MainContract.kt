package com.minwook.weatherdemo.ui

import com.minwook.weatherdemo.model.LocationRow

interface MainContract {

    interface View {

        fun setData(data: List<LocationRow>)

        fun showToast(text: String)

    }

    interface Presenter {

        fun getLocationSearchResult(query: String)

        fun getLocationInfo(woeIdList: List<Int>)

    }

}