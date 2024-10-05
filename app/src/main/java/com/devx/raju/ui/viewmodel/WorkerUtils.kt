package com.devx.raju.ui.viewmodel

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.devx.raju.R
import com.devx.raju.ui.activity.GithubListActivity
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

object WorkerUtils {
    private val TAG = WorkerUtils::class.java.simpleName
    fun makeStatusNotification(message: String, title: String, context: Context) {

        // Make a channel if necessary
        var title = title
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            val name = Constants.VERBOSE_NOTIFICATION_CHANNEL_NAME
            val description = Constants.VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(Constants.CHANNEL_ID, name, importance)
            channel.description = description

            // Add the channel
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager?.createNotificationChannel(channel)
        }

        // Create an explicit intent for an Activity in your app
        val intent = Intent(context, GithubListActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0, intent,
            PendingIntent.FLAG_IMMUTABLE)
        val date = Date(System.currentTimeMillis())
        val readableDate = SimpleDateFormat("dd MMM yyyy hh.mm aa").format(date)
        title = if (title.isEmpty()) "Background sync started" else Constants.NOTIFICATION_TITLE

        // Create the notification
        val builder = NotificationCompat.Builder(context, Constants.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText("$message at $readableDate")
                .setPriority(NotificationCompat.PRIORITY_HIGH) // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setVibrate(LongArray(0))
                .setAutoCancel(true)

        // Show the notification
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        NotificationManagerCompat.from(context).notify(Constants.NOTIFICATION_ID, builder.build())
    }


}