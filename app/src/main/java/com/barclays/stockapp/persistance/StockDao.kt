package com.barclays.stockapp.persistance

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.barclays.stockapp.model.Stock

@Dao
interface StockDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStocks(stocks: List<Stock>)

    @Query("SELECT * FROM Stock")
    suspend fun getStockList(): List<Stock>
}