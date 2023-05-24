package com.example.carefertask.model

abstract class ListItem {


    abstract fun getType(): Int

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_PLAN = 1
    }
}