package com.android_tetris.utils

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.WindowManager
import androidx.annotation.ColorInt

object StatusBarUtil {

    fun transparentStatusBar(activity: Activity) {
        setStatusBarColor(activity, Color.TRANSPARENT)
    }

    fun setStatusBarColor(activity: Activity, @ColorInt color: Int) {
        with(activity.window) {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or decorView.systemUiVisibility
            statusBarColor = color
        }
    }

}