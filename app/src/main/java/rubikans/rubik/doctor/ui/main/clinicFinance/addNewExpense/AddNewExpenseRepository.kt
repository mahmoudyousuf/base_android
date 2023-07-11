package rubikans.rubik.doctor.ui.main.clinicFinance.addNewExpense
import android.content.Context
import com.google.gson.JsonObject
import retrofit2.Response
import rubikans.rubik.doctor.base.BaseRepository
import rubikans.rubik.doctor.base.BaseResponse
import rubikans.rubik.doctor.data.retrofit.ApiServices
import rubikans.rubik.doctor.data.shared.DataManager
import rubikans.rubik.doctor.model.ClinicFinanceExpenseTypesData
import rubikans.rubik.doctor.model.InsuranceCompanyData


import javax.inject.Inject

class AddNewExpenseRepository @Inject constructor(
    private val context: Context,
    private val dataManager: DataManager,
    private val api: ApiServices
) : BaseRepository(dataManager, context) {



    suspend fun addNewExpense(props: JsonObject):  Response<BaseResponse<BaseResponse.EmptyData>> =
        api.addNewExpense(
            props
        )

    suspend fun getClinicFinanceExpenseTypes(): Response<BaseResponse<ClinicFinanceExpenseTypesData>> =
        api.getClinicFinanceExpenseTypes(
        )

}