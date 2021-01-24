package com.devx.raju.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.devx.raju.data.local.converter.TimestampConverter;
import com.devx.raju.data.local.dao.GithubDao;
import com.devx.raju.data.local.entity.GithubEntity;


@Database(entities = {GithubEntity.class}, version = 1,  exportSchema = false)
@TypeConverters({TimestampConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract GithubDao githubDao();
}
