package com.devx.raju.utils;

import android.app.Activity;
import androidx.core.app.ShareCompat;

public class ShareUtils {

    public static void shareUrl(Activity activity,
                                String url) {
        ShareCompat.IntentBuilder
                .from(activity)
                .setType("text/plain")
                .setChooserTitle("Share URL")
                .setText(url)
                .startChooser();
    }
}
