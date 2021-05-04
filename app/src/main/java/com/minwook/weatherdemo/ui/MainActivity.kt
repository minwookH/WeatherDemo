package com.minwook.weatherdemo.ui

import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setMargins
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.minwook.weatherdemo.R
import com.minwook.weatherdemo.model.HeaderRow
import com.minwook.weatherdemo.model.LocationRow
import com.minwook.weatherdemo.view.WeatherView
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity(), MainContract.View, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var progress: ProgressBar
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var weatherRecyclerView: RecyclerView
    private lateinit var adapter: WeatherAdapter

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

    private fun initView() {
        weatherRecyclerView = findViewById(R.id.rv_weather)
        progress = findViewById(R.id.progress)
        refreshLayout = findViewById(R.id.refresh)
        refreshLayout.setOnRefreshListener(this)

        adapter = WeatherAdapter()
        weatherRecyclerView.adapter = adapter
        weatherRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun setData(data: List<LocationRow>) {
        if (refreshLayout.isRefreshing) {
            refreshLayout.isRefreshing = false
        }

        val list = arrayListOf<Any>()
        list.add(HeaderRow("Local", "Today", "Tomorrow"))
        list.addAll(data)

        adapter.addList(list)
        weatherRecyclerView.visibility = View.VISIBLE
        progress.visibility = View.GONE
    }

    override fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun onRefresh() {
        adapter.clear()
        weatherRecyclerView.visibility = View.GONE
        presenter.getLocationSearchResult(SEARCH_KEYWORD)
    }

    override fun onDestroy() {
        presenter.disposableAll()
        super.onDestroy()
    }
}