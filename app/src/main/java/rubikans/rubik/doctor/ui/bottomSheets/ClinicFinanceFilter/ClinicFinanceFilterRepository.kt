package rubikans.rubik.doctor.ui.bottomSheets.ClinicFinanceFilter
import android.content.Context
import com.google.gson.JsonObject
import retrofit2.Response
import rubikans.rubik.doctor.base.BaseRepository
import rubikans.rubik.doctor.base.BaseResponse
import rubikans.rubik.doctor.data.retrofit.ApiServices
import rubikans.rubik.doctor.data.shared.DataManager
import rubikans.rubik.doctor.model.ClinicFinanceExpenseTypesData
import rubikans.rubik.doctor.model.ClinicFinanceIncomeTypesData
import rubikans.rubik.doctor.model.ExpenseTypeItem
import rubikans.rubik.doctor.model.InsuranceCompanyData


import javax.inject.Inject

class ClinicFinanceFilterRepository @Inject constructor(
    private val context: Context,
    private val dataManager: DataManager,
    private val api: ApiServices
) : BaseRepository(dataManager, context) {




    suspend fun getClinicFinanceIncomeTypes(): Response<BaseResponse<ClinicFinanceIncomeTypesData>> =
        api.getClinicFinanceIncomeTypes(
        )


    suspend fun getClinicFinanceExpenseTypes(): Response<BaseResponse<ClinicFinanceExpenseTypesData>> =
        api.getClinicFinanceExpenseTypes(
        )


}