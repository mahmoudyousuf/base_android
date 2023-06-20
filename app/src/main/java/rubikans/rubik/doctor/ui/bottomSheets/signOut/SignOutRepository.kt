package rubikans.rubik.doctor.ui.bottomSheets.signOut

import android.content.Context
import retrofit2.Response
import rubikans.rubik.doctor.base.BaseRepository
import rubikans.rubik.doctor.base.BaseResponse
import rubikans.rubik.doctor.data.retrofit.ApiServices
import rubikans.rubik.doctor.data.shared.DataManager


import javax.inject.Inject

class SignOutRepository @Inject constructor(
    private val context: Context,
    private val dataManager: DataManager,
    private val api: ApiServices
) : BaseRepository(dataManager, context) {

    suspend fun logOut(): Response<BaseResponse<BaseResponse.EmptyData>> =
        api.logOut()

}