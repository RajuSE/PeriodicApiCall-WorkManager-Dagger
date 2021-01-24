/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.devx.raju.ui.viewmodel;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.devx.raju.R;
import com.devx.raju.ui.activity.GithubListActivity;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;


public final class WorkerUtils {
    private static final String TAG = WorkerUtils.class.getSimpleName();
    private static final Gson gson = new Gson();
//    private static Type bookType = new TypeToken<List<Book>>() {
//    }.getType();

    /**
     * Create a Notification that is shown as a heads-up notification if possible.
     * <p>
     * For this codelab, this is used to show a notification so that you know when different steps
     * of the background work chain are starting
     *
     * @param message Message shown on the notification
     * @param context Context needed to create Toast
     */
    static void makeStatusNotification(String message, String title, Context context) {

        // Make a channel if necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            CharSequence name = Constants.VERBOSE_NOTIFICATION_CHANNEL_NAME;
            String description = Constants.VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION;
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel =
                    new NotificationChannel(Constants.CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // Add the channel
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(context, GithubListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        Date date = (new Date(System.currentTimeMillis()));
        String readableDate = new SimpleDateFormat("dd MMM yyyy hh.mm aa").format(date);

        if (title.isEmpty()) title = "Background sync started";
        else title = Constants.NOTIFICATION_TITLE;

        // Create the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Constants.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(message + " at " + readableDate)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setVibrate(new long[0])
                .setAutoCancel(true);

        // Show the notification
        NotificationManagerCompat.from(context).notify(Constants.NOTIFICATION_ID, builder.build());
    }

    /**
     * Method for sleeping for a fixed about of time to emulate slower work
     */
    static void sleep() {
        try {
            Thread.sleep(Constants.DELAY_TIME_MILLIS, 0);
        } catch (InterruptedException e) {
            Log.d("exc", e.getMessage());
        }
    }


//    //TODO: replace with Generics for params, return type and TypeToken Params
//    public static String toJson(List<Book> books) {
//        return gson.toJson(books, bookType);
//    }
//
//
//    public static List<Book> fromJson(String data) {
//        return gson.fromJson(data, bookType);
//    }

    private WorkerUtils() {
    }
}