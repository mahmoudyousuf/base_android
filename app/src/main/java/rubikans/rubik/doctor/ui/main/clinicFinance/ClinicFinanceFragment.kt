package rubikans.rubik.doctor.ui.main.clinicFinance

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.etamn.util.Status

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.custon_top_bar.view.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.base.BaseFragment
import rubikans.rubik.doctor.base.BaseResponse
import rubikans.rubik.doctor.databinding.FragmentClinicFinanceBinding
import rubikans.rubik.doctor.ui.bottomSheets.ClinicFinanceFilter.ClinicFinanceFilterBottomSheet
import rubikans.rubik.doctor.ui.bottomSheets.deleteExpense.DeleteExpenseBottomSheet

import rubikans.rubik.doctor.util.extensions.hide
import rubikans.rubik.doctor.util.extensions.observe
import rubikans.rubik.doctor.util.extensions.toJsonString
import rubikans.rubik.doctor.util.extensions.visible
import rubikans.rubikcare.util.paging.CustomLoadStateAdapter
import java.util.*


@AndroidEntryPoint
class ClinicFinanceFragment : BaseFragment<FragmentClinicFinanceBinding>(),
    SearchView.OnQueryTextListener {
    lateinit var binding: FragmentClinicFinanceBinding
    val viewModel: ClinicFinanceViewModel by activityViewModels()
    private lateinit var clinicIncomeAdapter: ClinicIncomeAdapter
    private lateinit var clinicExpenseAdapter: ClinicExpenseAdapter


    override fun onResume() {
        super.onResume()
        viewModel.setIncomeState(viewModel.isIncome.value!!)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = viewDataBinding!!



        binding.customBar.leftImage = R.drawable.ic_home_menu
        binding.customBar.hideRightIcon()
        binding.customBar.hideLeftIcon()

        if(viewModel.isIncome.value == true){
            binding.customBar.hideAddIcon()
        }else {
            binding.customBar.visibleAddIcon()

        }
        binding.customBar.addCardView.setOnClickListener {
            navController.navigate(ClinicFinanceFragmentDirections.openAddNewExpenseFragment(
                "",
                "false"
            ))


        }




        binding.refresh.setOnRefreshListener {
            if (viewModel.isIncome.value!!) {
                clinicIncomeAdapter.refresh()
                lifecycleScope.launch {
                    clinicIncomeAdapter.loadStateFlow
                        .collect {
                            binding.financeList.smoothScrollToPosition(0)
                        }
                }

            } else {
                clinicExpenseAdapter.refresh()
                lifecycleScope.launch {
                    clinicExpenseAdapter.loadStateFlow
                        .collect {
                            binding.financeList.smoothScrollToPosition(0)
                        }
                }

            }

        }



        binding.filterBtn.setOnClickListener {
            val bottomSheet = ClinicFinanceFilterBottomSheet(
                { typeID, typeName , applyFromDate, applyToDate ->

                    viewModel.pIncomeTypeID.postValue(typeID)
                    viewModel.pIncomeTypeName.postValue(typeName)
                    viewModel.pDateFrom.postValue(applyFromDate)
                    viewModel.pDateTo.postValue(applyToDate)


                }, {

                    viewModel.pIncomeTypeID.postValue("")
                    viewModel.pIncomeTypeName.postValue("")
                    viewModel.pDateFrom.postValue("")
                    viewModel.pDateTo.postValue("")

                },

                typeID = viewModel.pIncomeTypeID.value ?: "",
                typeName = viewModel.pIncomeTypeName.value ?: "",
                selectedFromDate = viewModel.pDateFrom.value ?: "",
                selectedToDate = viewModel.pDateTo.value ?: "",
                isIncome = viewModel.isIncome.value!!

            )
            bottomSheet.show(baseActivity.supportFragmentManager, "PatientsFilterBottomSheet")
        }


        clinicIncomeAdapter = ClinicIncomeAdapter(baseActivity,
            onItemClicked = {


//                navController.navigate(HomeFragmentDirections.openappointmentDetailsFragment(it.bookingID.toString()))

            },


        )


        clinicExpenseAdapter = ClinicExpenseAdapter(baseActivity,
            onDelete = {
                val bottomSheet = DeleteExpenseBottomSheet( it , clinicExpenseAdapter)
                bottomSheet.show(baseActivity.supportFragmentManager, "DeleteExpenseBottomSheet")
            }, onEdit = {

                navController.navigate(ClinicFinanceFragmentDirections.openAddNewExpenseFragment(
                    it.toJsonString(),
                    "true"
                ))


            },
            onItemClicked = {

//                navController.navigate(HomeFragmentDirections.openappointmentDetailsFragment(it.bookingID.toString()))
            },

            )




        if (viewModel.isIncome.value!!) {
            binding.financeList.adapter = clinicIncomeAdapter
        } else {
            binding.financeList.adapter = clinicExpenseAdapter
        }


        binding.incomeBtn.setOnClickListener {
            viewModel.pIncomeTypeID.postValue("")
            viewModel.pIncomeTypeName.postValue("")
            viewModel.pDateFrom.postValue("")
            viewModel.pDateTo.postValue("")
            binding.financeList.adapter = clinicIncomeAdapter
            viewModel.setIncomeState(true)
        }

        binding.expenseBtn.setOnClickListener {
            viewModel.pIncomeTypeID.postValue("")
            viewModel.pIncomeTypeName.postValue("")
            viewModel.pDateFrom.postValue("")
            viewModel.pDateTo.postValue("")
            binding.financeList.adapter = clinicExpenseAdapter
            viewModel.setIncomeState(false)
        }




        binding.searchView.setOnQueryTextListener(this)




        setObserver()


    }


    private fun setObserver() {


        observe(viewModel.pIncomeTypeID)
        {
            if (viewModel.isIncome.value!!) {
                clinicIncomeAdapter.refresh()
                lifecycleScope.launch {
                    clinicIncomeAdapter.loadStateFlow
                        .collect {
                            binding.financeList.smoothScrollToPosition(0)
                        }
                }

            } else {
                clinicExpenseAdapter.refresh()
                lifecycleScope.launch {
                    clinicExpenseAdapter.loadStateFlow
                        .collect {
                            binding.financeList.smoothScrollToPosition(0)
                        }
                }

            }
        }

        observe(viewModel.pDateFrom)
        {
            if (viewModel.isIncome.value!!) {
                clinicIncomeAdapter.refresh()
                lifecycleScope.launch {
                    clinicIncomeAdapter.loadStateFlow
                        .collect {
                            binding.financeList.smoothScrollToPosition(0)
                        }
                }

            } else {
                clinicExpenseAdapter.refresh()
                lifecycleScope.launch {
                    clinicExpenseAdapter.loadStateFlow
                        .collect {
                            binding.financeList.smoothScrollToPosition(0)
                        }
                }

            }
        }

        observe(viewModel.pDateTo)
        {
            if (viewModel.isIncome.value!!) {
                clinicIncomeAdapter.refresh()
                lifecycleScope.launch {
                    clinicIncomeAdapter.loadStateFlow
                        .collect {
                            binding.financeList.smoothScrollToPosition(0)
                        }
                }

            } else {
                clinicExpenseAdapter.refresh()
                lifecycleScope.launch {
                    clinicExpenseAdapter.loadStateFlow
                        .collect {
                            binding.financeList.smoothScrollToPosition(0)
                        }
                }

            }
        }

        observe(viewModel.pSearchText)
        {
            if (viewModel.isIncome.value!!) {
                clinicIncomeAdapter.refresh()
                lifecycleScope.launch {
                    clinicIncomeAdapter.loadStateFlow
                        .collect {
                            binding.financeList.smoothScrollToPosition(0)
                        }
                }

            } else {
                clinicExpenseAdapter.refresh()
                lifecycleScope.launch {
                    clinicExpenseAdapter.loadStateFlow
                        .collect {
                            binding.financeList.smoothScrollToPosition(0)
                        }
                }

            }
        }



        observe(viewModel.isIncome)
        {

            if(viewModel.isIncome.value == true){
                binding.customBar.hideAddIcon()
            }else {
                binding.customBar.visibleAddIcon()

            }

            if (viewModel.isIncome.value == true) {
                lifecycleScope.launch {
                    viewModel.incomeList.collectLatest {
                        clinicIncomeAdapter.submitData(it)
                    }
                }
                binding.financeList.adapter = clinicIncomeAdapter.withLoadStateFooter(
                    footer = CustomLoadStateAdapter {
                        clinicIncomeAdapter.retry()
                    }
                )


                lifecycleScope.launch {
                    clinicIncomeAdapter.loadStateFlow.collect {
                        if (it.refresh is LoadState.Loading) {
                            baseActivity.showDialogLoading()
                        } else {
                            baseActivity.hideDialogLoading()

                            try {
                                binding.refresh.isRefreshing = false
                            } catch (e: java.lang.Exception) {

                            }
                            if (clinicIncomeAdapter.itemCount == 0) {
                                binding.emptyDataLayout.visible()
                            } else {
                                binding.financeList.visible()
                                binding.emptyDataLayout.hide()

                            }
                        }
                    }
                }

            } else {

                lifecycleScope.launch {
                    viewModel.expenseList.collectLatest {
                        clinicExpenseAdapter.submitData(it)
                    }
                }
                binding.financeList.adapter = clinicExpenseAdapter.withLoadStateFooter(
                    footer = CustomLoadStateAdapter {
                        clinicExpenseAdapter.retry()
                    }
                )


                lifecycleScope.launch {
                    clinicExpenseAdapter.loadStateFlow.collect {
                        if (it.refresh is LoadState.Loading) {
                            baseActivity.showDialogLoading()
                        } else {
                            baseActivity.hideDialogLoading()

                            try {
                                binding.refresh.isRefreshing = false
                            } catch (e: java.lang.Exception) {

                            }
                            if (clinicExpenseAdapter.itemCount == 0) {
                                binding.emptyDataLayout.visible()
                            } else {
                                binding.financeList.visible()
                                binding.emptyDataLayout.hide()

                            }
                        }
                    }
                }


            }
        }





    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_clinic_finance
    }


    override fun onQueryTextSubmit(query: String): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        val userInput = newText.lowercase(Locale.getDefault())

            viewModel.pSearchText.postValue(userInput)



        return true

    }

}