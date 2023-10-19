package com.example.crypto.repository

import com.example.crypto.datasource.coingecko.CoinGeckoApiDatasource
import com.example.crypto.datasource.etherscan.EtherScanApiDatasource
import com.example.crypto.datasource.wallet.Wallet
import com.example.crypto.model.Currency
import com.example.crypto.model.Currency.Dollar
import com.example.crypto.model.Currency.Ethereum
import com.example.crypto.model.Currency.Euro
import com.example.crypto.model.Currency.Pound
import com.example.crypto.model.CurrencyAmount
import javax.inject.Inject

class EthRepository @Inject constructor(
    private val etherScanApiDatasource: EtherScanApiDatasource,
    private val coinGeckoApiDatasource: CoinGeckoApiDatasource,
    private val wallet: Wallet
) {

    suspend fun convert(currencyAmount: CurrencyAmount, targetCurrency: Currency): CurrencyAmount? {
        return if (currencyAmount.currency is Dollar || targetCurrency is Dollar) {
            coinGeckoApiDatasource.ethRateDao.getUsdChangeRate().body()?.ethereum?.usd?.let {
                if (targetCurrency is Ethereum) {
                    CurrencyAmount(currencyAmount.amount / it, Ethereum)
                } else {
                    CurrencyAmount(it * currencyAmount.amount, Dollar)
                }
            }
        } else if (currencyAmount.currency is Euro || targetCurrency is Euro) {
            coinGeckoApiDatasource.ethRateDao.getEurChangeRate().body()?.ethereum?.eur?.let {
                if (targetCurrency is Ethereum) {
                    CurrencyAmount(currencyAmount.amount / it, Ethereum)
                } else {
                    CurrencyAmount(it * currencyAmount.amount, Euro)
                }
            }
        } else if (currencyAmount.currency is Pound || targetCurrency is Pound) {
            coinGeckoApiDatasource.ethRateDao.getEurChangeRate().body()?.ethereum?.eur?.let {
                if (targetCurrency is Ethereum) {
                    CurrencyAmount(currencyAmount.amount / it, Ethereum)
                } else {
                    CurrencyAmount(it * currencyAmount.amount, Pound)
                }
            }
        } else throw Exception("Unsupported conversion")
    }

    fun getWalletAmount() = wallet.ethAmount

    fun sendEth(amount: Float): CurrencyAmount {
        val currentWalletAmount = wallet.ethAmount.amount
        val newAmount = currentWalletAmount - amount

        if (newAmount >= 0f) {
            wallet.ethAmount = wallet.ethAmount.copy(amount = newAmount)
        }
        return wallet.ethAmount
    }

    suspend fun getNetworkFees(): Float {
        return try {
            etherScanApiDatasource.gasDao.getGasInfo().body()?.result?.fastGasPrice?.toFloat()?.let {
                21000 * it / 100000000
            } ?: 0F
        } catch (e: Throwable) {
            0F
        }
    }
}