package com.example.spacex.worker

import android.content.Context
import android.os.Bundle
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.spacex.repository.Repository
import com.example.spacex.util.BundleConstants.LAUNCH_DETAILS
import com.example.spacex.util.BundleConstants.LAUNCH_ID
import com.example.spacex.util.BundleConstants.LAUNCH_NAME
import com.example.spacex.util.NotificationHelper
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

    private val notificationHelper = NotificationHelper()

    override suspend fun doWork(): Result {

        //Get new launches and show notification
        withContext(Dispatchers.IO) {
            val newLaunches = repository.updateContents()
            newLaunches?.let {
                if (it.isNotEmpty()) {
                    val bundle = Bundle()
                    bundle.putString(LAUNCH_ID,it.last().id)
                    bundle.putString(LAUNCH_NAME,it.last().missionName)
                    bundle.putString(LAUNCH_DETAILS,it.last().details)
                    notificationHelper.createNavDeepLinkToLaunchDetails(appContext,bundle)
                }
            }
        }
        return Result.success()
    }
}