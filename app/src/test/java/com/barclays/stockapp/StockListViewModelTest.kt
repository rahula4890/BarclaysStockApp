package com.barclays.stockapp

import com.barclays.stockapp.helpers.SharedPreferenceHelper
import com.barclays.stockapp.model.Stock
import com.barclays.stockapp.network.IStockAppService
import com.barclays.stockapp.persistance.StockDao
import com.barclays.stockapp.repository.StockListRepository
import com.barclays.stockapp.viewmodel.StockListViewModel
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class StockListViewModelTest {

    private lateinit var classUnderTest: StockListViewModel

    lateinit var stockListRepository: StockListRepository

    @Mock
    lateinit var iStockAppService: IStockAppService

    @Mock
    lateinit var stockDao: StockDao

    @Mock
    lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    @Mock
    lateinit var viewModel: StockListViewModel

    @Before
    fun setUp(){
        stockListRepository = StockListRepository(iStockAppService,stockDao,sharedPreferenceHelper)
        viewModel = StockListViewModel(stockListRepository)
        classUnderTest = StockListViewModel(
            stockListRepository
        )
    }

    @Test
    fun fetchStockList_ShouldReturnUser() {
        listOfStocks()
        assertEquals(listOfExpectedStocks(),viewModel.filterStockList("i"))
    }

    private fun listOfExpectedStocks(): Unit {
        mutableListOf(
            Stock("ibm","IBM", 100.0F, "NSE"),Stock("infy","Infosys", 100.0F, "BSE")
        )
    }

      private fun listOfStocks():MutableList<Stock>{
        return mutableListOf(
            Stock("ibm","IBM", 100.0F, "NSE"),Stock("infy","Infosys", 100.0F, "BSE"),Stock("barclays","Barclays", 100.0F, "BSE")
        )
    }
}