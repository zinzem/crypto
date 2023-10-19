package com.example.crypto.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.crypto.R
import com.example.crypto.model.CurrencyAmount

@Composable
fun Fees(fees: CurrencyAmount?, modifier: Modifier = Modifier) {
    fees?.let {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
        ) {
            Image(
                painterResource(R.drawable.ic_info),
                contentDescription = "Info icon",
            )
            Text(
                text = "Est. Network fees: ~ $it",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }

}