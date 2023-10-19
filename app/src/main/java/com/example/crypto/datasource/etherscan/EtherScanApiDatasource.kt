package com.example.crypto.datasource.etherscan

import com.example.crypto.BuildConfig
import com.example.crypto.datasource.etherscan.dao.GasDao
import com.example.crypto.network.HttpClient
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class EtherScanApiDatasource @Inject constructor(
    httpClient: HttpClient,
    gson: Gson
) {

    val gasDao: GasDao by lazy {  retrofit.create(GasDao::class.java) }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.ETHERSCAN_API_ENDPOINT_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(httpClient.client)
        .build()
}