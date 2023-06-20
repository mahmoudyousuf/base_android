package rubikans.rubik.doctor.ui.auth.forgetPassword

import com.etamn.util.SingleLiveEvent
import com.etamn.util.Status
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import rubikans.rubik.doctor.base.BaseViewModel
import rubikans.rubik.doctor.data.shared.DataManager


import javax.inject.Inject


@HiltViewModel
class ForgetPasswordViewModel @Inject constructor(
    private val repository: ForgetPasswordRepository,
    val dataManager: DataManager
) : BaseViewModel(repository) {




    val forgetPasswordState = SingleLiveEvent<Status>()
    fun forgetPassword(
        props: JsonObject
    ) {
        performNetworkCall({
            repository.forgetPassword(props)
        }, forgetPasswordState)
    }


}