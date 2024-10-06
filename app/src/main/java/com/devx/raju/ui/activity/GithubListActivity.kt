package com.devx.raju.ui.activity

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.res.colorResource
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.WorkInfo
import com.devx.raju.R
import com.devx.raju.data.local.entity.GithubEntity
import com.devx.raju.databinding.ActivityRepoListBinding
import com.devx.raju.factory.ViewModelFactory
import com.devx.raju.ui.adapter.GithubListAdapter
import com.devx.raju.ui.custom.recyclerview.RecyclerLayoutClickListener
import com.devx.raju.ui.viewmodel.GithubListViewModel
import com.devx.raju.ui.workers.SyncDataWorker
import com.devx.raju.utils.*
import dagger.android.AndroidInjection
import javax.inject.Inject


class GithubListActivity : AppCompatActivity(), RecyclerLayoutClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var binding: ActivityRepoListBinding
    var githubListViewModel: GithubListViewModel? = null
    var githubListAdapter: GithubListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        initialiseViewModel()
        initialiseView()

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 0);
        }

    }



    private fun initialiseView() {

        val sharedPreferences = getSharedPreferences("git", Context.MODE_PRIVATE)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_repo_list)
        setSupportActionBar(binding.mainToolbar.toolbar)
        binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        githubListAdapter = GithubListAdapter(applicationContext, this)
        binding.recyclerView.adapter = githubListAdapter
        AppUtils.updateStatusBarColor(this, resources.getColor(R.color.colorPrimary))

        displayLoader()
        githubListViewModel?.fetchRepositories2()

        githubListViewModel?.getOutputWorkInfo()?.observe(this, Observer<List<WorkInfo>> { listOfWorkInfo: List<WorkInfo> ->

            if (listOfWorkInfo == null || listOfWorkInfo.isEmpty()) {
                return@Observer
            }

            val workInfo: WorkInfo = listOfWorkInfo.get(0)

            Log.i(SyncDataWorker.TAG, "work state:: " + workInfo.state + " sZ: " + listOfWorkInfo.size)
            if (workInfo.state == WorkInfo.State.ENQUEUED||workInfo.state == WorkInfo.State.SUCCEEDED) {
                Log.i(SyncDataWorker.TAG, "Received Success")

                showWorkFinished()
                val localdata = githubListViewModel?.loadLocalData()
               if(localdata==null){
                   println("null localdata")
               }
                localdata?.let {
                    if (!it.hasObservers())
                        it.observe(this, Observer<List<GithubEntity>> { repositories: List<GithubEntity> ->
                            Log.i(SyncDataWorker.TAG, "Lx:" + repositories.size)

                            if(repositories.isEmpty()){
                                println("emptyy no data")
                                displayEmptyView()
                                return@Observer
                            }
                            if (githubListAdapter!!.itemCount == 0) {
                                if (!repositories.isEmpty()) {
                                    animateView(repositories)
                                } else if (workInfo.state != WorkInfo.State.ENQUEUED || !InternetUtil.isConnectionOn(this)) {
                                    println("displayEmptyView no data")
                                    displayEmptyView()
                                }
                            } else if (!repositories.isEmpty()) displayDataView(repositories)else{
                                println("displayEmptyView called")
                                displayEmptyView()
                            }
                        })

                }
            } else {
                showWorkInProgress()
            }
        })

    }

    private fun showWorkInProgress() {
    }

    private fun showWorkFinished() {
    }

    private fun initialiseViewModel() {
        githubListViewModel = ViewModelProvider(this, viewModelFactory).get(GithubListViewModel::class.java)

    }

    private fun displayLoader() {
        binding.viewLoader.rootView.visibility = View.VISIBLE
    }

    private fun hideLoader() {
        binding.viewLoader.rootView.visibility = View.GONE
    }

    private fun animateView(repositories: List<GithubEntity>) {
        hideLoader()
        displayDataView(repositories)
        binding.recyclerView.scheduleLayoutAnimation()
    }

    private fun displayDataView(repositories: List<GithubEntity>) {
        binding.viewEmpty.emptyContainer.visibility = View.GONE
        githubListAdapter!!.setItems(repositories)
    }

    private fun displayEmptyView() {
        if (!InternetUtil.isConnectionOn(this)) ToastUtil.showShort("No Internet", this)
        hideLoader()
        binding.viewEmpty.emptyContainer.visibility = View.VISIBLE
    }

    override fun redirectToDetailScreen(imageView: View, titleView: View, revealView: View, languageView: View, githubEntity: GithubEntity) {
        NavigatorUtils.redirectToDetailScreen(this, githubEntity,
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, *AppUtils.getTransitionElements(
                        applicationContext, imageView, titleView, revealView, languageView
                ) as Array<out Pair<View, String>>))
    }

    override fun sharePost(githubEntity: GithubEntity) {
        ShareUtils.shareUrl(this, githubEntity.htmlUrl)
    }
}