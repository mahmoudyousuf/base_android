package rubikans.rubik.doctor.ui.bottomSheets.deleteExpense

import android.content.Context
import retrofit2.Response
import rubikans.rubik.doctor.base.BaseRepository
import rubikans.rubik.doctor.base.BaseResponse
import rubikans.rubik.doctor.data.retrofit.ApiServices
import rubikans.rubik.doctor.data.shared.DataManager


import javax.inject.Inject

class DeleteExpenseRepository @Inject constructor(
    private val context: Context,
    private val dataManager: DataManager,
    private val api: ApiServices
) : BaseRepository(dataManager, context) {

    suspend fun deleteClinicExpense(pId: String): Response<BaseResponse<BaseResponse.EmptyData>> =
        api.deleteClinicExpense(pId)


}