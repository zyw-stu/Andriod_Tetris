package com.android_tetris.ui

import com.android_tetris.ui.theme.ControlButton
import com.android_tetris.ui.theme.PlayButton
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.android_tetris.logic.PlayListener
import com.android_tetris.logic.TransformationType.*
import com.android_tetris.logic.combinedPlayListener

@Preview(backgroundColor = 0xFFFFCC33, showBackground = true)
@Composable
fun TetrisButton(
    playListener: PlayListener = combinedPlayListener()
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.Center
        ) {
            val controlPadding = 13.dp
            ControlButton(
                label = "Start",
                modifier = Modifier.padding(end = controlPadding)
            ) {
                playListener.onStart()
            }
            ControlButton(
                label = "Pause",
                modifier = Modifier.padding(start = controlPadding, end = controlPadding)
            ) {
                playListener.onPause()
            }
            ControlButton(
                label = "Reset",
                modifier = Modifier.padding(start = controlPadding, end = controlPadding)
            ) {
                playListener.onReset()
            }
            ControlButton(
                label = "Sound",
                modifier = Modifier.padding(start = controlPadding)
            ) {
                playListener.onSound()
            }
        }
        ConstraintLayout(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
        ) {
            val (leftBtn, rightBtn, fastDownBtn, rotateBtn, fallBtn) = createRefs()
            val innerMargin = 16.dp

            PlayButton(
                label = "Rotate",
                fontSize = 18.sp,
                modifier = Modifier.constrainAs(rotateBtn) {
                /*
                    top:    上
                    bottom: 下
                    start:  左
                    end:    右
                    margin: 一律应用之前定义的内边距 innerMargin = 24.dp
                */
                    top.linkTo(anchor = parent.top)
                    bottom.linkTo(anchor = fastDownBtn.top)
                    start.linkTo(anchor = leftBtn.end)
                    end.linkTo(anchor = rightBtn.start)
                }) {
                playListener.onTransformation(Rotate)
            }
            PlayButton(
                label = "✔",
                modifier = Modifier.constrainAs(fallBtn) {
                    top.linkTo(anchor = parent.top)
                    bottom.linkTo(anchor = parent.bottom)
                    start.linkTo(anchor = rightBtn.end, margin = innerMargin)
                }) {
                playListener.onTransformation(Fall)
            }
            PlayButton(
                label = "◀",
                modifier = Modifier.constrainAs(leftBtn) {
                    top.linkTo(anchor = fastDownBtn.top)
                    bottom.linkTo(anchor = parent.bottom)
                    start.linkTo(anchor = parent.start)
                    end.linkTo(anchor = fastDownBtn.start)
            }) {
                playListener.onTransformation(Left)
            }
            PlayButton(
                label = "▶",
                modifier = Modifier.constrainAs(rightBtn) {
                    top.linkTo(anchor = fastDownBtn.top)
                    bottom.linkTo(anchor = parent.bottom)
                    start.linkTo(anchor = fastDownBtn.end, margin = innerMargin)
            }) {
                playListener.onTransformation(Right)
            }

            PlayButton(
                label = "▼",
                modifier = Modifier.constrainAs(fastDownBtn) {
                    top.linkTo(anchor = rotateBtn.bottom, margin = 10.dp)
                    bottom.linkTo(anchor = parent.bottom)
                    start.linkTo(anchor = leftBtn.end, margin = innerMargin)
                    end.linkTo(anchor = rightBtn.start)
            }) {
                playListener.onTransformation(FastDown)
            }
        }
    }
}