package com.example.carefertask.model

import java.time.LocalDate

class HeaderItem(var date: LocalDate) : ListItem() {

    override fun getType(): Int {
        return TYPE_HEADER
    }
}