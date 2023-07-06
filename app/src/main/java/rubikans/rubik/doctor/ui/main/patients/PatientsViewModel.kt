package rubikans.rubik.doctor.ui.main.patients

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow

import androidx.paging.cachedIn
import com.etamn.util.SingleLiveEvent
import com.etamn.util.Status
import com.google.gson.JsonObject
import rubikans.rubik.doctor.base.BaseViewModel
import rubikans.rubik.doctor.data.shared.DataManager
import rubikans.rubik.doctor.model.AppointmentItem

import javax.inject.Inject

@HiltViewModel
class PatientsViewModel @Inject constructor(
    private val repository: PatientsRepository,
    val dataManager: DataManager

) : BaseViewModel(repository) {


    val pBranchId = SingleLiveEvent<String>()
    val pBookingID = SingleLiveEvent<String>()
    val pPatientID = SingleLiveEvent<String>()
    val pStatusID = SingleLiveEvent<String>()
    val pDateFrom = SingleLiveEvent<String>()
    val pDateTo = SingleLiveEvent<String>()
    val pSearchText = SingleLiveEvent<String>()

    var appointmentsList = getAppointments()
    fun getAppointments(): Flow<PagingData<AppointmentItem>> {
        return Pager(config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { PatientsDataSource(this, repository) }).flow.cachedIn(
            viewModelScope
        )
    }


    val changeAppointmentStatusState = SingleLiveEvent<Status>()
    fun changeAppointmentStatus(
        pBookingID: String,
        pStatus: String,
    ) {
        performNetworkCall({
            repository.changeAppointmentStatus(
                pBookingID = pBookingID,
                pStatus = pStatus,
            )
        }, changeAppointmentStatusState)
    }


    val checkClinicSettingState = SingleLiveEvent<Status>()
    fun checkClinicSetting(
        pEntityBranchID: String,
    ) {
        performNetworkCall({
            repository.checkClinicSetting(

                pEntityBranchID
            )
        }, checkClinicSettingState)
    }


}

