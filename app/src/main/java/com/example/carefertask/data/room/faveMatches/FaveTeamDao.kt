package com.example.carefertask.data.room.faveMatches

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface FaveTeamDao {

    @get:Query("SELECT * FROM FaveTeamEntity")
    val all: List<FaveTeamEntity?>?


    @Insert
    fun insert(entity: FaveTeamEntity?)

    @Delete
    fun delete(entity: FaveTeamEntity?)

    @Update
    fun update(entity: FaveTeamEntity?)
}