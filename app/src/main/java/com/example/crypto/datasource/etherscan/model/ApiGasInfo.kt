package com.example.crypto.datasource.etherscan.model

import com.google.gson.annotations.SerializedName

data class ApiGasInfo(
    @SerializedName(value = "FastGasPrice")
    val fastGasPrice: String?
)