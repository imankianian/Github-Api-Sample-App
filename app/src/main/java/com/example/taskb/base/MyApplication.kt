package com.example.taskb.base

import Motrack
import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.motrack.sdk.LogLevel
import com.motrack.sdk.MotrackConfig
import com.motrack.sdk.MotrackEvent
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application(), ImageLoaderFactory {

    val TAG = "Motrack"
    override fun onCreate() {
        super.onCreate()
        val appToken = "rMN5ZCwpOzY7"
        val environment: String = MotrackConfig.ENVIRONMENT_SANDBOX
        val config = MotrackConfig(this, appToken, environment)
        Motrack.onCreate(config)
        Motrack.setEnabled(true)
        registerActivityLifecycleCallbacks(MyActivityLifecycleCallbacks())
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            Log.d(TAG, "FCM token: $token")
            Motrack.setPushToken(token, this)
        })
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .diskCache(
                DiskCache.Builder()
                    .directory(this.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.1)
                    .build())
            .respectCacheHeaders(false)
            .crossfade(true).build()
    }

    class MyActivityLifecycleCallbacks: Application.ActivityLifecycleCallbacks {

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        }

        override fun onActivityStarted(activity: Activity) {
        }

        override fun onActivityResumed(activity: Activity) {
            Motrack.onResume()
        }

        override fun onActivityPaused(activity: Activity) {
            Motrack.onPause()
        }

        override fun onActivityStopped(activity: Activity) {
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        }

        override fun onActivityDestroyed(activity: Activity) {
        }
    }
}