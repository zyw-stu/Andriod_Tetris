package com.android_tetris.ui.theme


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle


@ExperimentalAnimationApi
@Composable
fun MultiColorText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = LocalTextStyle.current,
    rainbow: List<Color> = SkittlesRainbow,
    startColor: Int = 0,
) {
    Row(modifier) {
        var index = startColor
        for (letter in text) {
            Text(letter.toString(), color = rainbow.get(index), style = style)
            index++
            if (index == rainbow.size) index = 0
        }

    }
}

@ExperimentalAnimationApi
@Composable
fun SnappyRainbowText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = LocalTextStyle.current,
    rainbow: List<Color> = SkittlesRainbow
) {
    require(rainbow.size > 0)
    val infiniteTransition = rememberInfiniteTransition()
    val colorIndex by infiniteTransition.animateValue(
        initialValue = 0,
        targetValue = rainbow.size - 1,
        typeConverter = Int.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(),
            repeatMode = RepeatMode.Restart
        )
    )
    MultiColorText(
        text = text,
        style = style,
        modifier = modifier,
        startColor = colorIndex,
        rainbow = rainbow
    )
}

@ExperimentalAnimationApi
@Composable
fun SmoothRainbowText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = LocalTextStyle.current,
    rainbow: List<Color> = PastelRainbow,
    startColor: Int = 0,
    duration: Int = 1200
) {
    Row(modifier) {
        var index = startColor
        for (letter in text) {
            MultiColorSmoothText(
                text = letter.toString(),
                style = style,
                rainbow = rainbow,
                startIndex = index,
                duration = duration
            )
            index++
            if (index == rainbow.size) index = 0

        }

    }
}

@ExperimentalAnimationApi
@Composable
fun TwoColorSmoothText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = LocalTextStyle.current,
    startColor: Color,
    endColor: Color
) {
    val infiniteTransition = rememberInfiniteTransition()
    val color by infiniteTransition.animateColor(
        initialValue = startColor,
        targetValue = endColor,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Restart
        )
    )
    Text(text = text, color = color, style = style, modifier = modifier)
}

@ExperimentalAnimationApi
@Composable
fun MultiColorSmoothText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = LocalTextStyle.current,
    rainbow: List<Color> = PastelRainbow,
    startIndex: Int = 0,
    duration: Int
) {
    val infiniteTransition = rememberInfiniteTransition()
    val interval = duration / rainbow.size
    val color by infiniteTransition.animateColor(
        initialValue = rainbow[0],
        targetValue = rainbow.last(),
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = duration
                delayMillis = startIndex * interval / 2
                var i = 0
                for (color in rainbow) {
                    color at i
                    i += interval
                }
            },
            repeatMode = RepeatMode.Restart
        )
    )
    Text(text = text, color = color, style = style, modifier = modifier)
}

@ExperimentalAnimationApi
@Composable
fun ThemedBlinkText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = LocalTextStyle.current
) {
    LocalContentBlinkTag {
        Text(
            text = text,
            modifier = modifier,
            style = style
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun LocalContentBlinkTag(
    durationMillis: Int = 500,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha: Float by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis),
            repeatMode = RepeatMode.Reverse
        )
    )
    CompositionLocalProvider(LocalContentAlpha provides alpha) {
        content()
    }
}

@ExperimentalAnimationApi
@Composable
fun BlinkTag(
    modifier: Modifier = Modifier,
    duration: Int = 500000,
    content: @Composable (modifier: Modifier) -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha: Float by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = duration
                1f at (duration*0.8).toInt()
                0f at duration
            },
            repeatMode = RepeatMode.Reverse
        )
    )
    content(modifier = modifier.alpha(alpha))
}

@ExperimentalAnimationApi
@Composable
fun BlinkText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified,
    style: TextStyle = LocalTextStyle.current,
    durationMillis: Int = 1000,
) {
    BlinkTag(modifier = modifier, duration = durationMillis) {
        Text(modifier = it, text = text, style = style, color = color)
    }
}
