package com.barclays.stockapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.barclays.stockapp.helpers.SharedPreferenceHelper
import com.barclays.stockapp.network.createStockAppService
import com.barclays.stockapp.persistance.AppDatabase
import com.barclays.stockapp.repository.StockListRepository
import java.lang.IllegalArgumentException

class StockListViewModelFactory (private val context: Context) :ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(StockListViewModel::class.java)){
            return StockListViewModel(StockListRepository(createStockAppService(),AppDatabase.getAppDatabase(context)!!.stockDao(),SharedPreferenceHelper(context))) as T
        }
        throw IllegalArgumentException("unknown view model")
    }

}