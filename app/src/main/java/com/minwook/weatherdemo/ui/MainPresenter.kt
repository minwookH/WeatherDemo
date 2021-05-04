package com.minwook.weatherdemo.ui

import android.util.Log
import com.minwook.weatherdemo.model.LocationRow
import com.minwook.weatherdemo.network.WeatherRouter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainPresenter(private val view: MainContract.View) : MainContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun getLocationSearchResult(query: String) {
        compositeDisposable.add(
            WeatherRouter.api().getLocationSearch(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (!it.isNullOrEmpty()) {
                        val map = it.map { it.title }
                        Log.d("weather", "getLocationSearchResult : $map")

                        getLocationInfo(it.map { it.woeid })
                    } else {
                        view.showToast("Search List Empty")
                    }
                }, {
                    view.showToast(it.localizedMessage)
                    Log.e("weather", "getLocationSearchResult error : ${it.localizedMessage}")
                })
        )
    }

    override fun getLocationInfo(woeIdList: List<Int>) {
        compositeDisposable.add(
            Observable.fromIterable(woeIdList)
                .concatMapSingle {
                    WeatherRouter.api().getLocation(it)
                }
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (!it.isNullOrEmpty()) {
                        val map = it.map {
                            LocationRow(
                                it.title,
                                it.consolidated_weather[0],
                                it.consolidated_weather[1]
                            )
                        }

                        view.setData(map)
                    } else {
                        view.showToast("LocationInfo Empty")
                    }
                }, {
                    view.showToast(it.localizedMessage)
                    Log.e("weather", "getLocationInfo error : ${it.localizedMessage}")
                })
        )
    }

    fun disposableAll() {
        compositeDisposable.dispose()
    }
}