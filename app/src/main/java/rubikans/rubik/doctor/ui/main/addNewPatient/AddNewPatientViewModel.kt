package rubikans.rubik.doctor.ui.main.addNewPatient

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
class AddNewPatientViewModel @Inject constructor(
    private val repository: AddNewPatientRepository,
    val dataManager: DataManager
) : BaseViewModel(repository) {


    val insuranceCompanyModel = MutableLiveData<InsuranceCompanyItem>()

    init {
        insuranceCompanyModel.value = InsuranceCompanyItem()
    }


    fun setSelectedInsuranceCompany(patient: InsuranceCompanyItem) {

        insuranceCompanyModel.value = patient
        insuranceCompanyModel.value = insuranceCompanyModel.value
    }

    val addNewPatientState = SingleLiveEvent<Status>()
    fun addNewPatient(
        props: JsonObject
    ) {
        performNetworkCall({
            repository.addNewPatient(props)
        }, addNewPatientState)
    }


    val getInsuranceCompaniesState = SingleLiveEvent<Status>()
    fun getInsuranceCompanies(
    ) {
        performNetworkCall({
            repository.getInsuranceCompanies(
            )
        }, getInsuranceCompaniesState)
    }



}