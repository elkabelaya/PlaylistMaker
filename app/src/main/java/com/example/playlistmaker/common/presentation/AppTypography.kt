package com.example.playlistmaker.common.presentation

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.playlistmaker.R


val appFontFamily = FontFamily(
    Font(R.font.ys_display_bold, FontWeight.Bold),
    Font(R.font.ys_display_medium, FontWeight.Medium),
    Font(R.font.ys_display_regular, FontWeight.Normal)
)

val AppTypography = Typography(
    labelSmall = TextStyle(
        fontFamily = appFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = Dimens.textXS
    ),
//    displayLarge = TextStyle(
//        fontFamily = MyCustomFontFamily,
//        fontWeight = FontWeight.Normal,
//        fontSize = 32.sp,
//        lineHeight = 40.sp,
//        letterSpacing = 0.sp
//    ),
//    bodyLarge = TextStyle(
//        fontFamily = MyCustomFontFamily,
//        fontSize = 16.sp
//    )
    // You can customize all 15 M3 styles or omit them to use defaults
)