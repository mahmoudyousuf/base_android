package com.example.carefertask.data.retrofit

import com.example.carefertask.model.MatchesResponse
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*


interface ApiServices {


    @GET("v2/competitions/2021/matches")
    suspend fun getMatches(): Response<MatchesResponse>



}