package com.example.carefertask.ui.main.fragments.home

import android.content.Context
import com.example.carefertask.base.BaseRepository
import com.example.carefertask.data.retrofit.ApiServices
import com.example.carefertask.data.room.faveMatches.FaveTeamDao
import com.example.carefertask.data.room.faveMatches.FaveTeamEntity
import com.example.carefertask.data.shared.DataManager
import com.example.carefertask.model.MatchesResponse
import retrofit2.Response
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val context: Context,
    private val dataManager: DataManager,
    private val api: ApiServices,
    private val favDao: FaveTeamDao,
) : BaseRepository(dataManager, context) {


    suspend fun getMatches(): Response<MatchesResponse> =
        api.getMatches()

    fun addFaveMatch(int: Int) {
        favDao.insert(FaveTeamEntity(teamId = int))
    }

    fun getFaveMatch(): List<FaveTeamEntity?>? = favDao.all
}