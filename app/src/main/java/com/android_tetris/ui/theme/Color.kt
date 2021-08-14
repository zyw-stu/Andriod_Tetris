package com.android_tetris.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val LightGrey = Color(0xFFB9B9B9)
val AlmostBlack = Color.Black.copy(0.8f)

// 黄色背景、黑色按钮的主题配色方案
val BodyBackground = Color(0xFFFFCC33)
val ScreenBackground = Color(0xff9ead86)
val PlayButtonColor1 = Color(0xFF555555)
val PlayButtonColor2 = Color(0xFF2E2E2E)
val PlayButtonColor3 = Color(0xFF0F0F0F)
val PlayButtonFontColor = Color(0xFFA5A5A5)
val PlayButtonBrush = Brush.linearGradient(
    colors = listOf(
        PlayButtonColor1,
        PlayButtonColor2,
        PlayButtonColor3
    )
)

// 黑色背景、灰色按钮的主题配色方案
val BodyBackgroundNight = Color(0xFF2B2B2B)
val ScreenBackgroundNight = Color(0xFF636D54)
val PlayButtonColorNight1 = Color(0xF2999999)
val PlayButtonColorNight2 = Color(0xF2797979)
val PlayButtonColorNight3 = Color(0xF24D4D4D)
val PlayButtonFontColorNight = Color(0x99000000)
val PlayButtonBrushNight = Brush.linearGradient(
    colors = listOf(
        PlayButtonColorNight1,
        PlayButtonColorNight2,
        PlayButtonColorNight3
    )
)

val BrickAlpha = Color.Black.copy(alpha = 0.2f)
val BrickFill = Color.Black.copy(alpha = 0.9f)
val Rainbow1 = Color(0xFFF8BBD0)
val Rainbow2 = Color(0xFFE1BEE7)
val Rainbow3 = Color(0xFFD1C4E9)   //
val Rainbow4 = Color(0xFFBBDEFB)
val Rainbow5 = Color(0xFFB2EBF2)  //
val Rainbow6 = Color(0xFFB2DFDB)
val Rainbow7 = Color(0xFFC8E6C9)
val gradient = Brush.verticalGradient(0f to Color.Gray, 1000f to Color.White)


// 彩虹字体
val RainbowRed = Color(0xFFDA034E)
val RainbowOrange = Color(0xFFFF9800)
val RainbowYellow = Color(0xFFFFEB3B)
val RainbowGreen = Color(0xFF4CAF50)
val RainbowBlue = Color(0xFF2196F3)
val RainbowIndigo = Color(0xFF3F51B5)
val RainbowViolet = Color(0xFF9C27B0)

val SkittlesRainbow = listOf(
    RainbowRed,
    RainbowOrange,
    RainbowYellow,
    RainbowGreen,
    RainbowBlue,
    RainbowIndigo,
    RainbowViolet
)

val PastelRainbowRed = Color(0xFFFF80AB)   //
val PastelRainbowOrange = Color(0xFFFF9E80)
val PastelRainbowYellow = Color(0xFFFFFF8D)  //
val PastelRainbowGreen = Color(0xFFA7FFEB)  //
val PastelRainbowBlue = Color(0xFF82B1FF)
val PastelRainbowIndigo = Color(0xFF8C9EFF)
val PastelRainbowViolet = Color(0xFFEA80FC)

val PastelRainbow = listOf(
    PastelRainbowRed,
    PastelRainbowOrange,
    PastelRainbowYellow,
    PastelRainbowGreen,
    PastelRainbowBlue,
    PastelRainbowIndigo,
    PastelRainbowViolet
)