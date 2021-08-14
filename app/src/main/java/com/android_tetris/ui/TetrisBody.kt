package com.android_tetris.ui


import android.widget.Toast
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android_tetris.infoStorage
import com.android_tetris.ui.theme.BodyBackground
import com.android_tetris.ui.theme.ComposeTetrisTheme
import com.android_tetris.ui.theme.Rainbow7


@Preview(widthDp = 360, heightDp = 700)
@Composable
fun PreviewTetrisBody() {
    ComposeTetrisTheme{
        TetrisBody(
            tetrisScreen = {},
            tetrisButton = {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)) {}
            }
        )
    }
}


@Composable
fun TetrisBody(
    tetrisScreen: @Composable (() -> Unit),
    tetrisButton: @Composable (() -> Unit),
) {
    var sizeState by remember {
        mutableStateOf(400.dp)
    }
    val size by animateDpAsState(
        targetValue = sizeState,
        tween( //增大的较为平缓
            durationMillis = 3000,
            delayMillis = 300,
            easing = LinearOutSlowInEasing
        )
    )
    //改变颜色状态
    val infiniteTransition= rememberInfiniteTransition()
    val color by infiniteTransition.animateColor(
        initialValue = BodyBackground,
        targetValue = Rainbow7,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 2000),
            repeatMode = RepeatMode.Reverse
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            //.background(color = BodyBackground)
            .background(color = MaterialTheme.colors.background)
            .padding(bottom = 30.dp)
    ) {

        //顶部导航
        TopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            backgroundColor = MaterialTheme.colors.primarySurface,
            elevation = 4.dp,
            contentPadding = AppBarDefaults.ContentPadding
        ) {
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically) {

                // 转到setting界面
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "menu按钮",
                    tint = Color.White,
                    modifier = Modifier
                        .clickable {
                            //点击了按钮
                            infoStorage.currentScreen = 1
                            //Toast.makeText(context, "Clicked on Icon Button", Toast.LENGTH_SHORT).show()
                        }
                        .padding(start = 12.dp, end = 12.dp)
                        .fillMaxHeight()
                )
                Text(text = "Setting",fontSize = 17.sp,color = Color.White)

                //转到About us 界面
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "menu按钮",
                    tint = Color.White,
                    modifier = Modifier
                        .clickable {
                            //点击了按钮
                            infoStorage.currentScreen = 2
                            //Toast.makeText(context, "Clicked on Icon Button", Toast.LENGTH_SHORT).show()
                        }
                        .padding(start = 12.dp, end = 12.dp)
                        .fillMaxHeight()
                )
                Text(text = "More",fontSize = 17.sp,color = Color.White)


                // 增大屏幕
                val context = LocalContext.current
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowUp,
                    contentDescription = "menu按钮",
                    tint = Color.White,
                    modifier = Modifier
                        .clickable {
                            //点击了按钮

                            if(sizeState<460.dp){
                                sizeState += 15.dp
                            }
                            else{
                                Toast.makeText(context, "The screen is maximized!", Toast.LENGTH_SHORT).show()
                            }

                        }
                        .padding(start = 12.dp, end = 12.dp)
                        .fillMaxHeight()
                )
                Text(text = "Big",fontSize = 17.sp,color = Color.White)

                //减小屏幕
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = "menu按钮",
                    tint = Color.White,
                    modifier = Modifier
                        .clickable {
                            //点击了按钮
                            if(sizeState>370.dp){
                                sizeState -= 15.dp
                            }
                            else{
                                Toast.makeText(context, "The screen is minimized!", Toast.LENGTH_SHORT).show()
                            }

                        }
                        .padding(start = 12.dp, end = 12.dp)
                        .fillMaxHeight()
                )
                Text(text = "Small",fontSize = 17.sp,color = Color.White)

            }
        }
        Box(
            modifier = Modifier
                .size(size)
                .background(color),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .matchParentSize()
                    .padding(10.dp)
            ) {
                //游戏屏幕
                tetrisScreen()
            }
        }
        //控制按钮
        tetrisButton()
    }
}

private fun DrawScope.drawScreenBorder(
    leftTop: Offset,
    width: Float,
    height: Float,
    borderPadding: Dp
) {
    val padding = borderPadding.toPx()
    val leftBottom = Offset(leftTop.x, leftTop.y + height)
    val rightTop = Offset(leftTop.x + width, leftTop.y)
    val rightBottom = Offset(rightTop.x, leftBottom.y)

    val path = Path().apply {
        lineTo(padding, padding)
        lineTo(rightTop.x - padding, padding)
        lineTo(rightTop.x, rightTop.y)
        close()
    }
    drawPath(path, Color.Black.copy(alpha = 0.7f))

    path.apply {
        reset()
        lineTo(padding, padding)
        lineTo(padding, leftBottom.y - padding)
        lineTo(leftBottom.x, leftBottom.y)
        close()
    }
    drawPath(path, Color.Black.copy(alpha = 0.5f))

    path.apply {
        reset()
        moveTo(leftBottom.x, leftBottom.y)
        relativeLineTo(padding, -padding)
        lineTo(rightBottom.x - padding, rightBottom.y - padding)
        lineTo(rightBottom.x, rightBottom.y)
        close()
    }
    drawPath(path, Color.Black.copy(alpha = 0.7f))

    path.apply {
        reset()
        moveTo(rightTop.x, rightTop.y)
        relativeLineTo(-padding, padding)
        lineTo(rightBottom.x - padding, rightBottom.y - padding)
        lineTo(rightBottom.x, rightBottom.y)
        close()
    }
    drawPath(path, Color.Black.copy(alpha = 0.5f))
}