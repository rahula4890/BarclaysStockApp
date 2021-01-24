package com.barclays.stockapp.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.barclays.stockapp.R
import com.barclays.stockapp.extensions.convertPriceToString
import com.barclays.stockapp.model.Stock
import com.barclays.stockapp.view.ui.stock_detail.StockDetailActivity
import kotlinx.android.synthetic.main.stock_item.view.*

class StocksListAdapter(private val context: Context) :
    RecyclerView.Adapter<StocksListItemViewHolder>() {

    private var listOfStocks = listOf<Stock>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StocksListItemViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.stock_item, parent, false)
        return StocksListItemViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listOfStocks.size
    }

    override fun onBindViewHolder(holder: StocksListItemViewHolder, position: Int) {
        val stockViewHolder = holder
        stockViewHolder.bindView(listOfStocks[position])
        stockViewHolder.itemView.setOnClickListener { item ->
            var stockName = item.stock_name.text
            Toast.makeText(context, "Stock Clicked is : $stockName", Toast.LENGTH_SHORT).show()

            val intent = Intent(context, StockDetailActivity::class.java)
            intent.putExtra("stock_symbol", listOfStocks[position].symbol)
            context.startActivity(intent)
        }
    }

    fun setStockList(listOfStocks: List<Stock>?) {
        if (listOfStocks == null) return
        this.listOfStocks = listOfStocks
        notifyDataSetChanged()
    }
}

class StocksListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindView(stockModel: Stock) {
        itemView.stock_name.text = stockModel.name
        itemView.stock_symbol.text = stockModel.symbol
        itemView.stock_price.text = stockModel.price.convertPriceToString()
    }
}
