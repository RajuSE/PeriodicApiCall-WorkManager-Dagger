package com.devx.raju.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.devx.raju.data.local.entity.GithubEntity

@Dao
interface GithubDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepositories(githubEntities: List<GithubEntity>): LongArray

    @Query("SELECT * FROM `GithubEntity`")
    fun getRepositories(): LiveData<List<GithubEntity>>
}