package com.example.android.pictureinpicture

import android.os.SystemClock

class ClockRepository {
    fun getUptimeMillis(): Long {
        return SystemClock.uptimeMillis()
    }
}
