package rubikans.rubik.doctor.ui.main.clinicFinance

import android.content.Context
import com.google.gson.JsonObject
import retrofit2.Response
import rubikans.rubik.doctor.base.BaseRepository
import rubikans.rubik.doctor.base.BaseResponse
import rubikans.rubik.doctor.data.retrofit.ApiServices
import rubikans.rubik.doctor.data.shared.DataManager
import rubikans.rubik.doctor.model.*

import javax.inject.Inject

class ClinicFinanceRepository @Inject constructor(
    private val context: Context,
    private val dataManager: DataManager,
    private val api: ApiServices

) : BaseRepository(dataManager, context) {


    suspend fun getClinicIncomeList(
        pIncomeTypeID: String,
        pDateFrom: String,
        pDateTo: String,
        pSearchText: String,
        pPageNumber: Int,
    ): Response<BaseResponse<ArrayList<ClinicIncomeDataItem>>>  =
        api.getClinicIncomeList(
        pEntityBranchID = dataManager.clinic!!.entityBranchID.toString(),
            pIncomeTypeID = pIncomeTypeID ,
            pDateFrom = pDateFrom,
            pDateTo = pDateTo,
            pSearchText = pSearchText ,
            pPageNumber = pPageNumber,
        )




    suspend fun getClinicExpenseList(
        pExpenseTypeID: String,
        pDateFrom: String,
        pDateTo: String,
        pSearchText: String,
        pPageNumber: Int,
    ): Response<BaseResponse<ArrayList<ClinicExpenseDataItem>>>  =
        api.getClinicExpenseList(
            pEntityBranchID = dataManager.clinic!!.entityBranchID.toString(),
            pExpenseTypeID = pExpenseTypeID ,
            pDateFrom = pDateFrom,
            pDateTo = pDateTo,
            pSearchText = pSearchText ,
            pPageNumber = pPageNumber,
        )









}