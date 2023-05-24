package com.example.carefertask.ui.splash

import android.content.Context
import com.example.carefertask.base.BaseRepository
import com.example.carefertask.data.retrofit.ApiServices
import com.example.carefertask.data.shared.DataManager
import com.example.carefertask.model.MatchesResponse
import retrofit2.Response
import javax.inject.Inject

class SplashRepository @Inject constructor(
    private val context: Context,
    private val dataManager: DataManager,
    private val api: ApiServices
) : BaseRepository(dataManager, context) {


    fun saveXApiKey(key : String){
        dataManager.saveXAPIKey(key)
    }


}