package com.barclays.stockapp.network

import com.barclays.stockapp.BuildConfig
import com.barclays.stockapp.model.Stock
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

fun createStockAppService(): IStockAppService {

    val okHttpClient = OkHttpClient.Builder().build()

    val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://financialmodelingprep.com/api/v3/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    return retrofit.create(IStockAppService::class.java)
}

interface IStockAppService {
    @GET("stock/list?apikey=${BuildConfig.FINANCIAL_MODELING_PREP_API_KEY}")
    suspend fun fetchStockList(): Response<List<Stock>>
}