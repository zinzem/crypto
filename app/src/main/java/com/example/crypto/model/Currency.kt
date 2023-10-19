package com.example.crypto.model

import com.example.crypto.R

sealed class Currency(
    val prefix: String,
    val suffix: String,
    val shortCode: String,
    val icon: Int
) {

    val symbol: String
        get() = prefix.ifBlank { suffix }

    object Dollar : Currency("$", "", "USD", R.drawable.ic_usa)
    object Euro : Currency("€", "", "EUR", R.drawable.ic_eu)
    object Pound : Currency("£", "", "GBP", R.drawable.ic_uk)
    object Ethereum : Currency("", "ETH", "ETH", 0)
}

data class CurrencyAmount(
    val amount: Float,
    val currency: Currency
) {
    override fun toString(): String {
        val suffix = if (currency.suffix.isNotBlank()) " ${currency.suffix}" else ""
        return "${currency.prefix}$amount$suffix"
    }
}