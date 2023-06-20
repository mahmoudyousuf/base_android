package rubikans.rubik.doctor.ui.bottomSheets.signOut

import com.etamn.util.SingleLiveEvent
import com.etamn.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import rubikans.rubik.doctor.base.BaseViewModel


import javax.inject.Inject


@HiltViewModel
class SignOutViewModel @Inject constructor(
    private val repository: SignOutRepository
) : BaseViewModel(repository) {

    val logoutState = SingleLiveEvent<Status>()
    fun logout(
    ) {
        performNetworkCall({
            repository.logOut()
        }, logoutState)
    }


}


