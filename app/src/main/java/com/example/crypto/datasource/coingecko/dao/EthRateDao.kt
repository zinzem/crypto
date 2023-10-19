package com.example.crypto.datasource.coingecko.dao

import com.example.crypto.datasource.coingecko.model.ApiEthToFiatRate
import com.example.crypto.datasource.coingecko.model.FiatRate.EurRate
import com.example.crypto.datasource.coingecko.model.FiatRate.GbpRate
import com.example.crypto.datasource.coingecko.model.FiatRate.UsdRate
import retrofit2.Response
import retrofit2.http.GET

interface EthRateDao {

    @GET("api/v3/simple/price?ids=ethereum&vs_currencies=usd")
    suspend fun getUsdChangeRate(): Response<ApiEthToFiatRate<UsdRate>>

    @GET("api/v3/simple/price?ids=ethereum&vs_currencies=eur")
    suspend fun getEurChangeRate(): Response<ApiEthToFiatRate<EurRate>>

    @GET("api/v3/simple/price?ids=ethereum&vs_currencies=gbp")
    suspend fun getGbpChangeRate(): Response<ApiEthToFiatRate<GbpRate>>
}