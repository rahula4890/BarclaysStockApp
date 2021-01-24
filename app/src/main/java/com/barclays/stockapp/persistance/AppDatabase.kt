package com.barclays.stockapp.persistance

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.barclays.stockapp.model.Stock

@Database(entities = [Stock::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stockDao(): StockDao

    companion object {
        var INSTANCE: AppDatabase? = null

        fun getAppDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "stockDB"
                    ).build()
                }
            }
            return INSTANCE!!
        }

        fun destroyDatabase() {
            INSTANCE == null
        }
    }
}