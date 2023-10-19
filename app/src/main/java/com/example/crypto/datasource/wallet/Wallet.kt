package com.example.crypto.datasource.wallet

import com.example.crypto.model.Currency
import com.example.crypto.model.CurrencyAmount
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Wallet @Inject constructor() {

    var ethAmount = CurrencyAmount(10F, Currency.Ethereum)
}