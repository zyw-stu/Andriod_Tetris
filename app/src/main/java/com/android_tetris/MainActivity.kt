package com.android_tetris

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.InternalComposeApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android_tetris.ui.theme.ComposeTetrisTheme

// 屏幕间传递信息的变量类
class ScreenToScreenInfo {
    var currentScreen by mutableStateOf(0)  // 初始为游戏页面
    //var currentScreen by mutableStateOf(2)  // 初始为游戏页面
    var difficulty by mutableStateOf(500L)  // 难度选择
    var currentScore by mutableStateOf(0)   // 当前这局游戏的分数
    var highestScore by mutableStateOf(0)   // 历史最高分
    var playerName by mutableStateOf("")    // 用户名
    var bgm by mutableStateOf(0)            // 音乐选择
}

class ThemeViewModel : ViewModel() {
    var Theme by mutableStateOf(ComposeTetrisTheme.Theme.Light)     // 选择夜间模式还是普通配色
}

@RequiresApi(Build.VERSION_CODES.R)
var infoStorage = ScreenToScreenInfo()

class MainActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    @InternalComposeApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            var viewModel : ThemeViewModel = viewModel()
            if(isSystemInDarkTheme())
                viewModel.Theme = ComposeTetrisTheme.Theme.Dark

            // 选择性显示界面
            ComposeTetrisTheme(viewModel.Theme) {
                when (infoStorage.currentScreen) {
                    0 -> {
                        TetrisGameScreen()
                    }

                    1 -> {
                        SettingsScreen()
                    }

                    2 -> {
                        AboutUsScreen()
                    }
                }

            }
        }
    }
}

/*
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // StatusBarUtil.transparentStatusBar(this)
        setContent {
            val tetrisViewModel = viewModel<TetrisViewModel>()
            val lifecycle = LocalLifecycleOwner.current.lifecycle

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

            ComposeTetrisTheme {
                Surface {
                    val tetrisState by tetrisViewModel.tetrisStateLD.collectAsState()
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
                                    },
                                    onSettings = {

                                    }
                                )
                            )
                        }
                    )
                }
            }
            tetrisViewModel.dispatch(action = Action.Welcome)
        }
    }
}
 */



