package com.devx.raju.ui.activity

import android.app.Instrumentation
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.devx.raju.AppConstants
import com.devx.raju.R
import com.devx.raju.data.local.entity.GithubEntity
import com.devx.raju.databinding.ActivityRepoDetailBinding
import com.devx.raju.utils.AppUtils
import com.devx.raju.utils.NavigatorUtils
import com.devx.raju.utils.ShareUtils
import com.squareup.picasso.Picasso

class GithubDetailActivity : AppCompatActivity() {
    lateinit var githubEntity: GithubEntity
    lateinit var binding: ActivityRepoDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialiseView()
    }

    private fun initialiseView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_repo_detail)
        githubEntity = intent.getParcelableExtra(AppConstants.INTENT_POST)!!
        Picasso.get().load(githubEntity.owner?.avatarUrl)
                .placeholder(R.drawable.ic_placeholder)
                .into(binding.itemProfileImg)
        binding.itemTitle.text = githubEntity.fullName
        binding.itemStars.text = githubEntity.starsCount.toString()
        binding.itemWatchers.text = githubEntity.watchers.toString()
        binding.itemForks.text = githubEntity.forks.toString()
        if (githubEntity.language != null) {
            binding.itemImgLanguage.visibility = View.VISIBLE
            binding.itemLanguage.visibility = View.VISIBLE
            binding.itemLanguage.text = githubEntity.language
            updateColorTheme()
        } else {
            binding.itemImgLanguage.visibility = View.INVISIBLE
            binding.itemLanguage.visibility = View.INVISIBLE
        }
        binding.btnShare.setOnClickListener { v: View? -> ShareUtils.shareUrl(this@GithubDetailActivity, githubEntity.htmlUrl) }
        binding.btnVisit.setOnClickListener { v: View? -> NavigatorUtils.openBrowser(this@GithubDetailActivity, githubEntity.htmlUrl) }
    }

    private fun updateColorTheme() {
        val bgColor = AppUtils.getColorByLanguage(applicationContext, githubEntity.language)
        binding.appBarLayout.setBackgroundColor(bgColor)
        binding.mainToolbar.toolbar.setBackgroundColor(bgColor)
        binding.btnShare.setTextColor(bgColor)
        binding.itemImgLanguage.setImageDrawable(AppUtils.updateGradientDrawableColor(applicationContext, bgColor))
        binding.btnVisit.setBackgroundDrawable(AppUtils.updateStateListDrawableColor(resources.getDrawable(R.drawable.btn_visit), bgColor))
        binding.btnShare.setBackgroundDrawable(AppUtils.updateStateListDrawableStrokeColor(resources.getDrawable(R.drawable.btn_share), bgColor))
        AppUtils.updateStatusBarColor(this, bgColor)
    }
}