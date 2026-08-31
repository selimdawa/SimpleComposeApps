package com.flatcode.simplecomposeapps.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import java.util.Locale

fun Context.openActivity(activityClass: Class<out Activity>?, finish: Boolean = false) {
    if (activityClass == null) return
    val intent = Intent(this, activityClass)
    startActivity(intent)
    if (finish && this is Activity) {
        this.finish()
    }
}

fun Long.formatDuration(): String {
    val seconds = (this / 1000) % 60
    val minutes = (this / (1000 * 60)) % 60
    val hours = (this / (1000 * 60 * 60)) % 24

    return if (hours > 0) {
        String.format(Locale.ENGLISH, "%02d:%02d:%02d", hours, minutes, seconds)
    } else {
        String.format(Locale.ENGLISH, "%02d:%02d", minutes, seconds)
    }
}
