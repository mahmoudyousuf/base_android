package rubikans.rubik.doctor.ui.main.home.appointmentDetails
import android.content.Context
import com.google.gson.JsonObject
import retrofit2.Response
import rubikans.rubik.doctor.base.BaseRepository
import rubikans.rubik.doctor.base.BaseResponse
import rubikans.rubik.doctor.data.retrofit.ApiServices
import rubikans.rubik.doctor.data.shared.DataManager
import rubikans.rubik.doctor.model.AppointmentsData


import javax.inject.Inject

class AppointmentDetailsRepository @Inject constructor(
    private val context: Context,
    private val dataManager: DataManager,
    private val api: ApiServices
) : BaseRepository(dataManager, context) {


    suspend fun getAppointmentsDetails(

        pBookingID: String,

    ): Response<BaseResponse<AppointmentsData>> =
        api.getAppointmentsDetails(
            pBranchId = dataManager.clinic!!.entityBranchID.toString(),
            pBookingID = pBookingID,

        )

}