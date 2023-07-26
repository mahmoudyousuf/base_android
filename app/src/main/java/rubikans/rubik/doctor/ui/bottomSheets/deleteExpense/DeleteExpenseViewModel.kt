package rubikans.rubik.doctor.ui.bottomSheets.deleteExpense

import com.etamn.util.SingleLiveEvent
import com.etamn.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import rubikans.rubik.doctor.base.BaseViewModel


import javax.inject.Inject


@HiltViewModel
class DeleteExpenseViewModel @Inject constructor(
    private val repository: DeleteExpenseRepository
) : BaseViewModel(repository) {

    val deleteClinicExpenseState = SingleLiveEvent<Status>()
    fun deleteClinicExpense(pId: String) {
        performNetworkCall({
            repository.deleteClinicExpense(pId)
        }, deleteClinicExpenseState)
    }

}


