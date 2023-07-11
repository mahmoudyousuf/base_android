package rubikans.rubik.doctor.ui.main.clinicFinance.addNewExpense

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
class AddNewExpenseViewModel @Inject constructor(
    private val repository: AddNewExpenseRepository,
    val dataManager: DataManager
) : BaseViewModel(repository) {




    val addNewExpenseState = SingleLiveEvent<Status>()
    fun addNewExpense(
        props: JsonObject
    ) {
        performNetworkCall({
            repository.addNewExpense(props)
        }, addNewExpenseState)
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