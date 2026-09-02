package com.flatcode.simplecomposeapps

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.VideoFrameDecoder
import dagger.hilt.android.HiltAndroidApp
import io.selimdawa.multicolors.MultiColorManager

@HiltAndroidApp
class Application : Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()
        MultiColorManager.init(this)
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this).components {
                add(VideoFrameDecoder.Factory())
            }.crossfade(true).build()
    }
}