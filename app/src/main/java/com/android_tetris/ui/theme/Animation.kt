package android_tetris.ui.theme

import android.graphics.*
import android.view.MotionEvent
import androidx.compose.animation.ExperimentalAnimationApi
import com.android_tetris.R
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android_tetris.ui.theme.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

@Composable
fun DefaultPreview() {
    ComposeTetrisTheme() {
        PlanetMoon()
    }
}


@Composable
fun PlanetMoon() {
    val earthFloatAnimation by buildEarthFloatAnimation()
    val earthRotationAnimation by buildEarthRotationAnimation()
    Box(
        modifier = Modifier
            .width(48.dp)
            .height(48.dp)
            .background(color = Color.Transparent),
        contentAlignment = BiasAlignment(0f, earthFloatAnimation)
    ) {
        Image(
            painter = painterResource(R.drawable.planet_moon_crescent),
            contentDescription = "Planet Earth",
            modifier = Modifier.scale(scaleX = earthRotationAnimation, scaleY = 1f)
        )
    }
}

@Composable
private fun buildEarthFloatAnimation(): State<Float> {
    val earthFloatAnimationInfiniteTransition = rememberInfiniteTransition()
    return earthFloatAnimationInfiniteTransition.animateFloat(
        initialValue = -0.1f,
        targetValue = 0.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        )
    )
}

@Composable
private fun buildEarthRotationAnimation(): State<Float> {
    val constInitialValue = 1f
    val constTargetValue = -1
    val earthRotationAnimationInfiniteTransition = rememberInfiniteTransition()
    return earthRotationAnimationInfiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = -1f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000),
            repeatMode = RepeatMode.Reverse
        )
    )
}



// 水墨动画
@Composable
fun InkColorCanvas() {
    val imageBitmap = getBitmap(R.drawable.tetris_color)
    val imageBitmapDefault = getBitmap(R.drawable.tetris_color_grey)
    val screenOffset = remember { mutableStateOf(Offset(0f, 0f)) }
    val animalState = remember { mutableStateOf(false) }

    val animal: Float by animateFloatAsState(
        if (animalState.value) {
            1f
        } else {
            0f
        }, animationSpec = TweenSpec(durationMillis = 6000)
    )
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                coroutineScope {
                    while (true) {
                        val position = awaitPointerEventScope {
                            awaitFirstDown().position
                        }
                        launch {
                            screenOffset.value = Offset(position.x, position.y)
                            animalState.value = !animalState.value
                        }

                    }
                }
            }
    ) {
        drawIntoCanvas { canvas ->
            val multiColorBitmap = Bitmap.createScaledBitmap(
                imageBitmap.asAndroidBitmap(),
                size.width.toInt(),
                size.height.toInt(),
                false
            )
            val blackColorBitmap = Bitmap.createScaledBitmap(
                imageBitmapDefault.asAndroidBitmap(),
                size.width.toInt(),
                size.height.toInt(),
                false
            )
            val paint = androidx.compose.ui.graphics.Paint().asFrameworkPaint()
            canvas.nativeCanvas.drawBitmap(multiColorBitmap, 0f, 0f, paint) //绘制图片
            //保存图层
            val layerId: Int = canvas.nativeCanvas.saveLayer(
                0f,
                0f,
                size.width,
                size.height,
                paint,
            )
            canvas.nativeCanvas.drawBitmap(blackColorBitmap, 0f, 0f, paint)
            // PorterDuffXfermode 设置画笔的图形混合模式
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
            val xbLength = sqrt(size.width.toDouble().pow(2.0) + size.height.toDouble().pow(2)).toFloat() * animal
            // 画圆
            //            canva.nativeCanvas.drawCircle(
            //                scrrenOffset.value.x,
            //                scrrenOffset.value.y,
            //                xbLength,
            //                paint
            //            )
            val path = Path().asAndroidPath()
            path.moveTo(screenOffset.value.x, screenOffset.value.y)
            //随便绘制了个区域。当然了为了好看曲线可以更美。
            if (xbLength > 0) {
                path.addOval(
                    RectF(
                        screenOffset.value.x - xbLength,
                        screenOffset.value.y - xbLength,
                        screenOffset.value.x + 100f + xbLength,
                        screenOffset.value.y + 130f + xbLength
                    ),
                    android.graphics.Path.Direction.CCW
                )
                path.addCircle(
                    screenOffset.value.x, screenOffset.value.y, 100f + xbLength,
                    android.graphics.Path.Direction.CCW
                )
                path.addCircle(
                    screenOffset.value.x-100, screenOffset.value.y-100, 50f + xbLength,
                    android.graphics.Path.Direction.CCW
                )
            }
            path.close()
            canvas.nativeCanvas.drawPath(path, paint)
            //画布斜边
            paint.xfermode = null
            canvas.nativeCanvas.restoreToCount(layerId)
        }
    }
}

@Composable
fun getBitmap(resource: Int): ImageBitmap {
    return ImageBitmap.Companion.imageResource(resource)
}

// 旋转太阳的动画
@Composable
fun AnimateSun(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition()

    val animateTween by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(5000), RepeatMode.Restart)
    )

    Canvas(modifier.rotate(animateTween)) {

        val radius = size.width / 6
        val stroke = size.width / 20
        val centerOffset = Offset(size.width / 30, size.width / 30)

        // draw circle
        drawCircle(
            color = Color.Black,
            radius = radius + stroke / 2,
            style = Stroke(width = stroke),
            center = center + centerOffset
        )
        drawCircle(
            color = Color.White,
            radius = radius,
            style = Fill,
            center = center + centerOffset
        )

        // draw line

        val lineLength = radius * 0.6f
        val lineOffset = radius * 1.8f
        (0..7).forEach { i ->
            val radians = Math.toRadians(i * 45.0)
            val offsetX = lineOffset * cos(radians).toFloat()
            val offsetY = lineOffset * sin(radians).toFloat()

            val x1 = size.width / 2 + offsetX + centerOffset.x
            val x2 = x1 + lineLength * cos(radians).toFloat()

            val y1 = size.height / 2 + offsetY + centerOffset.y
            val y2 = y1 + lineLength * sin(radians).toFloat()

            drawLine(
                color = Color.Black,
                start = Offset(x1, y1),
                end = Offset(x2, y2),
                strokeWidth = stroke,
                cap = StrokeCap.Round
            )
        }
    }
}

// 画动态太阳
@Composable
fun Sun(modifier: Modifier = Modifier) {
    Canvas(modifier) {

        val radius = size.width / 6
        val stroke = size.width / 20

        // draw circle
        drawCircle(
            color = Color.Black,
            radius = radius + stroke / 2,
            style = Stroke(width = stroke),
        )
        drawCircle(
            color = Color.White,
            radius = radius,
            style = Fill,
        )

        // draw line

        val lineLength = radius * 0.2f
        val lineOffset = radius * 1.8f
        (0..7).forEach { i ->

            val radians = Math.toRadians(i * 45.0)

            val offsetX = lineOffset * cos(radians).toFloat()
            val offsetY = lineOffset * sin(radians).toFloat()

            val x1 = size.width / 2 + offsetX
            val x2 = x1 + lineLength * cos(radians).toFloat()

            val y1 = size.height / 2 + offsetY
            val y2 = y1 + lineLength * sin(radians).toFloat()

            drawLine(
                color = Color.Black,
                start = Offset(x1, y1),
                end = Offset(x2, y2),
                strokeWidth = stroke,
                cap = StrokeCap.Round
            )
        }
    }
}

// 3D页面下翻动画
@Composable
fun DropDownAnimate(
    text: String,
    modifier: Modifier = Modifier,
    initiallyOpened: Boolean = false,
    content: @Composable () -> Unit
) {
    var isOpen by remember {
        mutableStateOf(initiallyOpened)
    }
    val alpha = animateFloatAsState(
        targetValue = if(isOpen) 1f else 0f,
        animationSpec = tween(
            durationMillis = 300
        )
    )
    val rotateX = animateFloatAsState(
        targetValue = if(isOpen) 0f else -90f,
        animationSpec = tween(
            durationMillis = 300
        )
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable {
                    isOpen = !isOpen
                }
                .fillMaxWidth()
        ) {
            Text(
                text = text,
                color = MaterialTheme.colors.surface,
                fontSize = 16.sp
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Open or close the drop down",
                tint = MaterialTheme.colors.surface,
                modifier = Modifier
                    .scale(1f, if (isOpen) -1f else 1f)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    transformOrigin = TransformOrigin(0.5f, 0f)
                    rotationX = rotateX.value
                }
                .alpha(alpha.value)
        ) {
            content()
        }
    }
}

// 3D android
const val maxAngle = 50f
val maxTranslation = 140.dp.value

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Preview
@Composable
fun Parallax() {
    var angle by remember { mutableStateOf(Pair(0f, 0f)) }
    var start by remember { mutableStateOf(Pair(-1f, -1f)) }
    var viewSize by remember { mutableStateOf(Size.Zero) }
    Box(modifier =Modifier//.fillMaxSize()
        //.verticalScroll(rememberScrollState())
        .background(color = Color.Black)

    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(450.dp)
                .height(470.dp)
                .background(Color.Black)
                .onGloballyPositioned { coordinates ->
                    viewSize = Size(
                        width = coordinates.size.width.toFloat(),
                        height = coordinates.size.height.toFloat()
                    )
                }
                .pointerInteropFilter { m ->
                    when (m.action) {
                        MotionEvent.ACTION_UP -> {
                            start = Pair(-1f, -1f)
                        }
                        MotionEvent.ACTION_DOWN -> {
                            start = Pair(m.rawX, m.rawY)
                        }
                        MotionEvent.ACTION_MOVE -> {
                            if (viewSize != Size.Zero) {
                                val end = Pair(m.rawX, m.rawY)
                                val newAngle = getRotationAngles(start, end, viewSize)
                                var x: Float = angle.first + newAngle.first
                                var y: Float = angle.second + newAngle.second

                                if (x > maxAngle) x = maxAngle
                                else if (x < -maxAngle) x = -maxAngle

                                if (y > maxAngle) y = maxAngle
                                else if (y < -maxAngle) y = -maxAngle

                                angle = Pair(x, y)
                                start = end
                            }
                        }
                    }
                    true
                }
        ) {
            Box(contentAlignment = Alignment.TopCenter) {
                Text(
                    text = "Contribute Information",
                    color = Color.White,
                    fontSize = 40.sp,
                    fontFamily = FontFamily.Cursive,
                    modifier = Modifier
                        .alpha(0.8f)
                        .padding(start = 5.dp)
                )
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .size(400.dp)
            ) {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    backgroundColor = Color.White,
                    elevation = 0.dp,
                    modifier = Modifier
                        .graphicsLayer(
                            transformOrigin = TransformOrigin(0.5f, 0.5f),
                            rotationY = animateFloatAsState(-angle.first).value,
                            rotationX = animateFloatAsState(angle.second).value,
                            cameraDistance = 16.dp.value
                        )
                        .width(320.dp)
                        .aspectRatio(1f)
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.back),
                        contentDescription = "Parallax Background",
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(contentAlignment = Alignment.BottomStart) {
                        Text(
                            text = "compose_version = 1.0.0-beta08\n"+
                                    "kotlin_version = 1.5.10\n"+
                                    "Running in SDK 30, minSdk = 21",
                            color = Color.Black,
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Serif,
                            modifier = Modifier
                                .alpha(0.8f)
                                .padding(start = 12.dp)
                        )
                    }
                    Box(contentAlignment = Alignment.TopCenter) {
                        Text(
                            text = "Contributers: 王雨潇、张煜玮、西鹏飞\n" +
                                    "Language: Kotlin\n"+
                                    "Framework: Jetpack compose",
                            color = Color.DarkGray,
                            fontSize = 14.sp,
                            fontFamily = FontFamily.Serif,
                            modifier = Modifier
                                .alpha(0.8f)
                                .padding(start = 12.dp)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .graphicsLayer(
                            transformOrigin = TransformOrigin(0.5f, 0.5f),
                            rotationY = animateFloatAsState(-angle.first).value,
                            rotationX = animateFloatAsState(angle.second).value,
                            cameraDistance = 16.dp.value,
                            translationX = -getTranslation(angle.first, maxTranslation),
                            translationY = -getTranslation(angle.second, maxTranslation)
                        )
                        .size(320.dp)
                        .align(Alignment.Center)
                ){
                    Image(imageVector = ImageVector.vectorResource(id = R.drawable.front), contentDescription = "Parallax Background")
                }

            }
            Box(contentAlignment = Alignment.BottomEnd) {
                Text(
                    text = "Made in Augest, 2021",
                    color = Color.White,
                    fontSize = 25.sp,
                    fontFamily = FontFamily.Cursive,
                    modifier = Modifier
                        .alpha(0.8f)
                        .padding(start = 5.dp)
                )
            }
        }

    }

}

/**
 * This method converts the current touch input to rotation values based on the original point
 * at which the touch event started.
 *
 * @param start : coordinates of first touch event
 * @param end : coordinates of final touch event
 */
fun getRotationAngles(
    start: Pair<Float, Float>,
    end: Pair<Float, Float>,
    size: Size
): Pair<Float, Float> {
    /**
     * 1. get the magnitude of drag event, based on screen's width & height & acceleration
     * 2. get the direction/angle of the drag event
     */
    val acceleration = 3
    val distances = getDistances(end, start)
    val rotationX = (distances.first / size.width) * maxAngle * acceleration
    val rotationY = (distances.second / size.height) * maxAngle * acceleration
    return Pair(rotationX, rotationY)
}

fun getDistances(p1: Pair<Float, Float>, p2: Pair<Float, Float>): Pair<Float, Float> {
    return Pair(
        p2.first - p1.first,
        p2.second - p1.second
    )
}

fun getTranslation(angle: Float, maxDistance: Float): Float {
    return (angle/90f) * maxDistance
}



// rocker launch
@Composable
fun Rocket(
    isRocketEnabled: Boolean,
    maxWidth: Dp,
    maxHeight: Dp
) {
    val resource: Painter
    val modifier: Modifier
    val rocketSize = 100.dp
    if(!isRocketEnabled){
        resource = painterResource(id = R.drawable.rocket_intial)
        modifier = Modifier.offset(
            y = maxHeight - rocketSize,
        )
    }
    else{
        val infiniteTransition = rememberInfiniteTransition()
        val engineState = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 500,
                    easing = LinearEasing
                )
            )
        )
        val xPositionState = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 2000,
                    easing = LinearEasing
                )
            )
        )
        if (engineState.value <= .5f) {
            resource = painterResource(id = R.drawable.rocket1)
        } else {
            resource = painterResource(id = R.drawable.rocket2)
        }
        modifier = Modifier.offset(
            x = (maxWidth - rocketSize) * xPositionState.value,
            y = (maxHeight - rocketSize) - (maxHeight - rocketSize) * xPositionState.value,
        )
    }
    Image(
        modifier = modifier
            .width(rocketSize)
            .height(rocketSize),
        painter = resource,
        contentDescription = "A Rocket",
    )
}


@ExperimentalAnimationApi
@Composable
fun LaunchButton(
    animationState: Boolean,
    onToggleAnimationState: () -> Unit,
){
    Column{
        Text(
            text = "    Shoot for the moon,\n" +
                    "   even if you miss,\n" +
                    "   you'll land among the star.\n" +
                    "                   ——Les Brown",
            color = Color.White,
            fontSize = 30.sp,
            fontFamily = FontFamily.Cursive,
            modifier = Modifier
                .alpha(0.8f)
                .padding(start = 5.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Row{
            MultiColorSmoothText(
                text ="   追寻月之所向，\n" +
                        "   纵使交错而过，\n" +
                        "   也将置身于繁星之间。\n",
                modifier = Modifier.padding(5.dp),
                style = MaterialTheme.typography.h6,
                duration = 1200
            )
            Icon(
                tint = PastelRainbowYellow,
                painter = painterResource(id = R.drawable.ic_moonstar),
                contentDescription = null // decorative element
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ){
            if(animationState){
                Button(
                    onClick = onToggleAnimationState,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Rainbow1, contentColor = Color.White)
                ) {
                    Text("STOP")
                }

            }
            else{
                Button(
                    onClick = onToggleAnimationState,
                ) {
                    Text("LAUNCH")
                }
            }
        }
    }
}


//ImageCard
@Composable
fun ImageCard(
    painter: Painter,
    contentDescription: String,
    title: String,
    modifier:Modifier=Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),  //形状
        elevation = 5.dp
    ) {
        Box(modifier = Modifier.height(200.dp)) {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop
            )
            Box(modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(  // 黑色透明背景
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        ),
                        startY = 300f
                    )
                )
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(title, style = TextStyle(color = Color.White, fontSize = 16.sp))
            }
        }
    }
}