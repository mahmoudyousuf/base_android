package rubikans.rubik.doctor.ui.main.notification.adapter

 import rubikans.rubik.doctor.model.NotificationTestModel

class NotificationItem(var notificationItem: NotificationTestModel) : ListItem() {

     override fun getType(): Int {
        return TYPE_NOTIFICATION
    }
}