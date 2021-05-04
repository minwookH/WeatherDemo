package com.minwook.weatherdemo.ui

import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setMargins
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.minwook.weatherdemo.R
import com.minwook.weatherdemo.model.LocationRow
import com.minwook.weatherdemo.view.WeatherView
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity(), MainContract.View, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var tableLayout: TableLayout
    private lateinit var progress: ProgressBar
    private lateinit var refreshLayout: SwipeRefreshLayout

    private val SEARCH_KEYWORD = "se"

    private val presenter by lazy {
        MainPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        presenter.getLocationSearchResult(SEARCH_KEYWORD)
    }

    override fun setData(data: List<LocationRow>) {
        if (refreshLayout.isRefreshing) {
            refreshLayout.isRefreshing = false
        }

        data.forEachIndexed { index, locationRow ->
            tableLayout.addView(setRowView(locationRow), index + 1)
        }

        tableLayout.visibility = View.VISIBLE
        progress.visibility = View.GONE
    }

    override fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun setRowView(lowData: LocationRow): TableRow {
        val tableRow = TableRow(this)
        tableRow.layoutParams = TableRow.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        // 장소
        val localTextView = TextView(this)

        val width = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            46f,
            resources.displayMetrics
        ).toInt()
        val layoutParams = TableRow.LayoutParams(
            width,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        layoutParams.setMargins(5)
        localTextView.layoutParams = layoutParams
        localTextView.text = lowData.local
        localTextView.gravity = Gravity.CENTER
        localTextView.textSize = 16f
        tableRow.addView(localTextView) // tableRow에 view 추가

        // 오늘
        val todayWeatherView = WeatherView(this)
        lowData.today.also { weather ->
            todayWeatherView.apply {
                setState(weather.weather_state_name)
                setImage(weather.weather_state_abbr)
                setTemp(weather.the_temp.roundToInt().toString())
                setHumidity(weather.humidity.roundToInt().toString())
            }
        }
        tableRow.addView(todayWeatherView)

        // 내일
        val tommorowWeatherView = WeatherView(this)
        lowData.tommorow.also { weather ->
            tommorowWeatherView.apply {
                setState(weather.weather_state_name)
                setImage(weather.weather_state_abbr)
                setTemp(weather.the_temp.roundToInt().toString())
                setHumidity(weather.humidity.roundToInt().toString())
            }
        }
        tableRow.addView(tommorowWeatherView)

        return tableRow
    }

    private fun initView() {
        tableLayout = findViewById(R.id.tl_weather)
        progress = findViewById(R.id.progress)
        refreshLayout = findViewById(R.id.refresh)
        refreshLayout.setOnRefreshListener(this)
    }

    override fun onRefresh() {
        tableLayout.visibility = View.GONE
        tableLayout.removeViews(1, tableLayout.childCount - 1)
        presenter.getLocationSearchResult(SEARCH_KEYWORD)
    }

    override fun onDestroy() {
        presenter.disposableAll()
        super.onDestroy()
    }
}