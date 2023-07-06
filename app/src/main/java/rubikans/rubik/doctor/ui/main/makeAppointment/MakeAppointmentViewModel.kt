package rubikans.rubik.doctor.ui.main.makeAppointment

import androidx.lifecycle.MutableLiveData
import com.etamn.util.SingleLiveEvent
import com.etamn.util.Status
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import rubikans.rubik.doctor.base.BaseViewModel
import rubikans.rubik.doctor.data.shared.DataManager
import rubikans.rubik.doctor.model.SelectPatientModelItem


import javax.inject.Inject


@HiltViewModel
class MakeAppointmentViewModel @Inject constructor(
    private val repository: MakeAppointmentRepository,
    val dataManager: DataManager
) : BaseViewModel(repository) {

    val selectedPatient = MutableLiveData<SelectPatientModelItem>()



    init {
        selectedPatient.value = SelectPatientModelItem()
    }


    fun setSelectedPatient(patient: SelectPatientModelItem) {

        selectedPatient.value = patient
        selectedPatient.value = selectedPatient.value
    }


    val getAppointmentsTimesState = SingleLiveEvent<Status>()
    fun getAppointmentsTimes(
        pEntityBranchID: String,
        pConsultationServicesID: String,
        pDate: String,
    ) {
        performNetworkCall({
            repository.getAppointmentsTimes(
                pEntityBranchID,
                pConsultationServicesID,
                pDate
            )
        }, getAppointmentsTimesState)
    }


    val getConsultationServicesState = SingleLiveEvent<Status>()
    fun getConsultationServices(
        pBranchId: String,
    ) {
        performNetworkCall({
            repository.getConsultationServices(
                pBranchId
            )
        }, getConsultationServicesState)
    }


    val getPaymentMethodsState = SingleLiveEvent<Status>()
    fun getPaymentMethods(
    ) {
        performNetworkCall({
            repository.getPaymentMethods(
            )
        }, getPaymentMethodsState)
    }




    val getSelectPatientListState = SingleLiveEvent<Status>()
    fun getSelectPatientList(
        pBranchID: String,
    ) {
        performNetworkCall({
            repository.getSelectPatientList(
                pBranchID
            )
        }, getSelectPatientListState)
    }



    val addAppointmentState = SingleLiveEvent<Status>()
    fun addAppointment(
        props: JsonObject
    ) {
        performNetworkCall({
            repository.addAppointment(
                props
            )
        }, addAppointmentState)
    }


}