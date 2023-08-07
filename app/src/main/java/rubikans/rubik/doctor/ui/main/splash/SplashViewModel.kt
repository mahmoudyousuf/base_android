package rubikans.rubikcare.patient.ui.splash

import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import rubikans.rubikcare.patient.base.BaseViewModel
import rubikans.rubikcare.patient.data.shared.DataManager
import rubikans.rubikcare.patient.util.SingleLiveEvent
import rubikans.rubikcare.patient.util.Status

import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: SplashRepository,
    val dataManager: DataManager
) : BaseViewModel(repository) {




    val refreshFCMTokenState = SingleLiveEvent<Status>()
    fun refreshFCMToken(
        pToken: String
    ) {
        performNetworkCall({
            repository.refreshFCMToken(pToken)
        }, refreshFCMTokenState)
    }


}