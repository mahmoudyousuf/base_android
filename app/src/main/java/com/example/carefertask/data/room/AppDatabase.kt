package com.example.carefertask.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.carefertask.data.room.faveMatches.FaveTeamDao
import com.example.carefertask.data.room.faveMatches.FaveTeamEntity


@Database(
    entities = [FaveTeamEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun faveTeamDao(): FaveTeamDao
}

