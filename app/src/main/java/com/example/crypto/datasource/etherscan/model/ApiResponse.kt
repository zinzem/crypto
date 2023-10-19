package com.example.crypto.datasource.etherscan.model

data class ApiResponse<T>(
    val status: String? = null,
    val message: String? = null,
    val result: T?,
)