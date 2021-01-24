package com.devx.raju.ui.custom.recyclerview;

import android.view.View;

import com.devx.raju.data.local.entity.GithubEntity;

public interface RecyclerLayoutClickListener {

    void redirectToDetailScreen(View imageView,
                                View titleView,
                                View revealView,
                                View languageView,
                                GithubEntity githubEntity);

    void sharePost(GithubEntity githubEntity);
}
