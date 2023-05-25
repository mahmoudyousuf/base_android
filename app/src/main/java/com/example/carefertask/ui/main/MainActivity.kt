package com.example.carefertask.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.etamn.util.Status
import com.example.carefertask.R
import com.example.carefertask.base.BaseActivity
import com.example.carefertask.databinding.ActivityMainBinding
import com.example.carefertask.model.HeaderItem
import com.example.carefertask.model.ListItem
import com.example.carefertask.model.MatchItem
import com.example.carefertask.model.MatchesItem
import com.example.carefertask.model.MatchesResponse
import com.example.carefertask.util.extensions.observe
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.math.absoluteValue


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    val viewModel: MainViewModel by viewModels()
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: MainGroupingAdapter
    private val matches: HashMap<LocalDate, ArrayList<MatchesItem>> = HashMap()
    val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = viewDataBinding!!

        setupViewPager(binding.viewPager);
        binding.tabLayout.setupWithViewPager(binding.viewPager);


        adapter = MainGroupingAdapter(ArrayList()) {
            viewModel.addFaveMatch(it)
        }

        binding.list.adapter = adapter

        viewModel.getMatches()
        viewModel.getFaveMatch()

        setObservers()
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = Adapter(getChildFragmentManager())
        adapter.addFragment(ChildFragment1(), "Tab 1")
        adapter.addFragment(ChildFragment2(), "Tab 2")
        viewPager.adapter = adapter
    }


    private fun setObservers() {


        observe(viewModel.getMatchesState)
        {
            when (it) {
                is Status.Loading -> {
                    showDialogLoading()
                }

                is Status.Success<*> -> {
                    hideDialogLoading()
                    val response = it.data as MatchesResponse
                    handelMatchesResponse(response.matches)

                }

                is Status.Error -> {
                    hideDialogLoading()
                    showWarningSnackbar(resources.getString(R.string.some_thing_went_wrong_error_msg))

                }


            }
        }

        observe(viewModel.faveList)
        {
            showSuccessSnackbar(it.size.toString())
        }
    }

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

        adapter.setMyData(recyclerViewListItem)
        /**
         * scroll top
         */
        binding.list.scrollToPosition(todayPosition)


    }

    /**
     * converted fetched list to hash map list to group listview by date
     */
    fun groupByDate(list: ArrayList<MatchesItem>): Map<LocalDate, ArrayList<MatchesItem>> {
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


    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }
}