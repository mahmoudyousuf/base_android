package rubikans.rubik.doctor.ui.main.basicInfo.editBasicInfo

import android.content.Context
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import rubikans.rubik.doctor.base.BaseRepository
import rubikans.rubik.doctor.base.BaseResponse
import rubikans.rubik.doctor.data.retrofit.ApiServices
import rubikans.rubik.doctor.data.shared.DataManager

import javax.inject.Inject


class EditBasicInfoRepository @Inject constructor(
    private val context: Context,
    private val dataManager: DataManager,
    private val api: ApiServices
) : BaseRepository(dataManager, context) {






    suspend fun updateUserInfo(
        profileImage: MultipartBody.Part?,
        data: MutableMap<String, RequestBody>
    ): Response<BaseResponse<BaseResponse.EmptyData>> =
        api.updateUserInfo(
          profileImage,
            data
        )



}