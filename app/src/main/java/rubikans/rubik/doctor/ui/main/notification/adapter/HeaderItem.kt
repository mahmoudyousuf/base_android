package rubikans.rubik.doctor.ui.main.notification.adapter

import rubikans.rubik.doctor.ui.main.notification.adapter.ListItem


class HeaderItem(var date: String) : ListItem() {

    override fun getType(): Int {
        return TYPE_HEADER
    }
}