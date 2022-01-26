package com.fraserbell.keepfit.di

import com.fraserbell.keepfit.ui.goals.GoalsRepository
import com.fraserbell.keepfit.ui.goals.GoalsRepositoryImpl
import com.fraserbell.keepfit.ui.steps.StepsRepository
import com.fraserbell.keepfit.ui.steps.StepsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun providesGoalsRepository(impl: GoalsRepositoryImpl): GoalsRepository

    @Binds
    abstract fun providesStepsRepository(impl: StepsRepositoryImpl): StepsRepository
}