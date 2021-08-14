package com.android_tetris


import android_tetris.ui.theme.AnimateSun
import android_tetris.ui.theme.DropDownAnimate
import android_tetris.ui.theme.PlanetMoon
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android_tetris.ui.theme.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@ExperimentalAnimationApi
@InternalComposeApi
@Composable
fun SettingsScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ){
        val animatedOffset = remember { Animatable(0f) }
        val animatedScales = remember { Animatable(1f) }
        val animatedRound = remember { Animatable(30f) }
        val animatedCheckBox = remember { Animatable(0f) }
        val animatedBitmap = remember { Animatable(0f) }
        val animatedText = remember { Animatable(1f) }
        val animatedColor = remember {
            androidx.compose.animation.Animatable(
                Color(
                    206,
                    199,
                    250,
                    121
                )
            )
        }
        val mutableState: MutableState<Boolean> = remember { mutableStateOf(true) }
        Box(
            modifier = Modifier
                //.fillMaxWidth()
                .background(color = MaterialTheme.colors.background)
        ) {
            Box {

                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.pointerInput(Unit) {
                        coroutineScope {
                            while (true) {
                                val offset = awaitPointerEventScope {
                                    awaitFirstDown().position
                                }
                                // Launch a new coroutine for animation so the touch detection thread is not
                                // blocked.
                                launch {
                                    if (mutableState.value) {
                                        animatedScales.animateTo(
                                            1.3f,
                                            animationSpec = spring(stiffness = Spring.StiffnessLow)
                                        )
                                        animatedText.animateTo(
                                            1f,
                                            animationSpec = spring(stiffness = Spring.StiffnessLow)
                                        )
                                        animatedRound.animateTo(
                                            10f,
                                            animationSpec = spring(stiffness = Spring.StiffnessLow)
                                        )
                                        animatedBitmap.animateTo(
                                            10f,
                                            animationSpec = spring(stiffness = Spring.StiffnessLow)
                                        )
                                        animatedCheckBox.animateTo(
                                            10f,
                                            animationSpec = spring(stiffness = Spring.StiffnessLow)
                                        )
                                        animatedColor.animateTo(Color(206, 170, 209, 121))
                                    } else {
                                        animatedScales.animateTo(
                                            1f,
                                            animationSpec = spring(stiffness = Spring.StiffnessLow)
                                        )
                                        animatedText.animateTo(
                                            -2f,
                                            animationSpec = spring(stiffness = Spring.StiffnessLow)
                                        )
                                        animatedRound.animateTo(
                                            30f,
                                            animationSpec = spring(stiffness = Spring.StiffnessLow)
                                        )
                                        animatedBitmap.animateTo(
                                            0f,
                                            animationSpec = spring(stiffness = Spring.StiffnessLow)
                                        )
                                        animatedCheckBox.animateTo(
                                            0f,
                                            animationSpec = spring(stiffness = Spring.StiffnessLow)
                                        )
                                        animatedColor.animateTo(Color(206, 199, 250, 121))
                                    }

                                    mutableState.value = !mutableState.value
                                    animatedOffset.animateTo(
                                        offset.x,
                                        animationSpec = spring(stiffness = Spring.StiffnessLow)
                                    )

                                }

                            }
                        }
                    }) {
                    Box(contentAlignment = Alignment.Center) {
                        LoginPageTopBlurImage(animatedBitmap, animatedOffset, animatedScales)
                        LoginPageTopRotaAndScaleImage(animatedColor, animatedScales, animatedOffset)
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = MaterialTheme.colors.background)
                            .padding(24.dp)
                    ) {
                        val biggerMargin = 16.dp
                        val smallerMargin = 5.dp

                        //"Setting"
                        Row() {
                            Text(
                                text = "Game Settings",
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colors.surface,
                                modifier = Modifier.padding(top = smallerMargin, bottom = smallerMargin)
                            )
                            Spacer(modifier = Modifier.width(30.dp))
                            if(MaterialTheme.colors.surface == LightGrey) {
                                Column() {
                                    Spacer(modifier = Modifier.height(smallerMargin))
                                    PlanetMoon()
                                }
                            }
                            else AnimateSun(Modifier.size(50.dp))   // 显示动画小太阳
                        }

                        ConstraintLayout(
                            modifier = Modifier
                                .wrapContentWidth(align = Alignment.CenterHorizontally)
                        ) {
                            val (difficultySwitch,
                                themeSwitch,
                                switchBGM) = createRefs()

                            Row(modifier = Modifier.constrainAs(difficultySwitch) {
                                start.linkTo(parent.start)
                                top.linkTo(parent.top)
                            }) {
                                Column{
                                    Spacer(modifier = Modifier.height(5.dp))
                                    Icon(
                                        tint = PastelRainbowGreen,
                                        painter = painterResource(id = R.drawable.ic___difficulty),
                                        contentDescription = null // decorative element
                                    )
                                }
                                // Switch difficulty 难度选择框
                                Text(
                                    text = "Switch difficulty",
                                    style = MaterialTheme.typography.body1.merge(),
                                    color = MaterialTheme.colors.surface,
                                    modifier = Modifier.padding(smallerMargin)
                                )

                                val difficultyOptions = listOf("Easy", "Normal", "Hard")
                                val difficultyDownSpeed = listOf(700L, 500L, 300L)
                                var select = 1

                                when(infoStorage.difficulty) {
                                    difficultyDownSpeed[0] -> select = 0
                                    difficultyDownSpeed[1] -> select = 1
                                    difficultyDownSpeed[2] -> select = 2
                                }

                                Column(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    RadioSelectionButton(
                                        label = "Easy",
                                        modifier = Modifier,
                                        isSelected = ("Easy" == difficultyOptions[select]),
                                        onPress = {
                                            select = 0
                                            infoStorage.difficulty = difficultyDownSpeed[select]
                                        },
                                        isDarkTheme = (MaterialTheme.colors.primary == PlayButtonColorNight2)
                                    )
                                    RadioSelectionButton(
                                        label = "Normal",
                                        modifier = Modifier,
                                        isSelected = ("Normal" == difficultyOptions[select]),
                                        onPress = {
                                            select = 1
                                            infoStorage.difficulty = difficultyDownSpeed[select]
                                        },
                                        isDarkTheme = (MaterialTheme.colors.primary == PlayButtonColorNight2)
                                    )
                                    RadioSelectionButton(
                                        label = "Hard",
                                        modifier = Modifier,
                                        isSelected = ("Hard" == difficultyOptions[select]),
                                        onPress = {
                                            select = 2
                                            infoStorage.difficulty = difficultyDownSpeed[select]
                                        },
                                        isDarkTheme = (MaterialTheme.colors.primary == PlayButtonColorNight2)
                                    )
                                }
                            } // end of Row(modifier = Modifier.constrainAs(difficultySwitch)



                            //主题选择
                            Row(modifier = Modifier.constrainAs(themeSwitch) {
                                start.linkTo(parent.start)
                                top.linkTo(difficultySwitch.bottom, margin = biggerMargin)
                            }) {
                                Column{
                                    Spacer(modifier = Modifier.height(5.dp))
                                    Icon(
                                        tint = PastelRainbowYellow,
                                        painter = painterResource(id = R.drawable.ic____theme),
                                        contentDescription = null // decorative element
                                    )
                                }
                                var viewModel : ThemeViewModel = viewModel()
                                Text(
                                    text = "Switch theme",
                                    style = MaterialTheme.typography.body1.merge(),
                                    color = MaterialTheme.colors.surface,
                                    modifier = Modifier.padding(smallerMargin)
                                )

                                Column(modifier = Modifier.fillMaxWidth()) {
                                    RectangularButton(
                                        label = "Daytime mode",
                                        modifier = Modifier
                                            .padding(
                                                horizontal = biggerMargin,
                                                vertical = smallerMargin
                                            )) {
                                        viewModel.Theme = ComposeTetrisTheme.Theme.Light
                                    }

                                    RectangularButton(
                                        label = "Night mode",
                                        modifier = Modifier.padding(
                                            horizontal = biggerMargin,
                                            vertical = smallerMargin
                                        )) {
                                        viewModel.Theme = ComposeTetrisTheme.Theme.Dark
                                    }
                                }
                            } // end of Row(modifier = Modifier.constrainAs(themeSwitch)



                            //音乐选择
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .constrainAs(switchBGM) {
                                    start.linkTo(parent.start)
                                    top.linkTo(themeSwitch.bottom, margin = biggerMargin)
                                }) {
                                Column{
                                    Spacer(modifier = Modifier.height(5.dp))
                                    Icon(
                                        tint = Rainbow5,
                                        painter = painterResource(id = R.drawable.ic___music),
                                        contentDescription = null // decorative element
                                    )
                                }
                                Text(
                                    text = "Choose the BGM",
                                    style = MaterialTheme.typography.body1.merge(),
                                    color = MaterialTheme.colors.surface,
                                    modifier = Modifier.padding(smallerMargin)
                                )
                                RectangularButton(
                                    label = "Next Music",
                                    modifier = Modifier
                                        .padding(horizontal = biggerMargin),
                                ) {
                                    /* TODO: 等这里有音乐再说 */
                                }
                            } // end of Row(modifier = Modifier.constrainAs(switchBGM)

                        }


                        //个人主页
                        Text(
                            text = "Player Profile",
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.surface,
                            modifier = Modifier.padding(
                                top = biggerMargin,
                                bottom = smallerMargin
                            )
                        )

                        ConstraintLayout(
                            modifier = Modifier
                                .wrapContentWidth(align = Alignment.CenterHorizontally)
                        ) {
                            val (playerName,
                                historyScore,
                                rules,
                                backButton) = createRefs()

                            //Player Name
                            Row(modifier = Modifier.constrainAs(playerName) {
                                start.linkTo(parent.start)
                                top.linkTo(parent.top)
                            }) {
                                Column{
                                    Spacer(modifier = Modifier.height(5.dp))
                                    Icon(
                                        tint = Rainbow3,
                                        painter = painterResource(id = R.drawable.ic_____name),
                                        contentDescription = null // decorative element
                                    )
                                }

                                Text(
                                    text = "Player name",
                                    style = MaterialTheme.typography.body1.merge(),
                                    color = MaterialTheme.colors.surface,
                                    modifier = Modifier.padding(smallerMargin)
                                )
                                var text by rememberSaveable { mutableStateOf("") }

                                TextField(
                                    //value = infoStorage.playerName,
                                    value=text,
                                    onValueChange = {
                                        //infoStorage.playerName = it
                                        text=it
                                    },
                                    label = {
                                        Text("\uD83C\uDF08 What's your name? ",
                                            color = MaterialTheme.colors.surface)
                                    },
                                    maxLines = 1,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )
                            }



                            // Highest  Score
                            Row(modifier = Modifier.constrainAs(historyScore) {
                                start.linkTo(playerName.start)
                                top.linkTo(playerName.bottom, margin = biggerMargin)
                            }) {
                                Column{
                                    Spacer(modifier = Modifier.height(5.dp))
                                    Icon(
                                        tint = PastelRainbowRed,
                                        painter = painterResource(id = R.drawable.ic_____score),
                                        contentDescription = null // decorative element
                                    )
                                }
                                /* TODO: 尝试从储存中捕获最高分 */
                                val highest = infoStorage.highestScore
                                Text(
                                    text = "Highest score",
                                    style = MaterialTheme.typography.body1.merge(),
                                    color = MaterialTheme.colors.surface,
                                    modifier = Modifier.padding(smallerMargin)
                                )

                                Text(
                                    text = "$highest",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colors.surface,
                                    modifier = Modifier.padding(horizontal = biggerMargin)
                                )
                            }


                            Row(modifier = Modifier
                                .constrainAs(rules) {
                                    start.linkTo(historyScore.start)
                                    top.linkTo(historyScore.bottom, margin = biggerMargin)
                                }) {

                                    Column{
                                        Spacer(modifier = Modifier.height(5.dp))
                                        Icon(
                                            tint = PastelRainbowOrange,
                                            painter = painterResource(id = R.drawable.ic_____zhinan),
                                            contentDescription = null // decorative element
                                        )
                                    }
                                    Text(
                                        text = "Help",
                                        style = MaterialTheme.typography.body1.merge(),
                                        color = MaterialTheme.colors.surface,
                                        modifier = Modifier.padding(smallerMargin)
                                    )


                                DropDownAnimate(
                                    text = "         *  Click to expand  *",
                                    modifier = Modifier.padding(smallerMargin)
                                ) {
                                    Text(
                                        text = "Press \"Rotate\" to rotate the block\n" +
                                                "Press \"◀\" and \"▶\" to move the block horizontally\n" +
                                                "Press \"▼\" to speed up the block fall\n" +
                                                "Press \"✔\" directly to drop the current block\n",
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colors.surface,
                                        modifier = Modifier
                                            .padding(smallerMargin)
                                            .fillMaxSize()
                                    )
                                }
                            }


                        }
                    }
                }
            }
        }
    }
}

