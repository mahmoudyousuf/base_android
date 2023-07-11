package rubikans.rubik.doctor.ui.main.profile

import com.etamn.util.SingleLiveEvent
import com.etamn.util.Status
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import rubikans.rubik.doctor.base.BaseViewModel
import rubikans.rubik.doctor.data.shared.DataManager


import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository,
    val dataManager: DataManager
) : BaseViewModel(repository) {




    val getProfileState = SingleLiveEvent<Status>()
    fun getProfile(
    ) {
        performNetworkCall({
            repository.getProfile(
                pEntityBranchID = dataManager.clinic!!.entityBranchID.toString()
            )
        }, getProfileState)
    }


}