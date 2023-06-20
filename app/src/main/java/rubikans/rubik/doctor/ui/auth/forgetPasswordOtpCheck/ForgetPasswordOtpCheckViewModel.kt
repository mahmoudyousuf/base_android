package rubikans.rubik.doctor.ui.auth.forgetPasswordOtpCheck

import com.etamn.util.SingleLiveEvent
import com.etamn.util.Status
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import rubikans.rubik.doctor.base.BaseViewModel
import rubikans.rubik.doctor.data.shared.DataManager


import javax.inject.Inject


@HiltViewModel
class ForgetPasswordOtpCheckViewModel @Inject constructor(
    private val repository: ForgetPasswordOtpCheckRepository,
    val dataManager: DataManager
) : BaseViewModel(repository) {




    val forgetPasswordState = SingleLiveEvent<Status>()
    fun forgetPasswordOtpCheck(
        props: JsonObject
    ) {
        performNetworkCall({
            repository.forgetPasswordOtpCheck(props)
        }, forgetPasswordState)
    }


    val resendCodeState = SingleLiveEvent<Status>()
    fun resendCode(
        props: JsonObject
    ) {
        performNetworkCall({
            repository.resendCode(props)
        }, resendCodeState)
    }


}