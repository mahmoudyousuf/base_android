package rubikans.rubik.doctor.ui.main.makeAppointment

import android.content.Context
import com.google.gson.JsonObject
import retrofit2.Response
import rubikans.rubik.doctor.base.BaseRepository
import rubikans.rubik.doctor.base.BaseResponse
import rubikans.rubik.doctor.data.retrofit.ApiServices
import rubikans.rubik.doctor.data.shared.DataManager
import rubikans.rubik.doctor.model.AppointmentsTimesModelItem
import rubikans.rubik.doctor.model.ConsultationServicesModelItem
import rubikans.rubik.doctor.model.PaymentMethodsModel
import rubikans.rubik.doctor.model.SelectPatientModelItem


import javax.inject.Inject

class MakeAppointmentRepository @Inject constructor(
    private val context: Context,
    private val dataManager: DataManager,
    private val api: ApiServices
) : BaseRepository(dataManager, context) {


    suspend fun getAppointmentsTimes(
        pEntityBranchID: String,
        pConsultationServicesID: String,
        pDate: String,
    ): Response<BaseResponse<ArrayList<AppointmentsTimesModelItem>>> =
        api.getAppointmentsTimes(
            pEntityBranchID,
            pConsultationServicesID,
            pDate
        )


    suspend fun getConsultationServices(
        pBranchId: String,
    ): Response<BaseResponse<ArrayList<ConsultationServicesModelItem>>> =
        api.getConsultationServices(
            pBranchId,

            )


    suspend fun getPaymentMethods(

    ): Response<BaseResponse<PaymentMethodsModel>> =
        api.getPaymentMethods(

        )



    suspend fun getSelectPatientList(
        pBranchID: String,
    ): Response<BaseResponse<ArrayList<SelectPatientModelItem>>> =
        api.getSelectPatientList(
            pBranchID,

            )




    suspend fun addAppointment(
        props: JsonObject
    ): Response<BaseResponse<BaseResponse.EmptyData>> =
        api.addAppointment(
            props,

            )

}