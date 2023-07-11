package rubikans.rubik.doctor.ui.main.addNewPatient
import android.content.Context
import com.google.gson.JsonObject
import retrofit2.Response
import rubikans.rubik.doctor.base.BaseRepository
import rubikans.rubik.doctor.base.BaseResponse
import rubikans.rubik.doctor.data.retrofit.ApiServices
import rubikans.rubik.doctor.data.shared.DataManager
import rubikans.rubik.doctor.model.InsuranceCompanyData


import javax.inject.Inject

class AddNewPatientRepository @Inject constructor(
    private val context: Context,
    private val dataManager: DataManager,
    private val api: ApiServices
) : BaseRepository(dataManager, context) {



    suspend fun addNewPatient(props: JsonObject):  Response<BaseResponse<BaseResponse.EmptyData>> =
        api.addNewPatient(
            props
        )

    suspend fun getInsuranceCompanies(): Response<BaseResponse<InsuranceCompanyData>> =
        api.getInsuranceCompanies(
        )


}