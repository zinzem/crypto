package com.example.crypto.datasource.coingecko

import com.example.crypto.BuildConfig
import com.example.crypto.datasource.coingecko.dao.EthRateDao
import com.example.crypto.network.HttpClient
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class CoinGeckoApiDatasource @Inject constructor(
    httpClient: HttpClient,
    gson: Gson
) {

    val ethRateDao: EthRateDao by lazy {  retrofit.create(EthRateDao::class.java) }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.COINGECKO_API_ENDPOINT_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(httpClient.client)
        .build()
}