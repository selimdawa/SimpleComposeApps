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

inline fun <reified T : Activity> Context.openActivity(finish: Boolean = false) {
    val intent = Intent(this, T::class.java)
    startActivity(intent)
    if (finish && this is Activity) {
        this.finish()
    }
}

fun Context.intent1(cls: Class<*>, block: Intent.() -> Unit) {
    val intent = Intent(this, cls)
    intent.apply(block)
    startActivity(intent)
}

inline fun <reified T : Activity> Context.launchActivity(
    finish: Boolean = false,
    noinline block: Intent.() -> Unit = {}
) {
    val intent = Intent(this, T::class.java)
    intent.apply(block)
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