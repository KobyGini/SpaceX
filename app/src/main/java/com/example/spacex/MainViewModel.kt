package com.example.spacex

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.spacex.worker.HiltSchedulerWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    @ApplicationContext context: Context
) : ViewModel(){
    private val schedulerWorker = WorkManager.getInstance(context)

    fun updateApiContent(){

        val hiltPeriodicSaveRequest =
            PeriodicWorkRequestBuilder<HiltSchedulerWorker>(1,TimeUnit.DAYS)
                // Additional configuration
                .build()
        schedulerWorker.enqueue(hiltPeriodicSaveRequest)
    }


}