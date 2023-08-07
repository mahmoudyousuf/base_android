package rubikans.rubik.doctor.ui.main.notification
import android.content.Context
import retrofit2.Response
import rubikans.rubik.doctor.base.BaseRepository
import rubikans.rubik.doctor.base.BaseResponse
import rubikans.rubik.doctor.data.retrofit.ApiServices
import rubikans.rubik.doctor.data.shared.DataManager
import rubikans.rubik.doctor.model.NotificationsDataItem


import javax.inject.Inject

class NotificationsRepository @Inject constructor(
    private val context: Context,
    private val dataManager: DataManager,
    private val api: ApiServices
) : BaseRepository(dataManager, context) {



    suspend fun getNotifications(): Response<BaseResponse<ArrayList<NotificationsDataItem>>> =
        api.getNotifications()


    suspend fun readAllNotifications(): Response<BaseResponse<BaseResponse.EmptyData>> =
        api.readAllNotifications()


    suspend fun readSelectedNotifications(pNotificationID: String): Response<BaseResponse<BaseResponse.EmptyData>> =
        api.readSelectedNotifications(pNotificationID)


    suspend fun getNotificationCount(): Response<BaseResponse<Int>> =
        api.getNotificationCount()

}