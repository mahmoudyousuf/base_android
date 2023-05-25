package com.example.carefertask.model

import com.google.gson.annotations.SerializedName

data class MatchesResponse(

    @field:SerializedName("count")
    val count: Int? = null,

    @field:SerializedName("competition")
    val competition: Competition? = null,

    @field:SerializedName("filters")
    val filters: Filters? = null,

    @field:SerializedName("matches")
    val matches: ArrayList<MatchesItem>? = null
)

data class Competition(

    @field:SerializedName("area")
    val area: Area? = null,

    @field:SerializedName("lastUpdated")
    val lastUpdated: String? = null,

    @field:SerializedName("code")
    val code: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("plan")
    val plan: String? = null
)

data class ExtraTime(

    @field:SerializedName("awayTeam")
    val awayTeam: Any? = null,

    @field:SerializedName("homeTeam")
    val homeTeam: Any? = null
)

data class Odds(

    @field:SerializedName("msg")
    val msg: String? = null
)

data class RefereesItem(

    @field:SerializedName("role")
    val role: String? = null,

    @field:SerializedName("nationality")
    val nationality: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null
)

data class MatchesItem(

    @field:SerializedName("matchday")
    val matchday: Int? = null,

    @field:SerializedName("awayTeam")
    val awayTeam: AwayTeam? = null,

    @field:SerializedName("utcDate")
    val utcDate: String? = null,

    @field:SerializedName("lastUpdated")
    val lastUpdated: String? = null,

    @field:SerializedName("score")
    val score: Score? = null,

    @field:SerializedName("stage")
    val stage: String? = null,

    @field:SerializedName("odds")
    val odds: Odds? = null,

    @field:SerializedName("season")
    val season: Season? = null,

    @field:SerializedName("homeTeam")
    val homeTeam: HomeTeam? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("referees")
    val referees: List<RefereesItem?>? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("group")
    val group: Any? = null,


)

data class Area(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null
)

data class AwayTeam(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null

    ,

    var fave: Boolean = false
)

data class Season(

    @field:SerializedName("currentMatchday")
    val currentMatchday: Int? = null,

    @field:SerializedName("endDate")
    val endDate: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("startDate")
    val startDate: String? = null
)

data class Penalties(

    @field:SerializedName("awayTeam")
    val awayTeam: Any? = null,

    @field:SerializedName("homeTeam")
    val homeTeam: Any? = null
)

data class Score(

    @field:SerializedName("duration")
    val duration: String? = null,

    @field:SerializedName("winner")
    val winner: String? = null,

    @field:SerializedName("penalties")
    val penalties: Penalties? = null,

    @field:SerializedName("halfTime")
    val halfTime: HalfTime? = null,

    @field:SerializedName("fullTime")
    val fullTime: FullTime? = null,

    @field:SerializedName("extraTime")
    val extraTime: ExtraTime? = null
)

data class FullTime(

    @field:SerializedName("awayTeam")
    val awayTeam: Int? = null,

    @field:SerializedName("homeTeam")
    val homeTeam: Int? = null
)

data class HomeTeam(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    var fave: Boolean = false
)

data class Filters(
    val any: Any? = null
)

data class HalfTime(

    @field:SerializedName("awayTeam")
    val awayTeam: Int? = null,

    @field:SerializedName("homeTeam")
    val homeTeam: Int? = null
)
