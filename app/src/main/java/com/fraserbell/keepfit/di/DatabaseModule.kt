package com.fraserbell.keepfit.di

import android.content.Context
import androidx.room.Room
import com.fraserbell.keepfit.data.AppDatabase
import com.fraserbell.keepfit.data.dao.GoalDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideGoalDao(appDatabase: AppDatabase): GoalDao {
        return appDatabase.goalDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "KeepFit"
        ).build()
    }
}