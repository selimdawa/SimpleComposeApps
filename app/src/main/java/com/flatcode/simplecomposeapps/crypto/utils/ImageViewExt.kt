package com.flatcode.simplecomposeapps.crypto.utils

import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.compose.ui.graphics.toArgb
import coil.load
import com.flatcode.simplecomposeapps.ui.theme.image_profile

fun ImageView.loadImage(url: String) {
    val colorDrawable = ColorDrawable(image_profile.toArgb())
    this.load(url) {
        crossfade(true)
        placeholder(colorDrawable)
        error(colorDrawable)
    }
}