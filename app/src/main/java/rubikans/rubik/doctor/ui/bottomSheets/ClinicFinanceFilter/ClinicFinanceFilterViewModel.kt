package rubikans.rubik.doctor.ui.bottomSheets.ClinicFinanceFilter

import androidx.lifecycle.MutableLiveData
import com.etamn.util.SingleLiveEvent
import com.etamn.util.Status
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import rubikans.rubik.doctor.base.BaseViewModel
import rubikans.rubik.doctor.data.shared.DataManager
import rubikans.rubik.doctor.model.InsuranceCompanyItem
import rubikans.rubik.doctor.model.SelectPatientModelItem


import javax.inject.Inject


@HiltViewModel
class ClinicFinanceFilterViewModel @Inject constructor(
    private val repository: ClinicFinanceFilterRepository,
    val dataManager: DataManager
) : BaseViewModel(repository) {







    val getClinicFinanceIncomeTypesState = SingleLiveEvent<Status>()
    fun getClinicFinanceIncomeTypes(
    ) {
        performNetworkCall({
            repository.getClinicFinanceIncomeTypes(
            )
        }, getClinicFinanceIncomeTypesState)
    }



    val getClinicFinanceExpenseTypesState = SingleLiveEvent<Status>()
    fun getClinicFinanceExpenseTypes(
    ) {
        performNetworkCall({
            repository.getClinicFinanceExpenseTypes(
            )
        }, getClinicFinanceExpenseTypesState)
    }



}