package rubikans.rubik.doctor.ui.main.clinicBraches

import com.etamn.util.SingleLiveEvent
import com.etamn.util.Status
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import rubikans.rubik.doctor.base.BaseViewModel
import rubikans.rubik.doctor.data.shared.DataManager


import javax.inject.Inject


@HiltViewModel
class ClinicBranchesViewModel @Inject constructor(
    private val repository: ClinicBranchesRepository,
    val dataManager: DataManager
) : BaseViewModel(repository) {




    val getClinicBranchesState = SingleLiveEvent<Status>()
    fun getClinicBranches(
    ) {
        performNetworkCall({
            repository.getClinicBranches()
        }, getClinicBranchesState)
    }


}