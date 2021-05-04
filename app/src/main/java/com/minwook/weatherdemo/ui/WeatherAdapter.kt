package com.minwook.weatherdemo.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.minwook.weatherdemo.R
import com.minwook.weatherdemo.model.HeaderRow
import com.minwook.weatherdemo.model.LocationRow
import kotlin.math.roundToInt

class WeatherAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class ViewType {
        HEADER,
        WEATHER
    }

    private var list: ArrayList<Any> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.HEADER.ordinal -> {
                val inflate = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_weather_header, parent, false)
                HeaderViewHolder(inflate)
            }
            else -> {
                val inflate = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_weather_default, parent, false)
                WeatherViewHolder(inflate)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            ViewType.HEADER.ordinal -> (holder as HeaderViewHolder).bind(list[position] as HeaderRow)
            else -> (holder as WeatherViewHolder).bind(list[position] as LocationRow)
        }
    }

    override fun getItemCount(): Int  = list.size

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> ViewType.HEADER.ordinal
            else -> ViewType.WEATHER.ordinal
        }
    }

    fun setList(list: ArrayList<Any>) {
        this.list = list
    }

    fun addList(list: ArrayList<Any>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

    //" \u2109"
    inner class HeaderViewHolder(private var view: View): RecyclerView.ViewHolder(view) {
        private val localTextView = view.findViewById<TextView>(R.id.tv_header_local)
        private val todayTextView = view.findViewById<TextView>(R.id.tv_header_today)
        private val tomorrowTextView = view.findViewById<TextView>(R.id.tv_header_tomorrow)

        fun bind(data: HeaderRow) {
            with(data) {
                localTextView.text = local
                todayTextView.text = today
                tomorrowTextView.text = tomorrow
            }
        }
    }

    inner class WeatherViewHolder(private var view: View): RecyclerView.ViewHolder(view) {
        private val localView = view.findViewById<TextView>(R.id.tv_local)

        private val todayImage = view.findViewById<ImageView>(R.id.iv_today_weather)
        private val todayState = view.findViewById<TextView>(R.id.tv_today_state)
        private val todayTemp = view.findViewById<TextView>(R.id.tv_today_temp)
        private val todayHumidity = view.findViewById<TextView>(R.id.tv_today_humidity)

        private val tomorrowImage = view.findViewById<ImageView>(R.id.iv_tomorrow_weather)
        private val tomorrowState = view.findViewById<TextView>(R.id.tv_tomorrow_state)
        private val tomorrowTemp = view.findViewById<TextView>(R.id.tv_tomorrow_temp)
        private val tomorrowHumidity = view.findViewById<TextView>(R.id.tv_tomorrow_humidity)

        fun bind(data: LocationRow) {
            with(data) {
                localView.text = local

                // Today
                todayState.text = today.weather_state_name
                todayTemp.text = "${today.the_temp.roundToInt()}" + "\u2103"
                todayHumidity.text = "${today.humidity.roundToInt()}%"

                Glide.with(view.context)
                    .load("https://www.metaweather.com/static/img/weather/png/64/${today.weather_state_abbr}.png")
                    .into(todayImage)

                // Tomorrow
                tomorrowState.text = tommorow.weather_state_name
                tomorrowTemp.text = "${tommorow.the_temp.roundToInt()}" + "\u2103"
                tomorrowHumidity.text = "${tommorow.humidity.roundToInt()}%"

                Glide.with(view.context)
                    .load("https://www.metaweather.com/static/img/weather/png/64/${tommorow.weather_state_abbr}.png")
                    .into(tomorrowImage)
            }
        }
    }
}