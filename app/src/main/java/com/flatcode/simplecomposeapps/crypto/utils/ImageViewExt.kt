package com.flatcode.simplecomposeapps.crypto.utils

import android.widget.ImageView
import coil.load
import com.flatcode.simplecomposeapps.R

fun ImageView.loadImage(url: String) {
    this.load(url) {
        crossfade(true)
        placeholder(R.color.image_profile)
        error(R.color.image_profile)
    }
}