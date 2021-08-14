package com.android_tetris

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android_tetris.logic.Action
import com.android_tetris.logic.TetrisViewModel
import com.android_tetris.logic.combinedPlayListener
import com.android_tetris.logic.previewTetrisState
import com.android_tetris.ui.TetrisBody
import com.android_tetris.ui.TetrisButton
import com.android_tetris.ui.TetrisScreen
import com.android_tetris.ui.theme.ComposeTetrisTheme

@Composable
fun TetrisGameScreen() {
    val tetrisViewModel = viewModel<TetrisViewModel>()

    tetrisViewModel.changeDownSpeed(infoStorage.difficulty) // 改变下落速度

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val tetrisState by tetrisViewModel.tetrisStateLD.collectAsState()

    DisposableEffect(key1 = Unit) {
        val observer = object : DefaultLifecycleObserver {
            override fun onResume(owner: LifecycleOwner) {
                tetrisViewModel.dispatch(Action.Resume)
            }

            override fun onPause(owner: LifecycleOwner) {
                tetrisViewModel.dispatch(Action.Background)
            }
        }
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    TetrisBody(
        tetrisScreen = {
            TetrisScreen(tetrisState = tetrisState)
        },
        tetrisButton = {
            TetrisButton(
                playListener = combinedPlayListener(
                    onStart = {
                        tetrisViewModel.dispatch(Action.Start)
                    },
                    onPause = {
                        tetrisViewModel.dispatch(Action.Pause)
                    },
                    onReset = {
                        tetrisViewModel.dispatch(Action.Reset)
                    },
                    onTransformation = {
                        tetrisViewModel.dispatch(Action.Transformation(it))
                    },
                    onSound = {
                        tetrisViewModel.dispatch(Action.Sound)
                    }
                )
            )
        }
    )
    tetrisViewModel.dispatch(action = Action.Welcome)
}

@Composable
@Preview
fun PreviewGameScreenLight() {
    ComposeTetrisTheme(ComposeTetrisTheme.Theme.Light) {
        TetrisBody(
            tetrisScreen = {
                TetrisScreen(tetrisState = previewTetrisState)
            },
            tetrisButton = {
                TetrisButton()
            }
        )
    }
}

@Composable
@Preview
fun PreviewGameScreenDark() {
    ComposeTetrisTheme(ComposeTetrisTheme.Theme.Dark) {
        TetrisBody(
            tetrisScreen = {
                TetrisScreen(tetrisState = previewTetrisState)
            },
            tetrisButton = {
                TetrisButton()
            }
        )
    }
}