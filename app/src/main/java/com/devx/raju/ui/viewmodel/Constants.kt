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
package com.devx.raju.ui.viewmodel

object Constants {
    // Name of Notification Channel for verbose notifications of background work
    val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence = "Github Sync Notification"
    const val KEY_OUTPUT_DATA = "KEY_OUTPUT_DATA"
    var VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION = "Shows notifications whenever work starts"
    const val NOTIFICATION_TITLE = "Finished fetching data & stored in DB"
    const val CHANNEL_ID = "GITHUB_VERBOSE_NOTIFICATION"
    const val NOTIFICATION_ID = 1

    // The name of the Sync Data work
    const val SYNC_DATA_WORK_NAME = "sync_data_work_name"

    // Other keys
    const val DELAY_TIME_MILLIS: Long = 3000
    const val TAG_SYNC_DATA = "TAG_SYNC_DATA"
}