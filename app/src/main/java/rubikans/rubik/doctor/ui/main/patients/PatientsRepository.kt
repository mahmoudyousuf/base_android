package rubikans.rubik.doctor.ui.main.patients

import android.content.Context
import com.google.gson.JsonObject
import retrofit2.Response
import rubikans.rubik.doctor.base.BaseRepository
import rubikans.rubik.doctor.base.BaseResponse
import rubikans.rubik.doctor.data.retrofit.ApiServices
import rubikans.rubik.doctor.data.shared.DataManager
import rubikans.rubik.doctor.model.AppointmentsData
import rubikans.rubik.doctor.model.CheckClinicSettingModelItem
import rubikans.rubik.doctor.model.PatientsListModelItem

import javax.inject.Inject

class PatientsRepository @Inject constructor(
    private val context: Context,
    private val dataManager: DataManager,
    private val api: ApiServices

) : BaseRepository(dataManager, context) {


    suspend fun getPatientList(
        pPhoneNumber: String,
        pDateFrom: String,
        pDateTo: String,
        pSearchText: String,
        pPageNumber: Int,
    ): Response<BaseResponse<ArrayList<PatientsListModelItem>>>  =
        api.getPatientList(
        pEntityBranchID = dataManager.clinic!!.entityBranchID.toString(),
            pPhoneNumber = pPhoneNumber ,
            pDateFrom = pDateFrom,
            pDateTo = pDateTo,
            pSearchText = pSearchText,
            pPageNumber = pPageNumber
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


}