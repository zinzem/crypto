package com.example.crypto.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.crypto.model.Currency
import com.example.crypto.ui.theme.Grey
import com.example.crypto.ui.theme.Grey3

@Composable
fun FiatItem(
    currency: Currency,
    selected: Boolean,
    onItemClick: (Currency) -> Unit,
    modifier: Modifier,
) {
    val border = if (selected) {
        Modifier.border(2.dp, Color.Black, RoundedCornerShape(4.dp))
    } else Modifier.border(1.dp, Grey, RoundedCornerShape(4.dp))

    Row(
        modifier
            .then(border)
            .fillMaxWidth()
            .background(White)
            .clickable { onItemClick(currency) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = currency.icon),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 8.dp)
                .size(32.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "${currency::class.simpleName}s",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = currency.shortCode,
                    color = Grey3,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Column {
                Text(
                    text = "234.50",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "$67860",
                    color = Grey3,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}