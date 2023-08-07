package rubikans.rubik.doctor.ui.main.notification.adapter

abstract class ListItem {


    abstract fun getType(): Int

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_NOTIFICATION = 1
    }
}