package rubikans.rubikcare.patient.ui.splash
import android.content.Context
import com.google.gson.JsonObject
import retrofit2.Response
import rubikans.rubikcare.patient.base.BaseRepository
import rubikans.rubikcare.patient.base.BaseResponse
import rubikans.rubikcare.patient.data.shared.DataManager
import rubikans.rubikcare.patient.data.retrofit.ApiServices

import javax.inject.Inject

class SplashRepository @Inject constructor(
    private val context: Context,
    private val dataManager: DataManager,
    private val api: ApiServices
) : BaseRepository(dataManager, context) {



    suspend fun refreshFCMToken(pToken: String):  Response<BaseResponse<BaseResponse.EmptyData>> =
        api.refreshFCMToken(
            pToken
        )


}