package com.barclays.stockapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barclays.stockapp.model.Stock
import com.barclays.stockapp.repository.StockListRepository
import com.barclays.stockapp.viewstate.Loading
import com.barclays.stockapp.viewstate.Success
import com.barclays.stockapp.viewstate.Error
import com.barclays.stockapp.viewstate.ViewState
import kotlinx.coroutines.launch

class StockListViewModel(private val stockListRepository: StockListRepository) : ViewModel() {

    private val _stockListLiveData: MutableLiveData<ViewState<List<Stock>?>> = MutableLiveData()
    val stockListLiveData: LiveData<ViewState<List<Stock>?>> = _stockListLiveData

    private val stocks = mutableListOf<Stock>()

    fun getStocks(forceRefresh: Boolean = false) {
        if (!forceRefresh && (_stockListLiveData.value is Success || _stockListLiveData.value is Loading)) {
            return
        }

        viewModelScope.launch {
            _stockListLiveData.value = Loading
            val stockList = stockListRepository.getStocksList(forceRefresh)
            if (stockList == null || stockList.isEmpty()) {
                _stockListLiveData.value = Error("There was an error fetching stocks")
            } else {
                stocks.clear()
                stocks.addAll(stockList)
                _stockListLiveData.value = Success(stockList)
            }
        }
    }

    fun filterStockList(filter: String) {
        if (stocks.isNullOrEmpty()) {
            return;
        }

        val filterQuery = filter.trim()

        if (filterQuery.isEmpty()) {
            _stockListLiveData.value = Success(stocks)
            return
        }

        val filteredStocks = stocks.filter { it.name.contains(filterQuery, ignoreCase = true) }
        _stockListLiveData.value = Success(filteredStocks)
    }
}