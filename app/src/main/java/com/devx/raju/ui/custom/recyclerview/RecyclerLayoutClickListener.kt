package com.devx.raju.ui.custom.recyclerview

import android.view.View
import com.devx.raju.data.local.entity.GithubEntity

interface RecyclerLayoutClickListener {
    fun redirectToDetailScreen(imageView: View,
                               titleView: View,
                               revealView: View,
                               languageView: View,
                               githubEntity: GithubEntity)

    fun sharePost(githubEntity: GithubEntity)
}