package com.barclays.stockapp.view.ui.stock_detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.barclays.stockapp.R
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.*
import kotlinx.android.synthetic.main.activity_stock_detail.*

class StockDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_detail)
        setStockLiveChart()
    }

    private fun setStockLiveChart() {
        val entries = ArrayList<Entry>()
        entries.add(Entry(1f, 10f))
        entries.add(Entry(2f, 2f))
        entries.add(Entry(3f, 7f))
        entries.add(Entry(4f, 20f))
        entries.add(Entry(5f, 16f))

        val vl = LineDataSet(entries, "Barclays")

        vl.setDrawValues(false)
        vl.setDrawFilled(true)
        vl.lineWidth = 3f
        vl.fillColor = R.color.purple_700
        vl.fillAlpha = R.color.red

        lineChart.xAxis.labelRotationAngle = 0f
        lineChart.data = LineData(vl)

        lineChart.axisRight.isEnabled = false
        //lineChart.xAxis.axisMaximum = j+0.1f

        lineChart.setTouchEnabled(true)
        lineChart.setPinchZoom(true)

        lineChart.description.text = "Days"
        lineChart.description.textColor = R.color.teal_700

        lineChart.setNoDataText("No stock yet!")

        lineChart.animateX(1800, Easing.EaseInExpo)
    }
}