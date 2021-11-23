package com.example.spacex.worker

import android.app.PendingIntent
import android.content.Context
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.navigation.NavDeepLinkBuilder
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.spacex.MainActivity
import com.example.spacex.R
import com.example.spacex.repository.Repository
import com.example.spacex.util.Constants
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class HiltSchedulerWorker @AssistedInject constructor(
    @Assisted val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    val repository: Repository
) : CoroutineWorker(appContext, workerParams) {

    private var builder = NotificationCompat.Builder(appContext, Constants.CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launch_rocket)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    private val notificationId = 12543

    override suspend fun doWork(): Result {

        //Get new launches and show notification
        withContext(Dispatchers.IO) {
            val newLaunches = repository.updateContents()
            newLaunches?.let {
                if (it.isNotEmpty()) {

                    val bundle = Bundle()
                    bundle.putString("launchId",it.last().id)
                    val pendingIntent: PendingIntent = NavDeepLinkBuilder(appContext)
                        .setComponentName(MainActivity::class.java)
                        .setGraph(R.navigation.nav_graph)
                        .setDestination(R.id.launchDetailsFragment)
                        .setArguments(bundle)
                        .createPendingIntent()

                    builder.setContentTitle(it.last().missionName)
                        .setContentText(it.last().details)
                        .setContentIntent(pendingIntent)

                    with(NotificationManagerCompat.from(appContext)) {
                        // notificationId is a unique int for each notification that you must define
                        notify(notificationId, builder.build())
                    }
                }
            }
        }
        return Result.success()
    }
}