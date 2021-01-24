package com.barclays.stockapp.repository

import android.util.Log
import com.barclays.stockapp.helpers.SharedPreferenceHelper
import com.barclays.stockapp.model.Stock
import com.barclays.stockapp.network.IStockAppService
import com.barclays.stockapp.persistance.StockDao
import java.lang.Exception

class StockListRepository(
    private val iStockAppService: IStockAppService,
    private val stockDao: StockDao,
    private val sharedPreferenceHelper: SharedPreferenceHelper
) : IStockListRepository {

    companion object {
        private const val NETWORK_TIMESTAMP_KEY = "network_call_time_stamp"
        private const val TWENTY_FOUR_HOURS = 24 * 60 * 60 * 1000
    }

    private val TAG = StockListRepository::class.java.simpleName

    override suspend fun getStocksList(forceApi: Boolean): List<Stock>? {
        val currentTimeStamp: Long = System.currentTimeMillis() / 1000
        val sharePreferenceTSValue = sharedPreferenceHelper.getLong(NETWORK_TIMESTAMP_KEY, 0L)
        val hoursPassed = currentTimeStamp - sharePreferenceTSValue

        val shouldUseApi = forceApi || hoursPassed >= TWENTY_FOUR_HOURS

        return try {
            if (shouldUseApi) {
                sharedPreferenceHelper.putLong(NETWORK_TIMESTAMP_KEY, currentTimeStamp)
                val response = iStockAppService.fetchStockList()
                if (response.isSuccessful && response.body() != null) {
                    val stockList = response.body()!!
                    stockDao.insertStocks(stockList)
                    stockList
                } else {
                    null
                }
            } else {
                val stockList = stockDao.getStockList()
                Log.d(TAG, "List from local database")
                stockList
            }

        } catch (e: Exception) {
            Log.e(TAG, "There was a error fetching Stock list ")
            null
        }
    }
}