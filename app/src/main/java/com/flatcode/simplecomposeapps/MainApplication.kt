package com.flatcode.simplecomposeapps

import android.app.Activity
import android.app.Application
import android.os.Bundle
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.request.crossfade
import coil3.video.VideoFrameDecoder
import dagger.hilt.android.HiltAndroidApp
import io.selimdawa.multicolors.MultiColorManager

@HiltAndroidApp
class MainApplication : Application(), SingletonImageLoader.Factory {

    override fun onCreate() {
        super.onCreate()
        MultiColorManager.init(this)

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                MultiColorManager.applyTheme(activity)
            }

            override fun onActivityStarted(activity: Activity) {}
            override fun onActivityResumed(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {}
        })
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader {
        return ImageLoader.Builder(context).components {
            add(VideoFrameDecoder.Factory())
        }.crossfade(true).build()
    }
}