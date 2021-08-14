package com.android_tetris.ui.theme

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ControlButton(
    modifier: Modifier,
    label: String,
    fontSize: TextUnit = 18.sp,
    btnSize: Dp = 36.dp,
    onPress: () -> Unit = {},
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(2.dp),
            text = label,
            fontSize = fontSize,
            color = MaterialTheme.colors.surface,
            maxLines = 1
        )
        Box(
            modifier = Modifier
                .size(width = btnSize, height = btnSize)
                .shadow(elevation = 15.dp, shape = PlayButtonShape)
                .clip(shape = PlayButtonShape)
                .background(
                    brush = if(MaterialTheme.colors.primary == PlayButtonColorNight2) PlayButtonBrushNight
                            else PlayButtonBrush
                )
                .clickable {
                    onPress()
                }
        )
    }
}

@Composable
fun PlayButton(
    size: Dp = 70.dp,
    label: String,
    modifier: Modifier,
    fontSize: TextUnit = 22.sp,
    onPress: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .size(width = size, height = size)
            .shadow(elevation = 10.dp, shape = PlayButtonShape)
            .clip(shape = PlayButtonShape)
            .background(
                brush = if(MaterialTheme.colors.primary == PlayButtonColorNight2)
                             PlayButtonBrushNight
                        else PlayButtonBrush
            )
            .clickable {
                onPress()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = fontSize,
            color = MaterialTheme.colors.onPrimary
        )
    }
}

@Composable
fun RectangularButton(
    label: String,
    modifier: Modifier,
    fontSize: TextUnit = 16.sp,
    onPress: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(7.dp))
            .shadow(elevation = 20.dp, shape = RectangleShape)
            .background(
                brush = if(MaterialTheme.colors.primary == PlayButtonColorNight2)
                    PlayButtonBrushNight
                else PlayButtonBrush
            )
            .padding(7.dp)
            .clickable {
                onPress()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = fontSize,
            color = MaterialTheme.colors.onPrimary
        )
    }
}

@Composable
fun RadioSelectionButton(
    // 造这玩意儿是因为 Compose 自己的 RadioButton 居然用不了!!!
    label: String,
    modifier: Modifier,
    circleSize: Dp = 22.dp,
    fontSize: TextUnit = 16.sp,
    isSelected: Boolean = false,
    isDarkTheme: Boolean = false,
    onPress: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable {
                onPress()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Canvas(
            modifier = Modifier
                .padding(5.dp)
                .size(circleSize)
        ) {
            drawCircle(
                color = if (isDarkTheme) {
                    if(isSelected) LightGrey
                    else LightGrey.copy(0.3f)
                }
                else {
                    if(isSelected) PlayButtonColor3
                    else PlayButtonColor3.copy(0.3f)
                },
                center = Offset(x = size.width / 2, y = size.height / 2),
                radius = size.minDimension / 2
            )
        }
        Text(
            modifier = Modifier.padding(5.dp),
            text = label,
            fontSize = fontSize,
            color = MaterialTheme.colors.surface
        )
    }
}

@Preview
@Composable
fun PreviewRadioButton() {
    ComposeTetrisTheme() {
        Column() {
            RadioSelectionButton("Easy", Modifier, onPress = {}, isDarkTheme = true)
            RadioSelectionButton("Normal", Modifier, onPress = {}, isSelected = true, isDarkTheme = true)
            RadioSelectionButton("Hard", Modifier, onPress = {}, isDarkTheme = true)
        }
    }
}