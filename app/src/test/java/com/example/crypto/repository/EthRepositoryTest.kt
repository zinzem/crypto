package com.example.crypto.repository

import com.example.crypto.datasource.coingecko.CoinGeckoApiDatasource
import com.example.crypto.datasource.coingecko.model.ApiEthToFiatRate
import com.example.crypto.datasource.coingecko.model.FiatRate
import com.example.crypto.datasource.coingecko.model.FiatRate.UsdRate
import com.example.crypto.datasource.etherscan.EtherScanApiDatasource
import com.example.crypto.datasource.etherscan.model.ApiGasInfo
import com.example.crypto.datasource.etherscan.model.ApiResponse
import com.example.crypto.datasource.wallet.Wallet
import com.example.crypto.model.Currency.Dollar
import com.example.crypto.model.Currency.Ethereum
import com.example.crypto.model.Currency.Euro
import com.example.crypto.model.CurrencyAmount
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import retrofit2.Response

class EthRepositoryTest {

    private lateinit var subject: EthRepository

    private val etherScanApiDatasource = mockk<EtherScanApiDatasource>(relaxed = true)
    private val coinGeckoApiDatasource = mockk<CoinGeckoApiDatasource>(relaxed = true)
    private val wallet = mockk<Wallet>(relaxed = true)

    @Before
    fun setup() {
        subject = EthRepository(etherScanApiDatasource, coinGeckoApiDatasource, wallet)
    }

    @Test
    fun testConvertEthToUsd() {
        coEvery { coinGeckoApiDatasource.ethRateDao.getUsdChangeRate() } returns Response.success(
            ApiEthToFiatRate(UsdRate(2f))
        )
        val eth = CurrencyAmount(0.5f, Ethereum)

        runBlocking {
            val res = subject.convert(eth, Dollar)

            assertEquals(res!!.amount, 2f * 0.5f)
        }
    }

    @Test
    fun testConvertEurToEth() {
        coEvery { coinGeckoApiDatasource.ethRateDao.getEurChangeRate() } returns Response.success(
            ApiEthToFiatRate(FiatRate.EurRate(1.5f))
        )
        val eur = CurrencyAmount(0.8f, Euro)

        runBlocking {
            val res = subject.convert(eur, Ethereum)

            assertEquals(res!!.amount, 0.8f / 1.5f)
        }
    }

    @Test
    fun testSendEth() {
        every { wallet.ethAmount } returns CurrencyAmount(2f, Ethereum)

        subject.sendEth(1.5f)

        verify {
            wallet.ethAmount = CurrencyAmount(0.5f, Ethereum)
        }
    }

    @Test
    fun testSendTooMuchEth() {
        every { wallet.ethAmount } returns CurrencyAmount(1f, Ethereum)

        val res = subject.sendEth(1.5f)

        assertEquals(res.amount, 1f)
        verify(exactly = 0) {
            wallet.ethAmount = any()
        }
    }

    @Test
    fun testGetNetworkFee() {
        coEvery { etherScanApiDatasource.gasDao.getGasInfo() } returns Response.success(ApiResponse(result = ApiGasInfo("1")))

        runBlocking {
            val res = subject.getNetworkFees()

            assertEquals(res, 21000f / 100000000f)
        }
    }
}