package com.example.carefertask.ui.main.fragments.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.etamn.util.Status
import com.example.carefertask.R
import com.example.carefertask.base.BaseFragment
import com.example.carefertask.databinding.FragmentHomeBinding
import com.example.carefertask.model.HeaderItem
import com.example.carefertask.model.ListItem
import com.example.carefertask.model.MatchItem
import com.example.carefertask.model.MatchesItem
import com.example.carefertask.model.MatchesResponse
import com.example.carefertask.ui.main.fragments.home.util.UtilFun
import com.example.carefertask.util.extensions.observe
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    val viewModel: HomeViewModel by viewModels()
    lateinit var binding: FragmentHomeBinding
    lateinit var adapter: HomeAdapter
    val faveList = ArrayList<Int>()
    var matchesList = ArrayList<MatchesItem>()
    var myTeams = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding!!

        setUpRecycler()
        setObservers()
        seUpTabLayout()

        viewModel.getMatches()
    }

    private fun seUpTabLayout() {
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("est"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("eqwest"))
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                myTeams = tab?.position == 1
                viewModel.getMatches()

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {


            }

            override fun onTabReselected(tab: TabLayout.Tab?) {


            }
        })

    }

    private fun setUpRecycler() {
        adapter = HomeAdapter(ArrayList(), {
            viewModel.addFaveMatch(it)
        }, {
            navController.navigate(HomeFragmentDirections.openDetailsFragment())
        })
        binding.list.adapter = adapter
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
                    matchesList = response.matches!!

                    handelMatchesResponse(matchesList, faveList)
                }

                is Status.Error -> {
                    hideDialogLoading()
                    showWarningSnackbar(resources.getString(R.string.some_thing_went_wrong_error_msg))

                }


            }
        }

        observe(viewModel.faveList)
        {

            for (i in it)
                faveList.add(i?.teamId!!)
        }


    }

    private fun handelMatchesResponse(matches: ArrayList<MatchesItem>?, faveList: ArrayList<Int>) {


        val mapList = UtilFun.groupByDate(matches ?: ArrayList())
        val nearestDate = UtilFun.findClosestDate(mapList.keys.toList(), LocalDate.now())
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
                if (myTeams) {
                    if (faveList.contains(event.homeTeam?.id)) {
                        event.homeTeam?.fave = true
                        val item = MatchItem(event)
                        recyclerViewListItem.add(item)
                    }

                    if (faveList.contains(event.awayTeam?.id)) {
                        event.awayTeam?.fave = true
                        val item = MatchItem(event)
                        recyclerViewListItem.add(item)
                    }
                } else {
                    if (faveList.contains(event.homeTeam?.id))
                        event.homeTeam?.fave = true

                    if (faveList.contains(event.awayTeam?.id))
                        event.awayTeam?.fave = true


                    val item = MatchItem(event)
                    recyclerViewListItem.add(item)
                }

            }
        }

        adapter.setMyData(recyclerViewListItem)
        /**
         * scroll top
         */
        binding.list.scrollToPosition(todayPosition)


    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }
}