package com.android_tetris


import android_tetris.ui.theme.*
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android_tetris.ui.theme.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import com.android_tetris.ui.theme.getBitmap
import com.android_tetris.ui.theme.topBarView_More




@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun AboutUsScreen() {

    Box(modifier = Modifier
        //.fillMaxSize()
        .verticalScroll(rememberScrollState())
        .background(color = Color.Black)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black)
        ) {
            val biggerMargin = 16.dp
            val innerMargin = 10.dp
            val smallerMargin = 5.dp

            topBarView_More()  // 添加TopBar
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Rainbow1)) {
                        append("A")
                    }
                    withStyle(style = SpanStyle(color = Rainbow2)) {
                        append("b")
                    }
                    withStyle(style = SpanStyle(color = Rainbow3)) {
                        append("o")
                    }
                    withStyle(style = SpanStyle(color = Rainbow4)) {
                        append("u")
                    }
                    withStyle(style = SpanStyle(color = Rainbow5)) {
                        append("t")
                    }
                    withStyle(style = SpanStyle(color = Rainbow6)) {
                        append(" u")
                    }
                    withStyle(style = SpanStyle(color = Rainbow7)) {
                        append("s")
                    }
                },
                // text = "bout us",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            )
            BlinkTag {
                Column(modifier = Modifier.align(alignment = Alignment.CenterHorizontally)){
                    Row {
                        Icon(
                            tint = PastelRainbowYellow,
                            painter = painterResource(id = R.drawable.ic_star),
                            contentDescription = null // decorative element
                        )
                        Text(
                            text = " Click the heart!  ",
                            color = Color.White,
                            modifier = it
                                .padding(8.dp)
                        )
                    }
                }
            }

            // 水墨渲染图片
            Row(
                modifier = Modifier
                    .padding(biggerMargin)
                    .height(200.dp)
                    .fillMaxWidth()
            ) {
                InkColorCanvas()
            }

            // Contribute information
            Row(
                modifier = Modifier
                    .padding(biggerMargin)
                    .height(500.dp)
                    .fillMaxWidth()
            ){
                Parallax()
            }


            //Launch Rocket
            Row{
                Text(
                    text = "Our Faith",
                    color = Color.White,
                    fontSize = 40.sp,
                    fontFamily = FontFamily.Cursive,
                    modifier = Modifier
                        .alpha(0.8f)
                        .padding(start = 5.dp)
                )
                Spacer(modifier = Modifier.width(150.dp))
                Image(
                    painter = painterResource(id = R.drawable.moon),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(70.dp)
                        .height(70.dp)
                )
            }
            Row(
                modifier = Modifier
                    .padding(biggerMargin)
                    .height(400.dp)
                    .fillMaxWidth()
            ){
                val animationState = remember{ mutableStateOf(false)}
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Black)
                ) {
                    Rocket(
                        isRocketEnabled = animationState.value,
                        maxWidth = maxWidth,
                        maxHeight = maxHeight
                    )
                    LaunchButton(
                        animationState = animationState.value,
                        onToggleAnimationState = { animationState.value = !animationState.value }
                    )
                }
            }


            //Message Board
            Text(
                text = "Message Board",
                color = Color.White,
                fontSize = 40.sp,
                fontFamily = FontFamily.Cursive,
                modifier = Modifier
                    .alpha(0.8f)
                    .padding(start = 5.dp)
            )
            Image(
                bitmap = getBitmap(resource = R.drawable.film_edge),
                contentDescription = "filmEdgeImage",
                modifier = Modifier.fillMaxWidth()
            )
            Box(
                modifier = Modifier
                    .background(Color.Black)
                    .horizontalScroll(rememberScrollState())
                    .padding(smallerMargin)
            ) {
                Row {
                    Box(
                        modifier = Modifier
                            .width(200.dp)
                            .height(200.dp)
                    ){
                        ImageCard( //调用函数
                            painter = painterResource(id = R.drawable.meme_yx),
                            contentDescription = "WYX",
                            title = "王雨潇：\n" +
                                    "   好羡慕隔壁做深度学习的啊……"
                        )
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Box(
                        modifier = Modifier
                            .width(200.dp)
                            .height(200.dp)
                    ){
                        ImageCard( //调用函数
                            painter = painterResource(id = R.drawable.meme_yw),
                            contentDescription ="ZYW" ,
                            title = "张煜玮：\n" +
                                    "   感谢我两边大佬在开发过程中对我的帮助~"
                        )
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Box(
                        modifier = Modifier
                            .width(200.dp)
                            .height(200.dp)
                    ){
                        ImageCard( //调用函数
                            painter = painterResource(id = R.drawable.meme_pf),
                            contentDescription = "XPF",
                            title = "西鹏飞：\n" +
                                    "   狂啃kotlin，玩转Compose(实则摸鱼靠队友)的15天"
                        )
                    }
                }
            }
            Image(
                bitmap = getBitmap(resource = R.drawable.film_edge),
                contentDescription = "filmEdgeImage",
                modifier = Modifier.fillMaxWidth()
            )

            //contact us
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Contact us",
                color = Color.White,
                fontSize = 40.sp,
                fontFamily = FontFamily.Cursive,
                modifier = Modifier
                    .alpha(0.8f)
                    .padding(start = 5.dp)
            )
            Text(
                text=" The messages below can be coped! ",
                color = Color.White,
                fontSize = 12.sp,
                fontFamily = FontFamily.Serif,
            )
            Box(
                modifier = Modifier
                    .background(Color.Black)
                    .horizontalScroll(rememberScrollState())
                    .padding(smallerMargin)
            ){
                Row{
                    Card(
                        modifier = Modifier
                            .width(300.dp)
                            .height(220.dp)
                            .border(
                                width = 5.dp,
                                color = Rainbow7,
                                shape = RoundedCornerShape(15.dp)
                            ),
                        shape = RoundedCornerShape(15.dp),
                        elevation = 5.dp
                    ){
                        Box(modifier = Modifier
                            .background(Color.White)
                            .fillMaxSize()){
                            Column(modifier = Modifier.padding(10.dp)){
                                Row {
                                    Icon(
                                        tint = Rainbow7,
                                        painter = painterResource(id = R.drawable.ic_fork),
                                        contentDescription = null // decorative element
                                    )
                                    Spacer(modifier = Modifier.width(20.dp))
                                    Text(
                                        text = "Fork",
                                        color = Rainbow7,
                                        fontWeight=FontWeight.Bold,
                                        fontSize=20.sp
                                    )
                                }
                                SelectionContainer {
                                    Column {
                                        DisableSelection {  // 不可以选择的部分
                                            Text("ProjectAddress_1:",color = Color.Black,fontSize=15.sp)
                                        }
                                        Text("https://gitee.com/yxwang2023/android_tetris\n",color = Color.Black,fontSize=15.sp)
                                        DisableSelection {  // 不可以选择的部分
                                            Text("ProjectAddress_2:",color = Color.Black,fontSize=15.sp)
                                        }
                                        Text("https://github.com/zyw-stu/Andriod_Tetris\n",color = Color.Black,fontSize=15.sp)
                                    }
                                }
                            }//Column
                        }//Box

                    } // Fork card
                    Spacer(modifier = Modifier.width(20.dp))
                    Card(
                        modifier = Modifier
                            .width(300.dp)
                            .height(220.dp)
                            .border(
                                width = 5.dp,
                                color = Rainbow6,
                                shape = RoundedCornerShape(15.dp)
                            ),
                        shape = RoundedCornerShape(15.dp),
                        elevation = 5.dp
                    ){
                        Box(modifier = Modifier
                            .background(Color.White)
                            .fillMaxSize()){
                            Column(modifier = Modifier.padding(10.dp)){
                                Row {
                                    Icon(
                                        tint = Rainbow6,
                                        painter = painterResource(id = R.drawable.ic_mail),
                                        contentDescription = null // decorative element
                                    )
                                    Spacer(modifier = Modifier.width(20.dp))
                                    Text(
                                        text = "Email",
                                        color = Rainbow6,
                                        fontWeight=FontWeight.Bold,
                                        fontSize=20.sp
                                    )
                                }
                                SelectionContainer {
                                    Column {
                                        DisableSelection {  // 不可以选择的部分
                                            Text("王雨潇",fontSize=15.sp)
                                        }
                                        Text("19281171@bjtu.edu.cn\n",fontSize=15.sp)
                                        DisableSelection {  // 不可以选择的部分
                                            Text("张煜玮",fontSize=15.sp)
                                        }
                                        Text("19291255@bjtu.edu.cn\n",fontSize=15.sp)
                                        DisableSelection {  // 不可以选择的部分
                                            Text("西鹏飞",fontSize=15.sp)
                                        }
                                        Text("19281142@bjtu.edu.cn\n",fontSize=15.sp)
                                    }
                                }
                            }//Column
                        }//Box

                    } // Fork card
                    Spacer(modifier = Modifier.width(20.dp))
                    Card(
                        modifier = Modifier
                            .width(300.dp)
                            .height(220.dp)
                            .border(
                                width = 5.dp,
                                color = Rainbow5,
                                shape = RoundedCornerShape(15.dp)
                            ),
                        shape = RoundedCornerShape(15.dp),
                        elevation = 5.dp
                    ){
                        Box(modifier = Modifier
                            .background(Color.White)
                            .fillMaxSize()){
                            Column(modifier = Modifier.padding(10.dp)){
                                Row {
                                    Icon(
                                        tint = Rainbow5,
                                        painter = painterResource(id = R.drawable.ic___mayun),
                                        contentDescription = null // decorative element
                                    )
                                    Spacer(modifier = Modifier.width(20.dp))
                                    Text(
                                        text = "Gitee",
                                        color = Rainbow5,
                                        fontWeight=FontWeight.Bold,
                                        fontSize=20.sp
                                    )
                                }
                                SelectionContainer {
                                    Column {
                                        Column {
                                            DisableSelection {  // 不可以选择的部分
                                                Text("王雨潇",fontSize=15.sp)
                                            }
                                            Text("https://gitee.com/yxwang2023\n",fontSize=15.sp)
                                            DisableSelection {  // 不可以选择的部分
                                                Text("张煜玮",fontSize=15.sp)
                                            }
                                            Text("https://gitee.com/bigfishhhh\n",fontSize=15.sp)
                                            DisableSelection {  // 不可以选择的部分
                                                Text("西鹏飞",fontSize=15.sp)
                                            }
                                            Text("https://gitee.com/pengfei_xi\n",fontSize=15.sp)
                                    }
                                }
                            }//Column
                        }//Box

                    } // Fork card


                }// Fork card
            }


        }
    }
}}




