package com.example.carefertask.di


import com.example.carefertask.data.room.AppDatabase
import com.example.carefertask.data.room.faveMatches.FaveTeamDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomDaoModule {

    @Singleton
    @Provides
    fun provideFaveTeamDao(database: AppDatabase): FaveTeamDao {
        return database.faveTeamDao()
    }

}