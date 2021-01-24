package com.barclays.stockapp.repository

import com.barclays.stockapp.model.Stock

interface IStockListRepository {
    suspend fun getStocksList(forceApi: Boolean): List<Stock>?
}