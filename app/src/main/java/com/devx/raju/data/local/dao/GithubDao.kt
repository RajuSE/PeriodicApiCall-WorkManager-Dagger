package com.devx.raju.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devx.raju.data.local.entity.GithubEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GithubDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepositories(githubEntities: List<GithubEntity>): LongArray

    @Query("SELECT * FROM `GithubEntity`")
    fun getRepositories(): Flow<List<GithubEntity>>
}