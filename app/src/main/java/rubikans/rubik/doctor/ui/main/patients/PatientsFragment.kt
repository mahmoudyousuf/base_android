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

import rubikans.rubik.doctor.databinding.FragmentHomeBinding
import rubikans.rubik.doctor.model.CheckClinicSettingModelItem
import rubikans.rubik.doctor.ui.auth.AuthActivity
import rubikans.rubik.doctor.ui.bottomSheets.appointmentsFilter.AppointmentsFilterBottomSheet
import rubikans.rubik.doctor.ui.bottomSheets.changeAppointmentstatus.ChangeAppointmentStatusBottomSheet
import rubikans.rubik.doctor.ui.dialogs.CompleteClinicSettingDialog
import rubikans.rubik.doctor.ui.dialogs.ConfirmationAppointmentDialog
import rubikans.rubik.doctor.ui.main.clinicBraches.ClinicBranchesActivity
import rubikans.rubik.doctor.util.extensions.hide
import rubikans.rubik.doctor.util.extensions.observe
import rubikans.rubik.doctor.util.extensions.visible
import rubikans.rubikcare.util.paging.CustomLoadStateAdapter
import java.util.*


@AndroidEntryPoint
class PatientsFragment : BaseFragment<FragmentHomeBinding>(),
    SearchView.OnQueryTextListener {
    lateinit var binding: FragmentHomeBinding
    val viewModel: PatientsViewModel by activityViewModels()
    private lateinit var adapter: PatientsAdapter
    var charCount = 0


    override fun onResume() {
        super.onResume()

        if(baseActivity.dataManager.refreshHome == "1"){
            adapter.refresh()
            lifecycleScope.launch {
                adapter.loadStateFlow
                    .collect {
                        binding.appointmentsList.smoothScrollToPosition(0)
                    }
            }
        }
        baseActivity.dataManager.saveIsRefreshHome("0")
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = viewDataBinding!!

        charCount = 0

        binding.customBar.leftImage = R.drawable.ic_home_menu
        binding.customBar.hideRightIcon()
        binding.customBar.visibleAddIcon()
        binding.customBar.addCardView.setOnClickListener {
            viewModel.checkClinicSetting(baseActivity.dataManager.clinic!!.entityBranchID.toString())

        }

        binding.customBar.leftCardView.setOnClickListener {
            startActivity(Intent(baseActivity, ClinicBranchesActivity::class.java))

        }




        binding.refresh.setOnRefreshListener {
            adapter.refresh()
        }



        binding.filterBtn.setOnClickListener {
            val bottomSheet = AppointmentsFilterBottomSheet(
                { statusId, applyFromDate, applyToDate ->

                    viewModel.pStatusID.postValue(statusId)
                    viewModel.pDateFrom.postValue(applyFromDate)
                    viewModel.pDateTo.postValue(applyToDate)


                }, {

                    viewModel.pStatusID.postValue("")
                    viewModel.pDateFrom.postValue("")
                    viewModel.pDateTo.postValue("")

                },

                statusId = viewModel.pStatusID.value ?: "",
                selectedFromDate = viewModel.pDateFrom.value ?: "",
                selectedToDate = viewModel.pDateTo.value ?: ""

            )
            bottomSheet.show(baseActivity.supportFragmentManager, "AppointmentsFilterBottomSheet")
        }


        adapter = PatientsAdapter(baseActivity,
            onItemClicked = {


//                navController.navigate(HomeFragmentDirections.openappointmentDetailsFragment(it.bookingID.toString()))

            },


            onConfirmClicked = {

                val bottomSheet = ChangeAppointmentStatusBottomSheet(
                    onConfirmed = {



                        viewModel.changeAppointmentStatus(
                            pBookingID = it.bookingID.toString(),
                            pStatus = "2"
                        )


                    },


                )
                bottomSheet.show(
                    baseActivity.supportFragmentManager,
                    "AppointmentsFilterBottomSheet"
                )


            },


            onCancelClicked = {
                val bottomSheet = ChangeAppointmentStatusBottomSheet(
                    onConfirmed = {

                        viewModel.changeAppointmentStatus(
                            pBookingID = it.bookingID.toString(),
                            pStatus = "5"
                        )


                    },


                )
                bottomSheet.show(
                    baseActivity.supportFragmentManager,
                    "AppointmentsFilterBottomSheet"
                )

            },
            onNoShowClicked = {
                val bottomSheet = ChangeAppointmentStatusBottomSheet(
                    onConfirmed = {

                        viewModel.changeAppointmentStatus(
                            pBookingID = it.bookingID.toString(),
                            pStatus = "6"
                        )

                    },


                )
                bottomSheet.show(
                    baseActivity.supportFragmentManager,
                    "AppointmentsFilterBottomSheet"
                )

            },
            onDoneClicked = {
                val bottomSheet = ChangeAppointmentStatusBottomSheet(
                    onConfirmed = {



                        viewModel.changeAppointmentStatus(
                            pBookingID = it.bookingID.toString(),
                            pStatus = "4"
                        )


                    },



                )
                bottomSheet.show(
                    baseActivity.supportFragmentManager,
                    "AppointmentsFilterBottomSheet"
                )

            },
            onOnServiceClicked = {
                val bottomSheet = ChangeAppointmentStatusBottomSheet(
                    onConfirmed = {

                        viewModel.changeAppointmentStatus(
                            pBookingID = it.bookingID.toString(),
                            pStatus = "3"
                        )

                    },



                )
                bottomSheet.show(
                    baseActivity.supportFragmentManager,
                    "AppointmentsFilterBottomSheet"
                )

            }
        )



        binding.appointmentsList.adapter = adapter




        binding.searchView.setOnQueryTextListener(this)



//        viewModel.getAppointments()

        setObserver()


    }


    private fun setObserver() {

        observe(viewModel.pBranchId)
        {
            adapter.refresh()
        }

        observe(viewModel.pBookingID)
        {
            adapter.refresh()
        }

        observe(viewModel.pPatientID)
        {
            adapter.refresh()
        }

        observe(viewModel.pStatusID)
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
            viewModel.appointmentsList.collectLatest {
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
                    charCount ++
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
        return R.layout.fragment_home
    }


    override fun onQueryTextSubmit(query: String): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        val userInput = newText.lowercase(Locale.getDefault())

        if(charCount != 0){
            viewModel.pSearchText.postValue(userInput)
        }
        charCount++


        return true

    }

}