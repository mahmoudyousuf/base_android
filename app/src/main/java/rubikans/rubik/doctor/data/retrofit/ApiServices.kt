package rubikans.rubik.doctor.data.retrofit

import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*
import rubikans.rubik.doctor.base.BaseResponse
import rubikans.rubik.doctor.model.*


interface ApiServices {


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("token")
    suspend fun loginToken(
        @Field("Username") username: String,
        @Field("Password") password: String,
        @Field("grant_type") grant_type: String,
        @Field("DeviceToken") DeviceToken: String,
        @Field("DeviceID") DeviceID: String = "android",
        @Field("DeviceType") DeviceType: String = "2",
        @Field("UserType") UserType: String = "1",
    ): Response<BaseResponse<BaseResponse.EmptyData>>





    @Headers("Accept: application/json")
    @POST("api/Account/Logout")
    suspend fun logOut(
    ): Response<BaseResponse<BaseResponse.EmptyData>>








}