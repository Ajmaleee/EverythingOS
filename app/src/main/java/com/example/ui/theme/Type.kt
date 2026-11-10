package com.example.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.R

val SilkscreenDotMatrix = FontFamily(
    Font(R.font.silkscreen, FontWeight.Normal)
)

val SpaceGroteskSans = FontFamily(
    Font(R.font.space_grotesk, FontWeight.Normal),
    Font(R.font.space_grotesk, FontWeight.Medium),
    Font(R.font.space_grotesk, FontWeight.Bold)
)

val SpaceMonoTech = FontFamily(
    Font(R.font.space_mono, FontWeight.Normal),
    Font(R.font.space_mono, FontWeight.Bold)
)

val NothingTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = SilkscreenDotMatrix,
        fontWeight = FontWeight.Normal,
        fontSize = 44.sp,
        lineHeight = 48.sp,
        letterSpacing = 1.sp,
        color = NothingWhite
    ),
    displayMedium = TextStyle(
        fontFamily = SilkscreenDotMatrix,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 36.sp,
        letterSpacing = 1.sp,
        color = NothingWhite
    ),
    displaySmall = TextStyle(
        fontFamily = SilkscreenDotMatrix,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.5.sp,
        color = NothingWhite
    ),
    headlineLarge = TextStyle(
        fontFamily = SpaceGroteskSans,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.sp,
        color = NothingWhite
    ),
    headlineMedium = TextStyle(
        fontFamily = SpaceGroteskSans,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.sp,
        color = NothingWhite
    ),
    titleMedium = TextStyle(
        fontFamily = SpaceGroteskSans,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.15.sp,
        color = NothingWhite
    ),
    bodyLarge = TextStyle(
        fontFamily = SpaceGroteskSans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.25.sp,
        color = NothingWhiteSecondary
    ),
    bodyMedium = TextStyle(
        fontFamily = SpaceGroteskSans,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.25.sp,
        color = NothingWhiteMuted
    ),
    labelLarge = TextStyle(
        fontFamily = SpaceGroteskSans,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.5.sp,
        color = NothingWhite
    ),
    labelSmall = TextStyle(
        fontFamily = SpaceMonoTech,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        lineHeight = 12.sp,
        letterSpacing = 0.5.sp,
        color = NothingWhiteMuted
    )
)
