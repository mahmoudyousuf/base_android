package rubikans.rubik.doctor.ui.auth.forgetPasswordOtpCheck
import android.content.Context
import com.google.gson.JsonObject
import retrofit2.Response
import rubikans.rubik.doctor.base.BaseRepository
import rubikans.rubik.doctor.base.BaseResponse
import rubikans.rubik.doctor.data.retrofit.ApiServices
import rubikans.rubik.doctor.data.shared.DataManager


import javax.inject.Inject

class ForgetPasswordOtpCheckRepository @Inject constructor(
    private val context: Context,
    private val dataManager: DataManager,
    private val api: ApiServices
) : BaseRepository(dataManager, context) {



    suspend fun forgetPasswordOtpCheck(props: JsonObject):  Response<BaseResponse<String>> =
        api.forgetPasswordOtpCheck(
            props
        )


    suspend fun resendCode(props: JsonObject):  Response<BaseResponse<BaseResponse.EmptyData>> =
        api.forgetPassword(
            props
        )

}