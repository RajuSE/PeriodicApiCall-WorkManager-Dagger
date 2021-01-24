package com.devx.raju.ui.activity;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.devx.raju.R;
import com.devx.raju.data.local.entity.GithubEntity;
import com.devx.raju.databinding.ActivityRepoDetailBinding;
import com.devx.raju.utils.AppUtils;
import com.devx.raju.utils.NavigatorUtils;
import com.devx.raju.utils.ShareUtils;
import com.devx.raju.AppConstants;
import com.squareup.picasso.Picasso;

public class GithubDetailActivity extends AppCompatActivity {

    private GithubEntity githubEntity;
    private ActivityRepoDetailBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialiseView();
    }

    private void initialiseView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_repo_detail);
        githubEntity = getIntent().getParcelableExtra(AppConstants.INTENT_POST);

        Picasso.get().load(githubEntity.getOwner().getAvatarUrl())
                .placeholder(R.drawable.ic_placeholder)
                .into(binding.itemProfileImg);

        binding.itemTitle.setText(githubEntity.getFullName());
        binding.itemStars.setText(String.valueOf(githubEntity.getStarsCount()));
        binding.itemWatchers.setText(String.valueOf(githubEntity.getWatchers()));
        binding.itemForks.setText(String.valueOf(githubEntity.getForks()));

        if(githubEntity.getLanguage() != null) {
            binding.itemImgLanguage.setVisibility(View.VISIBLE);
            binding.itemLanguage.setVisibility(View.VISIBLE);
            binding.itemLanguage.setText(githubEntity.getLanguage());
            updateColorTheme();

        } else {
            binding.itemImgLanguage.setVisibility(View.INVISIBLE);
            binding.itemLanguage.setVisibility(View.INVISIBLE);
        }

        binding.btnShare.setOnClickListener(v -> ShareUtils.shareUrl(GithubDetailActivity.this, githubEntity.getHtmlUrl()));
        binding.btnVisit.setOnClickListener(v -> NavigatorUtils.openBrowser(GithubDetailActivity.this, githubEntity.getHtmlUrl()));
    }


    private void updateColorTheme() {
        int bgColor = AppUtils.getColorByLanguage(getApplicationContext(), githubEntity.getLanguage());

        binding.appBarLayout.setBackgroundColor(bgColor);
        binding.mainToolbar.toolbar.setBackgroundColor(bgColor);
        binding.btnShare.setTextColor(bgColor);
        binding.itemImgLanguage.setImageDrawable(AppUtils.updateGradientDrawableColor(getApplicationContext(), bgColor));
        binding.btnVisit.setBackgroundDrawable(AppUtils.updateStateListDrawableColor(getResources().getDrawable(R.drawable.btn_visit), bgColor));
        binding.btnShare.setBackgroundDrawable(AppUtils.updateStateListDrawableStrokeColor(getResources().getDrawable(R.drawable.btn_share), bgColor));
        AppUtils.updateStatusBarColor(this, bgColor);
    }
}
