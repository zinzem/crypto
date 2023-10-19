package com.example.crypto.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.crypto.model.Currency
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiatSelector(selectedCurrency: Currency, onItemClick: (Currency) -> Unit, onDismissed: () -> Unit) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    ModalBottomSheet(onDismissRequest = onDismissed, sheetState = sheetState) {
        Text(
            text = "Displayed currency",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(start = 32.dp, top = 16.dp, end = 32.dp, bottom = 32.dp)
        )
        listOf(Currency.Euro, Currency.Dollar, Currency.Pound).forEach {
            FiatItem(
                it,
                selectedCurrency.shortCode == it.shortCode,
                {
                    onItemClick(it)
                    scope.launch {
                        sheetState.hide()
                        onDismissed()
                    }
                },
                Modifier.padding(start = 32.dp, end = 32.dp, bottom = 16.dp)
            )
        }
        WarningItem(Modifier.padding(start = 32.dp, end = 32.dp, bottom = 16.dp))
    }
}