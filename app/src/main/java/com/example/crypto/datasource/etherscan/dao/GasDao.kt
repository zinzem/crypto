package com.example.crypto.datasource.etherscan.dao

import com.example.crypto.datasource.etherscan.model.ApiGasInfo
import com.example.crypto.datasource.etherscan.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface GasDao {

    @GET("api?module=gastracker&action=gasoracle&apikey=YourApiKeyToken")
    suspend fun getGasInfo(): Response<ApiResponse<ApiGasInfo>>
}