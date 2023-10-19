package com.example.crypto.ui.screens.sendeth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.crypto.R
import com.example.crypto.model.Currency.Ethereum
import com.example.crypto.model.CurrencyAmount
import com.example.crypto.ui.component.ConfirmButton
import com.example.crypto.ui.component.Fees
import com.example.crypto.ui.component.FiatSelector
import com.example.crypto.ui.theme.Blue
import com.example.crypto.ui.theme.Grey
import com.example.crypto.ui.theme.Grey2
import com.example.crypto.ui.theme.Grey3

@Composable
fun SendEthScreen(viewModel: SendEthViewModel, navController: NavController) {
    val state by viewModel.state.collectAsState(SendEthScreenState())

    Column(
        Modifier
            .padding()
            .fillMaxHeight()) {
        Text(
            text = "Send Ethereum",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(start = 32.dp, top = 138.dp, end = 32.dp, bottom = 32.dp)
        )

        Box {
            Input(
                state.walletAmount,
                state.fiatAmount,
                state.ethAmount,
                state.isFiatInput,
                viewModel::onInputChange,
                viewModel::onChangeCurrencyClick,
                Modifier
                    .padding(horizontal = 32.dp)
                    .background(Color.White)
                    .align(Alignment.Center)
                    .border(1.5.dp, Grey2, RoundedCornerShape(8.dp))
                    .fillMaxWidth(),
            )
            OutlinedButton(
                shape = CircleShape,
                border = BorderStroke(1.dp, Grey),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Black
                ),
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(50.dp)
                    .align(Alignment.CenterStart),
                onClick = viewModel::onSwitchClick
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_switch),
                    contentDescription = null
                )
            }
        }

        Fees(state.networkFees, Modifier.padding(horizontal = 32.dp, vertical = 16.dp))

        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.Bottom,
        ) {
            ConfirmButton(state.isSendEnabled, state.sendButtonLabel, viewModel::onSendClick)
        }

        if (state.showCurrencySelector) {
            FiatSelector(state.fiatAmount.currency, viewModel::onCurrencySelected, viewModel::onCurrencySelectorDismissed)
        }
    }
}

@Composable
fun Input(
    walletAmount: CurrencyAmount?,
    fiatAmount: CurrencyAmount,
    ethAmount: CurrencyAmount?,
    isFiatInput: Boolean,
    onInputChange: (String) -> Unit,
    onChangeCurrencyClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var value by remember { mutableStateOf(fiatAmount.amount.toString()) }
    val maxAmountText = walletAmount?.let { "Max $it" } ?: ""
    val inputSymbol = if (isFiatInput) fiatAmount.currency.symbol else Ethereum.symbol
    val labelText =  if (isFiatInput) {
        ethAmount?.toString() ?: ""
    } else {
        fiatAmount.takeIf { it.amount >= 0 }?.toString() ?: ""
    }

    onInputChange(value)

    Column(modifier) {
        Column {
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = AnnotatedString(maxAmountText),
                    color = Blue,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(8.dp),
                )
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column(Modifier.padding(start = 40.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = inputSymbol,
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.padding(end = 1.dp)
                        )
                        BasicTextField(
                            value = value,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            textStyle = MaterialTheme.typography.titleSmall,
                            onValueChange = {
                                value = it
                                onInputChange(it)
                            }
                        )
                    }
                    Text(
                        text = labelText,
                        color = Grey3,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 2.dp, bottom = 16.dp)
                    )
                }
                Row(
                    Modifier
                        .clickable(onClick = onChangeCurrencyClick)
                        .padding(end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = fiatAmount.currency.icon),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = fiatAmount.currency.shortCode,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_arrow_down),
                        contentDescription = null,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}