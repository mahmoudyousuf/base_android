package rubikans.rubik.doctor.ui.auth.forgetPassword
import android.content.Context
import com.google.gson.JsonObject
import retrofit2.Response
import rubikans.rubik.doctor.base.BaseRepository
import rubikans.rubik.doctor.base.BaseResponse
import rubikans.rubik.doctor.data.retrofit.ApiServices
import rubikans.rubik.doctor.data.shared.DataManager


import javax.inject.Inject

class ForgetPasswordRepository @Inject constructor(
    private val context: Context,
    private val dataManager: DataManager,
    private val api: ApiServices
) : BaseRepository(dataManager, context) {



    suspend fun forgetPassword(props: JsonObject):  Response<BaseResponse<BaseResponse.EmptyData>> =
        api.forgetPassword(
            props
        )


}