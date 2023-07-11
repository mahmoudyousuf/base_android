package rubikans.rubik.doctor.ui.main.patients

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.etamn.util.Status

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.custon_top_bar.view.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.base.BaseFragment
import rubikans.rubik.doctor.base.BaseResponse
import rubikans.rubik.doctor.databinding.FragmentPatientsBinding

import rubikans.rubik.doctor.model.CheckClinicSettingModelItem
import rubikans.rubik.doctor.ui.auth.AuthActivity
import rubikans.rubik.doctor.ui.bottomSheets.patientsFilter.PatientsFilterBottomSheet
import rubikans.rubik.doctor.ui.dialogs.CompleteClinicSettingDialog
import rubikans.rubik.doctor.util.extensions.hide
import rubikans.rubik.doctor.util.extensions.observe
import rubikans.rubik.doctor.util.extensions.visible
import rubikans.rubikcare.util.paging.CustomLoadStateAdapter
import java.util.*


@AndroidEntryPoint
class PatientsFragment : BaseFragment<FragmentPatientsBinding>(),
    SearchView.OnQueryTextListener {
    lateinit var binding: FragmentPatientsBinding
    val viewModel: PatientsViewModel by activityViewModels()
    private lateinit var adapter: PatientsAdapter





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = viewDataBinding!!



        binding.customBar.leftImage = R.drawable.ic_home_menu
        binding.customBar.hideRightIcon()
        binding.customBar.visibleAddIcon()
        binding.customBar.addCardView.setOnClickListener {
            navController.navigate(PatientsFragmentDirections.openAddNewPatientFragment())


        }




        binding.refresh.setOnRefreshListener {
            adapter.refresh()
        }



        binding.filterBtn.setOnClickListener {
            val bottomSheet = PatientsFilterBottomSheet(
                { insuranceId, insuranceName , applyFromDate, applyToDate ->

                    viewModel.pInsuranceCompanyId.postValue(insuranceId)
                    viewModel.pInsuranceCompanyName.postValue(insuranceName)
                    viewModel.pDateFrom.postValue(applyFromDate)
                    viewModel.pDateTo.postValue(applyToDate)


                }, {

                    viewModel.pInsuranceCompanyId.postValue("")
                    viewModel.pInsuranceCompanyName.postValue("")
                    viewModel.pDateFrom.postValue("")
                    viewModel.pDateTo.postValue("")

                },

                insuranceCompanyID = viewModel.pInsuranceCompanyId.value ?: "",
                insuranceCompanyName = viewModel.pInsuranceCompanyName.value ?: "",
                selectedFromDate = viewModel.pDateFrom.value ?: "",
                selectedToDate = viewModel.pDateTo.value ?: ""

            )
            bottomSheet.show(baseActivity.supportFragmentManager, "PatientsFilterBottomSheet")
        }


        adapter = PatientsAdapter(baseActivity,
            onItemClicked = {


//                navController.navigate(HomeFragmentDirections.openappointmentDetailsFragment(it.bookingID.toString()))

            },


        )



        binding.appointmentsList.adapter = adapter




        binding.searchView.setOnQueryTextListener(this)



//        viewModel.getAppointments()

        setObserver()


    }


    private fun setObserver() {


        observe(viewModel.pPhoneNumber)
        {
            adapter.refresh()
        }

        observe(viewModel.pDateFrom)
        {
            adapter.refresh()
        }

        observe(viewModel.pDateTo)
        {
            adapter.refresh()
        }

        observe(viewModel.pSearchText)
        {
            adapter.refresh()
        }




        lifecycleScope.launch {
            viewModel.patientsList.collectLatest {
                adapter.submitData(it)
            }
        }
        binding.appointmentsList.adapter = adapter.withLoadStateFooter(
            footer = CustomLoadStateAdapter {
                adapter.retry()
            }
        )


        lifecycleScope.launch {
            adapter.loadStateFlow.collect {
                if (it.refresh is LoadState.Loading) {
                    baseActivity.showDialogLoading()
                } else {
                    baseActivity.hideDialogLoading()

                    try {
                        binding.refresh.isRefreshing = false
                    } catch (e: java.lang.Exception) {

                    }
                    if (adapter.itemCount == 0) {
                        binding.emptyDataLayout.visible()
                    } else {
                        binding.appointmentsList.visible()
                        binding.emptyDataLayout.hide()

                    }
                }
            }
        }







        observe(viewModel.changeAppointmentStatusState)
        {
            when (it) {
                is Status.Loading -> {
                    baseActivity.showDialogLoading()
                }
                is Status.Success<*> -> {
                    baseActivity.hideDialogLoading()
                    val response = it.data as BaseResponse<BaseResponse.EmptyData>
                   adapter.refresh()

                }
                is Status.Error -> {
                    baseActivity.hideDialogLoading()
                    baseActivity.showWarningSnackbar(it.message!!)

                }
                is Status.Unauthorized -> {

                    baseActivity.showWarningSnackbar(getString(R.string.please_login))

                    val i = Intent(baseActivity, AuthActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                    baseActivity.finish()

                }
            }
        }






        observe(viewModel.checkClinicSettingState)
        {
            when (it) {
                is Status.Loading -> {
                    baseActivity.showDialogLoading()
                }
                is Status.Success<*> -> {
                    baseActivity.hideDialogLoading()
                    val response = it.data as BaseResponse<ArrayList<CheckClinicSettingModelItem>>


                    if(response.data!![0].columnType == 1){
//                        navController.navigate(HomeFragmentDirections.openMakeAppointmentFragment())

                    }else{


                        val completeClinicSettingDialog = CompleteClinicSettingDialog(baseActivity)
                        completeClinicSettingDialog.show(
                            baseActivity.supportFragmentManager,
                            "CompleteClinicSettingDialog"
                        )
                    }


                }
                is Status.Error -> {
                    baseActivity.hideDialogLoading()
                    baseActivity.showWarningSnackbar(it.message!!)

                }
                is Status.Unauthorized -> {

                    baseActivity.showWarningSnackbar(getString(R.string.please_login))

                    val i = Intent(baseActivity, AuthActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                    baseActivity.finish()

                }
            }
        }



    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_patients
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