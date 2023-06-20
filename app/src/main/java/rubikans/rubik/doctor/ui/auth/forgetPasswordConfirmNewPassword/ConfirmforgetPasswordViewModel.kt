package rubikans.rubik.doctor.ui.auth.forgetPasswordConfirmNewPassword

import com.etamn.util.SingleLiveEvent
import com.etamn.util.Status
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import rubikans.rubik.doctor.base.BaseViewModel
import rubikans.rubik.doctor.data.shared.DataManager


import javax.inject.Inject


@HiltViewModel
class ConfirmforgetPasswordViewModel @Inject constructor(
    private val repository: ConfirmForgetPasswordRepository,
    val dataManager: DataManager
) : BaseViewModel(repository) {




    val confirmForgetPasswordState = SingleLiveEvent<Status>()
    fun confirmForgetPassword(
        props: JsonObject
    ) {
        performNetworkCall({
            repository.confirmForgetPassword(props)
        }, confirmForgetPasswordState)
    }


}