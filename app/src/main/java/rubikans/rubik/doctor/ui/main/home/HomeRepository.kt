package rubikans.rubik.doctor.ui.main.home

import android.content.Context
import com.google.gson.JsonObject
import retrofit2.Response
import rubikans.rubik.doctor.base.BaseRepository
import rubikans.rubik.doctor.base.BaseResponse
import rubikans.rubik.doctor.data.retrofit.ApiServices
import rubikans.rubik.doctor.data.shared.DataManager
import rubikans.rubik.doctor.model.AppointmentsData
import rubikans.rubik.doctor.model.CheckClinicSettingModelItem
import rubikans.rubik.doctor.model.ProfileData

import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val context: Context,
    private val dataManager: DataManager,
    private val api: ApiServices

) : BaseRepository(dataManager, context) {


    suspend fun getAppointments(
        pPageNumber: Int,
        pBranchId: String,
        pBookingID: String,
        pPatientID: String,
        pStatusID: String,
        pDateFrom: String,
        pDateTo: String,
        pSearchText: String,
    ): Response<BaseResponse<AppointmentsData>> =
        api.getAppointments(
            pPageNumber = pPageNumber,
            pBranchId = dataManager.clinic!!.entityBranchID.toString(),
            pBookingID = pBookingID,
            pPatientID = pPatientID,
            pStatusID = pStatusID,
            pDateFrom = pDateFrom,
            pDateTo = pDateTo,
            pSearchText = pSearchText,
        )


    suspend fun changeAppointmentStatus(
        pBookingID: String,
        pStatus: String,
    ): Response<BaseResponse<BaseResponse.EmptyData>> =
        api.changeAppointmentStatus(
            pBookingID = pBookingID,
            pStatus = pStatus,
        )






    suspend fun checkClinicSetting(
        pEntityBranchID: String,
    ): Response<BaseResponse<ArrayList<CheckClinicSettingModelItem>>> =
        api.checkClinicSetting(
            pEntityBranchID
        )




    suspend fun getProfile(pEntityBranchID: String):  Response<BaseResponse<ProfileData>> =
        api.getProfile(
            pEntityBranchID
        )




    suspend fun getNotificationCount(): Response<BaseResponse<Int>> =
        api.getNotificationCount()


    suspend fun readSelectedNotifications(pNotificationID: String): Response<BaseResponse<BaseResponse.EmptyData>> =
        api.readSelectedNotifications(pNotificationID)




}