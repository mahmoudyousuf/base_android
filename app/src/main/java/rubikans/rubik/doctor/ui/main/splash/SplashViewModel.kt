package rubikans.rubik.doctor.ui.main.splash

import com.etamn.util.SingleLiveEvent
import com.etamn.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import rubikans.rubik.doctor.base.BaseViewModel
import rubikans.rubik.doctor.data.shared.DataManager
import rubikans.rubik.doctor.ui.main.splash.SplashRepository


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