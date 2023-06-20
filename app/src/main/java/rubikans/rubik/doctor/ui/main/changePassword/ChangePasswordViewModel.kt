package rubikans.rubik.doctor.ui.main.changePassword

import com.etamn.util.SingleLiveEvent
import com.etamn.util.Status
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import rubikans.rubik.doctor.base.BaseViewModel
import rubikans.rubik.doctor.data.shared.DataManager


import javax.inject.Inject


@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val repository: ChangePasswordRepository,
    val dataManager: DataManager
) : BaseViewModel(repository) {




    val changePasswordState = SingleLiveEvent<Status>()
    fun changePassword(
        props: JsonObject
    ) {
        performNetworkCall({
            repository.changePassword(props)
        }, changePasswordState)
    }


}