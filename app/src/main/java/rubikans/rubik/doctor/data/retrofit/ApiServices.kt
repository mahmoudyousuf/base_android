package rubikans.rubik.doctor.data.retrofit

import com.google.gson.JsonObject
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
    @POST("api/Authentication/ForgetPassword")
    suspend fun forgetPassword(@Body props: JsonObject): Response<BaseResponse<BaseResponse.EmptyData>>


    @Headers("Accept: application/json")
    @POST("api/Authentication/ValidateForgetToken")
    suspend fun forgetPasswordOtpCheck(@Body props: JsonObject): Response<BaseResponse<String>>


    @Headers("Accept: application/json")
    @POST("api/Authentication/ChangePasswordAfterForget")
    suspend fun confirmForgetPassword(@Body props: JsonObject): Response<BaseResponse<BaseResponse.EmptyData>>


    @Headers("Accept: application/json")
    @POST("api/Account/Logout")
    suspend fun logOut(
    ): Response<BaseResponse<BaseResponse.EmptyData>>



    @Headers("Accept: application/json")
    @POST("api/Authentication/ChangePasswordWithoutForget")
    suspend fun changePassword(@Body props: JsonObject): Response<BaseResponse<BaseResponse.EmptyData>>


    @Headers("Accept: application/json")
    @GET("api/Branch/GetUserBranches")
    suspend fun getClinicBranches(
    ): Response<BaseResponse<ArrayList<ClinicBranchesDataItem>>>



    @Headers("Accept: application/json")
    @GET("api/Bookings/GetAppointment")
    suspend fun getAppointments(
        @Query("pPageNumber")   pPageNumber: Int,
        @Query("pBranchId")     pBranchId: String,
        @Query("pBookingID")    pBookingID: String,
        @Query("pPatientID")    pPatientID: String,
        @Query("pStatusID")     pStatusID: String,
        @Query("pDateFrom")     pDateFrom: String,
        @Query("pDateTo")       pDateTo: String,
        @Query("pSearchText")   pSearchText: String,
        @Query("pPageSize")     pPageSize: Int = 10,
        ): Response<BaseResponse<AppointmentsData>>



    @Headers("Accept: application/json")
    @GET("api/Bookings/GetAppointment")
    suspend fun getAppointmentsDetails(
        @Query("pBranchId")     pBranchId: String,
        @Query("pBookingID")    pBookingID: String,
    ): Response<BaseResponse<AppointmentsData>>




    @Headers("Accept: application/json")
    @GET("api/Bookings/ChangeBookingStatus")
    suspend fun changeAppointmentStatus(
        @Query("pBookingID")     pBookingID: String,
        @Query("pStatus")    pStatus: String,
    ): Response<BaseResponse<BaseResponse.EmptyData>>




    @Headers("Accept: application/json")
    @GET("api/Bookings/GetDoctorAvailableAppointment")
    suspend fun getAppointmentsTimes(
        @Query("pEntityBranchID")           pEntityBranchID: String,
        @Query("pConsultationServicesID")   pConsultationServicesID: String,
        @Query("pDate")                     pDate: String ,
    ): Response<BaseResponse<ArrayList<AppointmentsTimesModelItem>>>




    @Headers("Accept: application/json")
    @GET("api/Branch/GetBranchConsultationServices")
    suspend fun getConsultationServices(
        @Query("pBranchId")           pBranchId: String,

    ): Response<BaseResponse<ArrayList<ConsultationServicesModelItem>>>



    @Headers("Accept: application/json")
    @GET("api/profiles/GetCollectionData")
    suspend fun getPaymentMethods(
        @Query("pOption") pOption: String = "6"
    ): Response<BaseResponse<PaymentMethodsModel>>



    @Headers("Accept: application/json")
    @GET("api/Profiles/GetPatientByBranchID")
    suspend fun getSelectPatientList(
        @Query("pBranchID")           pBranchID: String,

        ): Response<BaseResponse<ArrayList<SelectPatientModelItem>>>



    @Headers("Accept: application/json")
    @POST("api/Bookings/AddAppointment")
    suspend fun addAppointment(
        @Body props: JsonObject
    ): Response<BaseResponse<BaseResponse.EmptyData>>





    @Headers("Accept: application/json")
    @GET("api/Branch/CheckClinicSetting")
    suspend fun checkClinicSetting(
        @Query("pEntityBranchID")           pEntityBranchID: String,

        ): Response<BaseResponse<ArrayList<CheckClinicSettingModelItem>>>


}