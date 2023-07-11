package rubikans.rubik.doctor.ui.bottomSheets.patientsFilter

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
class PatientsFilterViewModel @Inject constructor(
    private val repository: PatientsFilterRepository,
    val dataManager: DataManager
) : BaseViewModel(repository) {







    val getInsuranceCompaniesState = SingleLiveEvent<Status>()
    fun getInsuranceCompanies(
    ) {
        performNetworkCall({
            repository.getInsuranceCompanies(
            )
        }, getInsuranceCompaniesState)
    }



}