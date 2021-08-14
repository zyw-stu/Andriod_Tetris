package com.android_tetris.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

class ThemeColors(
    background: Color,          // 游戏机和其他页面的背景颜色
    screenBackground: Color,    // 游戏屏幕背景颜色
    buttonBrush: Brush,         // 按钮渐变配色
    buttonFontColor: Color,     // 按钮上的字体的颜色
    textFontColor: Color,       // 背景上的字体的颜色
)

private val DarkColorPalette = darkColors(
    primary = PlayButtonColorNight2,             // 单色按钮颜色
    onPrimary = PlayButtonFontColorNight,        // 按钮上的字体的颜色
    secondary = ScreenBackgroundNight,           // 游戏屏幕背景颜色
    background = BodyBackgroundNight,            // 游戏机和其他页面的背景颜色
    surface = LightGrey,                         // 背景上的字体的颜色
    /*
        buttonBrush = PlayButtonBrushNight,
    */
)

private val LightColorPalette = lightColors(
    primary = PlayButtonColor2,             // 单色按钮颜色
    onPrimary = PlayButtonFontColor,        // 按钮上的字体的颜色
    secondary = ScreenBackground,           // 游戏屏幕背景颜色
    background = BodyBackground,            // 游戏机和其他页面的背景颜色
    surface = AlmostBlack,                  // 背景上的字体的颜色
    /*
        buttonBrush = PlayButtonBrush
    */
)

@Stable
object ComposeTetrisTheme {
    enum class Theme {
        Light, Dark
    }
}

@Composable
fun ComposeTetrisTheme(
    theme: ComposeTetrisTheme.Theme = ComposeTetrisTheme.Theme.Light,
    content: @Composable () -> Unit
) {
    val colors = if (theme == ComposeTetrisTheme.Theme.Dark)
        DarkColorPalette
    else
        LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}