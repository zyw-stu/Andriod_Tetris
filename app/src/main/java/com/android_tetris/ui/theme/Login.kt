package com.android_tetris.ui.theme


import android.graphics.*
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Shader
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import com.android_tetris.R



/**
 * 登陆背景模糊头缩放部图片
 */
@Composable
fun LoginPageTopBlurImage(
    animatedBitmap: Animatable<Float, AnimationVector1D>,
    animatedOffset: Animatable<Float, AnimationVector1D>,
    animatedScales: Animatable<Float, AnimationVector1D>
) {
    Column() {
        topBarView_Set()  // 添加set界面顶部导航栏
        Image(
            bitmap = BitmapBlur.doBlur(
                getBitmap(resource = R.drawable.mn_2).asAndroidBitmap(),//界面上方背景图
                animatedBitmap.value.toInt(), false
            ).asImageBitmap(),
            contentDescription = "",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(
                    QureytoImageShapes(160f, animatedOffset.value)
                )
                .scale(animatedScales.value, animatedScales.value)
        )
    }
}

//登陆页面头部旋转缩放的图片

@Composable
fun LoginPageTopRotaAndScaleImage(
    animatedColor: Animatable<androidx.compose.ui.graphics.Color, AnimationVector4D>,
    animatedScales: Animatable<Float, AnimationVector1D>,
    animatedOffset: Animatable<Float, AnimationVector1D>
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(0.dp)
            .clip(CicleImageShape())
            .background(animatedColor.value)
            .width((130 * animatedScales.value).dp)
            .height((130 * animatedScales.value).dp)
    ) {
        Image(
            bitmap = getBitmap(R.drawable.mn_3),  // 用户头像
            contentDescription = "w",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .height(80.dp)
                .width(80.dp)
                .background(color = Color(0XFF0DBEBF), shape = CircleShape)
                .padding(3.dp)
                .clip(
                    CircleShape
                )
                .shadow(elevation = 150.dp, clip = true)
                .rotate(
                    animatedOffset.value
                )
        )
    }
}

/**
 * 登陆页面顶部文字
 */
@Composable
fun LoginPageTopTextBox(
    animatedOffset: Animatable<Float, AnimationVector1D>,
    animatedScales: Animatable<Float, AnimationVector1D>
) {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .draggable(state = DraggableState {

                    }, orientation = Orientation.Horizontal, onDragStarted = {
                    }, onDragStopped = {

                    }),
            ) {
                drawIntoCanvas { canvas ->
                    val paint = androidx.compose.ui.graphics.Paint()
                    paint.style = PaintingStyle.Fill
                    paint.color = androidx.compose.ui.graphics.Color.Green
                    val textPaint = Paint()
                    textPaint.strokeWidth = 2f
                    textPaint.style = Paint.Style.FILL
                    textPaint.color = Color.BLACK
                    textPaint.textSize = 52f


                    //测量文字宽度
                    val rect = Rect()
                    textPaint.getTextBounds("BomposeUnit 登陆", 0, 6, rect)
                    val colors = intArrayOf(
                        Color.BLACK,
                        Color.argb(
                            250,
                            121,
                            animatedOffset.value.toInt(),
                            206
                        ),
                        Color.argb(
                            250,
                            121,
                            206,
                            animatedOffset.value.toInt()
                        )
                    )
                    val positions = floatArrayOf(0.2f, 11f, 0.2f)


                    //让渐变动起来从而感觉到文字闪动起来了
                    val transMatrix = Matrix()
                    transMatrix.postTranslate(
                        -rect.width() + rect.width() * 2 * (animatedScales.value * 1.5f),
                        0f
                    )
                    //设置渐变
                    val linearGradient = LinearGradient(
                        0f,
                        0f,
                        rect.width().toFloat(),
                        0f,
                        colors,
                        positions,
                        Shader.TileMode.CLAMP
                    )
                    //设置矩阵变换
                    linearGradient.setLocalMatrix(transMatrix)

                    textPaint.shader = linearGradient
                    //1.坐标变换
                    canvas.nativeCanvas.drawText(
                        "BomposeUnit 登陆",
                        size.width / 3.5f,
                        size.height / 2.5f,
                        textPaint
                    )
                    val secontextPath = navePath()


                    val rect1 = Rect()
                    textPaint.getTextBounds("更多精彩,更多体验 ~", 0, 6, rect1)
                    secontextPath.moveTo(340f, 100f)
                    //0-110
                    if (animatedOffset.value == 0f) {
                        secontextPath.quadTo(350f, 10f, 710f, 100f)

                    }
                    secontextPath.quadTo(animatedOffset.value, 10f, 710f, 100f)

                    textPaint.textSize = 32f
                    textPaint.letterSpacing = 0.3f
                    //canvas.nativeCanvas.drawPath(secontextPath,textPaint)
                    canvas.nativeCanvas.drawTextOnPath(
                        "更多精彩,更多体验 ~",
                        secontextPath,
                        0f,
                        0f,
                        textPaint
                    )
                }

            }
        }
    }
}


//登陆页面输入框

@Composable
fun LoginPageInput(
    inputUserName: MutableState<String>,
    animatedColor: Animatable<androidx.compose.ui.graphics.Color, AnimationVector4D>,
    animatedRound: Animatable<Float, AnimationVector1D>,
    inputPassworld: MutableState<String>
) {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxWidth()
            .padding(top = 60.dp)
    ) {
        TextField(
            value = inputUserName.value,
            onValueChange = {
                inputUserName.value = it.trim()
            },
            // shape = AnimalRoundedCornerShape(animatedRound.value),
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                backgroundColor = androidx.compose.ui.graphics.Color.Transparent
            ),
            modifier = Modifier
                .height(48.dp)
                .border(
                    1.2.dp,
                    //animatedColor.value.copy(alpha = 1f)
                    Color(
                        animatedColor.value.red,
                        animatedColor.value.green,
                        animatedColor.value.blue,
                        1f
                    ),
                    //shape = RoundedCornerShape(18.dp)
                    AnimalRoundedCornerShape(animatedRound.value)
                ),
            leadingIcon = {
                Icon(
                    bitmap = getBitmap(R.drawable.nicheng),
                    contentDescription = ""
                )
            })
    }
}


fun navePath(): Path {
    return Path()
}
@Composable
fun getBitmap(resource: Int): ImageBitmap {
    return ImageBitmap.Companion.imageResource(resource)
}
@Stable
class CicleImageShape(val circle: Float = 0f) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val minWidth = Math.min(size.width - circle, size.width - circle)
        val rect = androidx.compose.ui.geometry.Rect(circle, circle, minWidth, minWidth)
        val path = androidx.compose.ui.graphics.Path()
        path.addOval(rect)
        return Outline.Generic(path)
    }
}

// 动态圆角输入框
@Stable
class AnimalRoundedCornerShape(private val value: Float = 30f) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = androidx.compose.ui.graphics.Path()
        path.lineTo(value, 0f)
        path.cubicTo(value, 0f, 0f, 0f, 0f, value)
        path.lineTo(0f, size.height - value)
        path.cubicTo(0f, size.height - value, 0f, size.height, value, size.height)
        path.quadraticBezierTo(size.width / 2, size.height - value, size.width - value, size.height)
        path.quadraticBezierTo(size.width, size.height, size.width, size.height - value)
        path.lineTo(size.width, value)
        path.quadraticBezierTo(size.width, 0f, size.width - value, 0f)
        path.quadraticBezierTo(size.width / 2, value, value, 0f)
        path.lineTo(value, 0f)
        return Outline.Generic(path)
    }

}

//形状裁剪
@Stable
class QureytoImageShapes(var hudu: Float = 100f, var controller:Float=0f) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = androidx.compose.ui.graphics.Path()
        path.moveTo(0f, 0f)
        path.lineTo(0f, size.height - hudu)
        if(controller==0f){
            controller =size.width / 2f
        }
        path.quadraticBezierTo(controller, size.height, size.width, size.height - hudu)
        path.lineTo(size.width, 0f)
        path.close()
        return Outline.Generic(path)
    }
}

// 模糊效果
object BitmapBlur {
    fun doBlur(sentBitmap: Bitmap, radiu: Int = 1, canReuseInBitmap: Boolean): Bitmap {
        var radius: Int = radiu
        val bitmap: Bitmap = if (canReuseInBitmap) {
            sentBitmap
        } else {
            sentBitmap.copy(sentBitmap.config, true)
        }
        if (radius < 1) {
            radius = 0
        }
        val w = bitmap.width
        val h = bitmap.height
        val pix = IntArray(w * h)
        bitmap.getPixels(pix, 0, w, 0, 0, w, h)
        val wm = w - 1
        val hm = h - 1
        val wh = w * h
        val div = radius + radius + 1
        val r = IntArray(wh)
        val g = IntArray(wh)
        val b = IntArray(wh)
        var rsum: Int
        var gsum: Int
        var bsum: Int
        var x: Int
        var y: Int
        var i: Int
        var p: Int
        var yp: Int
        var yi: Int
        var yw: Int
        val vmin = IntArray(Math.max(w, h))
        var divsum = div + 1 shr 1
        divsum *= divsum
        val dv = IntArray(256 * divsum)
        i = 0
        while (i < 256 * divsum) {
            dv[i] = i / divsum
            i++
        }
        yi = 0
        yw = yi
        val stack = Array(div) {
            IntArray(
                3
            )
        }
        var stackpointer: Int
        var stackstart: Int
        var sir: IntArray
        var rbs: Int
        val r1 = radius + 1
        var routsum: Int
        var goutsum: Int
        var boutsum: Int
        var rinsum: Int
        var ginsum: Int
        var binsum: Int
        y = 0
        while (y < h) {
            bsum = 0
            gsum = bsum
            rsum = gsum
            boutsum = rsum
            goutsum = boutsum
            routsum = goutsum
            binsum = routsum
            ginsum = binsum
            rinsum = ginsum
            i = -radius
            while (i <= radius) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))]
                sir = stack[i + radius]
                sir[0] = p and 0xff0000 shr 16
                sir[1] = p and 0x00ff00 shr 8
                sir[2] = p and 0x0000ff
                rbs = r1 - Math.abs(i)
                rsum += sir[0] * rbs
                gsum += sir[1] * rbs
                bsum += sir[2] * rbs
                if (i > 0) {
                    rinsum += sir[0]
                    ginsum += sir[1]
                    binsum += sir[2]
                } else {
                    routsum += sir[0]
                    goutsum += sir[1]
                    boutsum += sir[2]
                }
                i++
            }
            stackpointer = radius
            x = 0
            while (x < w) {
                r[yi] = dv[rsum]
                g[yi] = dv[gsum]
                b[yi] = dv[bsum]
                rsum -= routsum
                gsum -= goutsum
                bsum -= boutsum
                stackstart = stackpointer - radius + div
                sir = stack[stackstart % div]
                routsum -= sir[0]
                goutsum -= sir[1]
                boutsum -= sir[2]
                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm)
                }
                p = pix[yw + vmin[x]]
                sir[0] = p and 0xff0000 shr 16
                sir[1] = p and 0x00ff00 shr 8
                sir[2] = p and 0x0000ff
                rinsum += sir[0]
                ginsum += sir[1]
                binsum += sir[2]
                rsum += rinsum
                gsum += ginsum
                bsum += binsum
                stackpointer = (stackpointer + 1) % div
                sir = stack[stackpointer % div]
                routsum += sir[0]
                goutsum += sir[1]
                boutsum += sir[2]
                rinsum -= sir[0]
                ginsum -= sir[1]
                binsum -= sir[2]
                yi++
                x++
            }
            yw += w
            y++
        }
        x = 0
        while (x < w) {
            bsum = 0
            gsum = bsum
            rsum = gsum
            boutsum = rsum
            goutsum = boutsum
            routsum = goutsum
            binsum = routsum
            ginsum = binsum
            rinsum = ginsum
            yp = -radius * w
            i = -radius
            while (i <= radius) {
                yi = Math.max(0, yp) + x
                sir = stack[i + radius]
                sir[0] = r[yi]
                sir[1] = g[yi]
                sir[2] = b[yi]
                rbs = r1 - Math.abs(i)
                rsum += r[yi] * rbs
                gsum += g[yi] * rbs
                bsum += b[yi] * rbs
                if (i > 0) {
                    rinsum += sir[0]
                    ginsum += sir[1]
                    binsum += sir[2]
                } else {
                    routsum += sir[0]
                    goutsum += sir[1]
                    boutsum += sir[2]
                }
                if (i < hm) {
                    yp += w
                }
                i++
            }
            yi = x
            stackpointer = radius
            y = 0
            while (y < h) {

                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] =
                    -0x1000000 and pix[yi] or (dv[rsum] shl 16) or (dv[gsum] shl 8) or dv[bsum]
                rsum -= routsum
                gsum -= goutsum
                bsum -= boutsum
                stackstart = stackpointer - radius + div
                sir = stack[stackstart % div]
                routsum -= sir[0]
                goutsum -= sir[1]
                boutsum -= sir[2]
                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w
                }
                p = x + vmin[y]
                sir[0] = r[p]
                sir[1] = g[p]
                sir[2] = b[p]
                rinsum += sir[0]
                ginsum += sir[1]
                binsum += sir[2]
                rsum += rinsum
                gsum += ginsum
                bsum += binsum
                stackpointer = (stackpointer + 1) % div
                sir = stack[stackpointer]
                routsum += sir[0]
                goutsum += sir[1]
                boutsum += sir[2]
                rinsum -= sir[0]
                ginsum -= sir[1]
                binsum -= sir[2]
                yi += w
                y++
            }
            x++
        }
        bitmap.setPixels(pix, 0, w, 0, 0, w, h)
        return bitmap
    }
}


