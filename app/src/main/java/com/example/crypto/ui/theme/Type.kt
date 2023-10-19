package com.example.crypto.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp
import com.example.crypto.R

val basisGrotesque = FontFamily(
    Font(R.font.basisgrotesque_pro_black, FontWeight.Black),
    Font(R.font.basisgrotesque_pro_bold, FontWeight.Bold),
    Font(R.font.basisgrotesque_pro_light, FontWeight.Light),
    Font(R.font.basisgrotesque_pro_medium, FontWeight.Medium),
    Font(R.font.basisgrotesque_pro_regular, FontWeight.Normal)
)

val Typography = Typography(
    titleLarge = DefaultTextStyle().copy(
        fontWeight = FontWeight.Medium,
        fontSize = 32.sp,
        lineHeight = 40.sp,
    ),
    titleSmall = DefaultTextStyle().copy(
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 24.sp,
    ),
    bodyMedium = DefaultTextStyle().copy(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    ),
    bodySmall = DefaultTextStyle().copy(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
    ),
)

private fun DefaultTextStyle() = TextStyle(
    fontFamily = basisGrotesque,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    ),
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.LastLineBottom
    )
)