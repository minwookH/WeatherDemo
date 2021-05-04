package com.minwook.weatherdemo.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.minwook.weatherdemo.R

class WeatherView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var weatherImage: ImageView
    private var weatherState: TextView
    private var weatherTemp: TextView
    private var weatherHumidity: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.item_weather, this, true)
        /*val inflater: LayoutInflater =
            getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_weather, this, false)
        addView(view)*/

        weatherImage = findViewById(R.id.iv_weather)
        weatherState = findViewById(R.id.tv_state)
        weatherTemp = findViewById(R.id.tv_temp)
        weatherHumidity = findViewById(R.id.tv_humidity)
    }

    fun setImage(abbreviation: String) {

        Glide.with(context)
            .load("https://www.metaweather.com" + "/static/img/weather/png/64/${abbreviation}.png")
            .into(weatherImage)
    }

    fun setState(state: String) {
        weatherState.text = state
    }

    fun setTemp(temp: String) {
        weatherTemp.text = temp
    }

    fun setHumidity(humidity: String) {
        weatherHumidity.text = humidity
    }
}