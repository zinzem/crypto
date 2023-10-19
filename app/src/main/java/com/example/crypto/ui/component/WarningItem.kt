package com.example.crypto.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.crypto.R

@Composable
fun WarningItem(modifier: Modifier) {
    Row(
        modifier
            .fillMaxWidth()
            .background(White)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_warning),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 8.dp)
                .size(32.dp)
        )
        Text(
            text = "The currency is for information only. You're still sending ETH.",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}