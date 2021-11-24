package com.example.spacex

import android.app.Application
import android.app.Notification
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.example.spacex.util.NotificationHelper
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    private val notificationHelper = NotificationHelper()

    override fun onCreate() {
        super.onCreate()
        notificationHelper.createNotificationChannel(this)
    }

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}