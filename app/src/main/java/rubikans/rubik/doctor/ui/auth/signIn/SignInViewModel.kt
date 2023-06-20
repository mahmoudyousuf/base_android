package rubikans.rubik.doctor.ui.auth.signIn

import com.etamn.util.SingleLiveEvent
import com.etamn.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import rubikans.rubik.doctor.base.BaseViewModel
import rubikans.rubik.doctor.data.shared.DataManager


import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: SignInRepository,
    val dataManager: DataManager
) : BaseViewModel(repository) {

    val loginTokenState = SingleLiveEvent<Status>()
    fun getLoginToken(
        userName: String,
        password: String
    ) {
        performNetworkCall({
            repository.loginToken(userName, password)
        }, loginTokenState)
    }





}