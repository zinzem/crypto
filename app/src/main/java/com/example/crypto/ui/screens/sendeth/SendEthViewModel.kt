package com.example.crypto.ui.screens.sendeth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crypto.model.Currency
import com.example.crypto.model.Currency.Ethereum
import com.example.crypto.model.CurrencyAmount
import com.example.crypto.repository.EthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SendEthViewModel @Inject constructor(
    private val ethRepository: EthRepository
): ViewModel() {

    private val _state: MutableStateFlow<SendEthScreenState> = MutableStateFlow(SendEthScreenState())
    val state: Flow<SendEthScreenState>
        get() = _state

    init {
        viewModelScope.launch {
            setState { copy(walletAmount = ethRepository.getWalletAmount()) }
        }

        viewModelScope.launch {
            val fees = ethRepository.getNetworkFees()
            setState { copy(networkFees = CurrencyAmount(fees, Ethereum)) }
        }
    }

    fun onInputChange(input: String) = viewModelScope.launch {
        val amount = input.toFloatOrNull() ?: 0F
        val state = state.first()
        if (state.isFiatInput) {
            setState {
                val fiatAmount = fiatAmount.copy(amount = amount)
                copy(
                    fiatAmount = fiatAmount,
                    sendButtonLabel = "Send $fiatAmount of ETH"
                )
            }.let {
                updateEthAmount(it.fiatAmount)
            }
        } else {
            val ethAmount = CurrencyAmount(amount = amount, Ethereum)
            setState {
                copy(
                    ethAmount = ethAmount,
                    isSendEnabled = walletAmount.amount > ethAmount.amount,
                    sendButtonLabel = "Send $ethAmount of ${fiatAmount.currency.symbol}"
                )
            }
            updateFiatAmount(ethAmount, state.fiatAmount.currency)
        }
    }

    fun onSwitchClick() = viewModelScope.launch {
        setState {
            copy(isFiatInput = !isFiatInput)
        }
    }

    fun onChangeCurrencyClick() = viewModelScope.launch {
        setState {
            copy(showCurrencySelector = true)
        }
    }

    fun onCurrencySelected(currency: Currency) = viewModelScope.launch {
        setState {
            copy(fiatAmount = fiatAmount.copy(currency = currency))
        }
    }

    fun onCurrencySelectorDismissed() = viewModelScope.launch {
        setState {
            copy(showCurrencySelector = false,)
        }
    }

    fun onSendClick() = viewModelScope.launch {
        val ethAmount = state.first().ethAmount?.amount
        if (ethAmount != null) {
            setState {
                copy(walletAmount = ethRepository.sendEth(ethAmount))
            }
        }
    }

    private fun updateEthAmount(fiatAmount: CurrencyAmount) {
        viewModelScope.launch {
            val ethAmount = ethRepository.convert(fiatAmount, Ethereum)
            setState {
                copy(
                    ethAmount = ethAmount,
                    isSendEnabled = ethAmount?.let { walletAmount.amount > it.amount } ?: false
                )
            }
        }
    }

    private fun updateFiatAmount(ethAmount: CurrencyAmount, fiatCurrency: Currency) {
        viewModelScope.launch {
            val fiatAmount = ethRepository.convert(ethAmount, fiatCurrency)
            setState {
                copy(fiatAmount = fiatAmount ?: CurrencyAmount(-1F, fiatCurrency))
            }
        }
    }

    private suspend fun setState(
        buildNewState: SendEthScreenState.() -> SendEthScreenState
    ) = buildNewState(_state.value).also {
        _state.emit(it)
    }
}

data class SendEthScreenState(
    val isFiatInput: Boolean = true,
    val walletAmount: CurrencyAmount = CurrencyAmount(0F, Ethereum),
    val networkFees: CurrencyAmount? = null,
    val fiatAmount: CurrencyAmount = CurrencyAmount(100F, Currency.Dollar),
    val ethAmount: CurrencyAmount? = null,
    val isSendEnabled: Boolean = false,
    val sendButtonLabel: String = "Send",
    val showCurrencySelector: Boolean = false
)