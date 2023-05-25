package com.example.carefertask.ui.main.fragments.home.util

import com.example.carefertask.model.HeaderItem
import com.example.carefertask.model.ListItem
import com.example.carefertask.model.MatchItem
import com.example.carefertask.model.MatchesItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.math.absoluteValue

object UtilFun {


    private fun handelMatchesResponse(matches: ArrayList<MatchesItem>?) {
        val mapList = groupByDate(matches ?: ArrayList())
        val nearestDate = findClosestDate(mapList.keys.toList(), LocalDate.now())
        var todayPosition = 0

        /**
         * sort list by date
         */
        val sortedMap = mapList.toSortedMap()

        /**
         * convert hash map to on arraylist to show in recycler view
         */
        var recyclerViewListItem: ArrayList<ListItem> = ArrayList()
        for (date in sortedMap.keys) {


            val header = HeaderItem(date)
            recyclerViewListItem.add(header)
            if (date == nearestDate) {
                todayPosition = recyclerViewListItem.size
            }
            for (event in sortedMap[date]!!) {
                val item = MatchItem(event)
                recyclerViewListItem.add(item)
            }
        }

        //adapter.setMyData(recyclerViewListItem)
        /**
         * scroll top
         */
        // binding.list.scrollToPosition(todayPosition)


    }

    /**
     * converted fetched list to hash map list to group listview by date
     */
    fun groupByDate(list: ArrayList<MatchesItem>): Map<LocalDate, ArrayList<MatchesItem>> {
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE

        val matches: HashMap<LocalDate, ArrayList<MatchesItem>> = HashMap()
        for (item in list) {
            val date: LocalDate = LocalDate.parse(item.utcDate?.take(10), formatter)
            if (!matches.containsKey(date)) {
                matches[date] = ArrayList<MatchesItem>()
            }
            matches[date]!!.add(item)
        }
        return matches
    }

    /**
     * get current date from fetched list and if no matches today get next day
     */
    fun findClosestDate(dates: List<LocalDate>, targetDate: LocalDate): LocalDate? {
        return dates.minByOrNull { ChronoUnit.DAYS.between(it, targetDate).absoluteValue }
    }
}