package com.devx.raju.ui.activity

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
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
import com.devx.raju.utils.AppUtils
import com.devx.raju.utils.InternetUtil
import com.devx.raju.utils.NavigatorUtils
import com.devx.raju.utils.ShareUtils
import com.devx.raju.utils.ToastUtil
import dagger.android.AndroidInjection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 0);
        }

        githubListViewModel?.loadLocalData()
        lifecycleScope.launch {
                githubListViewModel!!.stateFlow.distinctUntilChanged { old, neww ->
                    println("old:${old.size} _ neww:${neww.size}")

                    old.size >0 && old.size == neww.size }
                    .collectLatest { repositories: List<GithubEntity> ->

                        println("inside collectLatest TH: ${Thread.currentThread().name} ${lifecycle.currentState}")

                        Log.i(SyncDataWorker.TAG, "Lx:" + repositories.size)

                        withContext(Dispatchers.Main) {
                           // println("withContext called TH:${Thread.currentThread().name}")
                            //ToastUtil.showShort("Loaded new data", this@GithubListActivity)

                            if (repositories.isEmpty()) {
                                println("emptyy no data")
                                displayEmptyView()
                            } else if (githubListAdapter!!.itemCount == 0) {
                                if (!repositories.isEmpty()) {
                                    animateView(repositories)
                                } else {
                                    println("displayEmptyView no data")
                                    displayEmptyView()
                                }
                            } else if (repositories.isNotEmpty())
                                displayDataView(repositories)
                            else {
                                println("displayEmptyView called")
                                displayEmptyView()
                            }


                        }

                    }
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

        githubListViewModel!!.viewModelScope.launch(Dispatchers.IO) {

            githubListViewModel?.getOutputWorkInfo()
                ?.collectLatest { listOfWorkInfo: List<WorkInfo> ->
                    if (listOfWorkInfo.isEmpty()) {
                        return@collectLatest
                    }

                    val workInfo: WorkInfo = listOfWorkInfo.first()

                    Log.i(
                        SyncDataWorker.TAG,
                        "work state:: " + workInfo.state + " sZ: " + listOfWorkInfo.size
                    )
                    if (workInfo.state == WorkInfo.State.ENQUEUED || workInfo.state == WorkInfo.State.SUCCEEDED) {
                        Log.i(SyncDataWorker.TAG, "Received ${workInfo.state}")


                            println("current TH: ${Thread.currentThread().name}")

                        println("lifecycle.currentState: ${lifecycle.currentState}")

                        githubListViewModel?.loadLocalData()

//                        repeatOnLifecycle(Lifecycle.State.RESUMED) {

                            /*localdata?.collectLatest { repositories: List<GithubEntity> ->
                                println("inside let called TH: ${Thread.currentThread().name} ${lifecycle.currentState}")

                                Log.i(SyncDataWorker.TAG, "Lx:" + repositories.size)

                                withContext(Dispatchers.Main) {
                                    println("collect called TH:${Thread.currentThread().name}")

                                    ToastUtil.showShort("Loaded new data", this@GithubListActivity)

                                    if (repositories.isEmpty()) {
                                        println("emptyy no data")
                                        displayEmptyView()
                                    } else if (githubListAdapter!!.itemCount == 0) {
                                        if (!repositories.isEmpty()) {
                                            animateView(repositories)
                                        } else if (workInfo.state != WorkInfo.State.ENQUEUED || !InternetUtil.isConnectionOn(
                                                this@GithubListActivity
                                            )
                                        ) {
                                            println("displayEmptyView no data")
                                            displayEmptyView()
                                        }
                                    } else if (repositories.isNotEmpty())
                                        displayDataView(repositories)
                                    else {
                                        println("displayEmptyView called")
                                        displayEmptyView()
                                    }


                                }
                            }*/
//                        }
                    } else {
                        showWorkInProgress()
                    }


                }
        }


    }

    private fun showWorkInProgress() {
    }

    private fun showWorkFinished() {
    }

    private fun initialiseViewModel() {
        githubListViewModel =
            ViewModelProvider(this, viewModelFactory).get(GithubListViewModel::class.java)

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

    override fun redirectToDetailScreen(
        imageView: View,
        titleView: View,
        revealView: View,
        languageView: View,
        githubEntity: GithubEntity,
    ) {
        NavigatorUtils.redirectToDetailScreen(
            this, githubEntity,
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, *AppUtils.getTransitionElements(
                    applicationContext, imageView, titleView, revealView, languageView
                ) as Array<out Pair<View, String>>
            )
        )
    }

    override fun sharePost(githubEntity: GithubEntity) {
        ShareUtils.shareUrl(this, githubEntity.htmlUrl)
    }
}