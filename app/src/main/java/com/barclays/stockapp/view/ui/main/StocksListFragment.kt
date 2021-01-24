package com.barclays.stockapp.view.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.barclays.stockapp.R
import com.barclays.stockapp.model.Stock
import com.barclays.stockapp.view.adapter.StocksListAdapter
import com.barclays.stockapp.viewmodel.StockListViewModel
import com.barclays.stockapp.viewmodel.StockListViewModelFactory
import com.barclays.stockapp.viewstate.Loading
import com.barclays.stockapp.viewstate.Success
import com.barclays.stockapp.viewstate.Error
import com.barclays.stockapp.viewstate.ViewState

class StocksListFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    private val viewModelFactory by lazy { StockListViewModelFactory(requireContext()) }
    private val viewModel: StockListViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stocks_list, container, false)

        val stockRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.stock_refresh_layout)
        stockRefreshLayout.setOnRefreshListener(this)

        val stocksList: RecyclerView = view.findViewById(R.id.stocksList)
        stocksList.layoutManager = LinearLayoutManager(requireContext())

        val stocksListAdapter = StocksListAdapter(requireContext())
        stocksList.adapter = stocksListAdapter

        val stocksListObserver = Observer<ViewState<List<Stock>?>> { viewState ->
            when (viewState) {
                is Success -> {
                    stockRefreshLayout.isRefreshing = false
                    stocksListAdapter.setStockList(viewState.data as List<Stock>?)
                }
                is Error -> {
                    stockRefreshLayout.isRefreshing = false
                    Toast.makeText(requireContext(), viewState.errMsg, Toast.LENGTH_SHORT).show()
                }
                is Loading -> {
                    stockRefreshLayout.isRefreshing = true
                }
            }
        }

        viewModel.stockListLiveData.observe(viewLifecycleOwner, stocksListObserver)
        viewModel.getStocks()

        val searchEditText = view.findViewById<EditText>(R.id.search_edit_text)
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editableText: Editable?) {
                if (editableText == null) {
                    return
                }

                val filterText = editableText.toString().trim()
                viewModel.filterStockList(filterText)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })

        return view
    }

    override fun onRefresh() {
        viewModel.getStocks(true)
    }
}