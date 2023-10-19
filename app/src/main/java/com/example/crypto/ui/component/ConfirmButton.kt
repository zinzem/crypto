package com.example.crypto.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ConfirmButton(enabled: Boolean, label: String, onClick: () -> Unit) {
    Button(
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier.fillMaxWidth().height(56.dp),
        onClick = { onClick() }
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
    }
}