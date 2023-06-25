package rubikans.rubik.doctor.ui.main.home.appointmentDetails

import com.etamn.util.SingleLiveEvent
import com.etamn.util.Status
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import rubikans.rubik.doctor.base.BaseViewModel
import rubikans.rubik.doctor.data.shared.DataManager


import javax.inject.Inject


@HiltViewModel
class AppointmentDetailsViewModel @Inject constructor(
    private val repository: AppointmentDetailsRepository,
    val dataManager: DataManager
) : BaseViewModel(repository) {




    val getAppointmentsDetailsState = SingleLiveEvent<Status>()
    fun getAppointmentsDetails(
        pBookingID: String,
    ) {
        performNetworkCall({
            repository.getAppointmentsDetails(pBookingID)
        }, getAppointmentsDetailsState)
    }


}