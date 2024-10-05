package com.devx.raju.utils

import android.app.Activity
import androidx.core.app.ShareCompat

object ShareUtils {
    fun shareUrl(activity: Activity?,
                 url: String?) {
        if (activity != null) {
            ShareCompat.IntentBuilder
                .from(activity)
                .setType("text/plain")
                .setChooserTitle("Share URL")
                .setText(url)
                .startChooser()
        }
    }
}