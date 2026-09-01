package com.flatcode.simplecomposeapps.utils

import android.app.Activity
import android.content.Context
import android.content.Intent

fun Context.openActivity(activityClass: Class<out Activity>?, finish: Boolean = false) {
    if (activityClass == null) return
    val intent = Intent(this, activityClass)
    startActivity(intent)
    if (finish && this is Activity) {
        finish()
    }
}

inline fun <reified T : Activity> Context.openActivity(finish: Boolean = false) {
    val intent = Intent(this, T::class.java)
    startActivity(intent)
    if (finish && this is Activity) {
        finish()
    }
}
