package com.example.spacex.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import com.example.spacex.MainActivity
import com.example.spacex.R
import com.example.spacex.util.BundleConstants.LAUNCH_DETAILS
import com.example.spacex.util.BundleConstants.LAUNCH_NAME

class NotificationHelper {

    fun createNotificationChannel(context:Context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = Constants.CHANNEL_NAME
            val descriptionText = Constants.CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(Constants.CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun createNavDeepLinkToLaunchDetails(
        context: Context,
        bundle: Bundle,
    ) {

        val builder = NotificationCompat.Builder(context, Constants.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launch_rocket)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationId = 12543

        val pendingIntent: PendingIntent = NavDeepLinkBuilder(context)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.launchDetailsFragment)
            .setArguments(bundle)
            .createPendingIntent()

        val launchName = bundle.getString(LAUNCH_NAME)
        val launchDetails = bundle.getString(LAUNCH_DETAILS)

        builder.setContentTitle(launchName)
            .setContentText(launchDetails)
            .setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, builder.build())
        }
    }
}