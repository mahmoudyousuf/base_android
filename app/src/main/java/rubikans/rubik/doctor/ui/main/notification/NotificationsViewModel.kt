package rubikans.rubik.doctor.ui.main.notification

import com.etamn.util.SingleLiveEvent
import com.etamn.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import rubikans.rubik.doctor.base.BaseViewModel
import rubikans.rubik.doctor.data.shared.DataManager
import rubikans.rubik.doctor.ui.main.notification.NotificationsRepository


import javax.inject.Inject


@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val repository: NotificationsRepository,
    val dataManager: DataManager,
) : BaseViewModel(repository) {



    val getNotificationsState = SingleLiveEvent<Status>()
    fun getNotifications() {
        performNetworkCall({
            repository.getNotifications()
        }, getNotificationsState)
    }

    val readAllNotificationsState = SingleLiveEvent<Status>()
    fun readAllNotifications() {
        performNetworkCall({
            repository.readAllNotifications()
        }, readAllNotificationsState)
    }


   val readSelectedNotificationsState = SingleLiveEvent<Status>()
    fun readSelectedNotifications(pNotificationID: String) {
        performNetworkCall({
            repository.readSelectedNotifications(pNotificationID)
        }, readSelectedNotificationsState)
    }



    val getNotificationCountState = SingleLiveEvent<Status>()
    fun getNotificationCount(
    ) {
        performNetworkCall({
            repository.getNotificationCount()
        }, getNotificationCountState)
    }



}