package com.example.carefertask.model

class MatchItem(var matchItem: MatchesItem) : ListItem() {

    override fun getType(): Int {
        return TYPE_PLAN
    }

}