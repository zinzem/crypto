package com.example.crypto.datasource.coingecko.model

data class ApiEthToFiatRate<T : FiatRate>(
    val ethereum: T,
)

sealed class FiatRate {
    data class UsdRate(val usd: Float) : FiatRate()
    data class EurRate(val eur: Float) : FiatRate()
    data class GbpRate(val gbp: Float) : FiatRate()
}