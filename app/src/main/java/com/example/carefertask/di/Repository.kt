package com.example.carefertask.di


import android.content.Context

import com.example.carefertask.data.retrofit.ApiServices
import com.example.carefertask.data.room.faveMatches.FaveTeamDao
import com.example.carefertask.data.shared.DataManager
import com.example.carefertask.ui.main.fragments.home.HomeRepository
import com.example.carefertask.ui.splash.SplashRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Repository {

    @Singleton
    @Provides
    fun provideMainRepository(
        @ApplicationContext appContext: Context,
        dataManager: DataManager,
        api: ApiServices,
        fav: FaveTeamDao,
    ): HomeRepository =
        HomeRepository(appContext, dataManager, api, fav)


    @Singleton
    @Provides
    fun provideSplashRepository(
        @ApplicationContext appContext: Context,
        dataManager: DataManager,
        api: ApiServices,
    ): SplashRepository =
        SplashRepository(appContext, dataManager, api)


}