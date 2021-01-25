package com.devx.raju.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import com.devx.raju.AppConstants
import com.devx.raju.data.local.entity.GithubEntity
import com.devx.raju.ui.activity.GithubDetailActivity

object NavigatorUtils {
    fun redirectToDetailScreen(activity: Activity?,
                               githubEntity: GithubEntity?,
                               options: ActivityOptionsCompat) {
        val intent = Intent(activity, GithubDetailActivity::class.java)
        intent.putExtra(AppConstants.INTENT_POST, githubEntity)
        ActivityCompat.startActivity(activity!!, intent, options.toBundle())
    }

    fun openBrowser(activity: Activity,
                    url: String?) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        activity.startActivity(i)
    }
}