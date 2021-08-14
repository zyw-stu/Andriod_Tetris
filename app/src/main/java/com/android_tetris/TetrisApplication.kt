package com.android_tetris

import android.app.Application
import com.android_tetris.utils.SoundUtil

class TetrisApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SoundUtil.init(this)
    }
}