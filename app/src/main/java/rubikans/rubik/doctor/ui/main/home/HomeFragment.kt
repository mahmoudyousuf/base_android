package rubikans.rubik.doctor.ui.main.home

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.etamn.util.Status

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.base.BaseFragment
import rubikans.rubik.doctor.base.BaseResponse

import rubikans.rubik.doctor.databinding.FragmentHomeBinding
import rubikans.rubik.doctor.ui.auth.AuthActivity
import rubikans.rubik.doctor.ui.bottomSheets.appointmentsFilter.AppointmentsFilterBottomSheet
import rubikans.rubik.doctor.ui.bottomSheets.changeAppointmentstatus.ChangeAppointmentStatusBottomSheet
import rubikans.rubik.doctor.ui.bottomSheets.signOut.SignOutBottomSheet
import rubikans.rubik.doctor.ui.clinicBraches.ClinicBranchesActivity
import rubikans.rubik.doctor.util.extensions.hide
import rubikans.rubik.doctor.util.extensions.observe
import rubikans.rubik.doctor.util.extensions.visible
import rubikans.rubikcare.util.paging.CustomLoadStateAdapter
import java.util.*


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(),
    SearchView.OnQueryTextListener {
    lateinit var binding: FragmentHomeBinding
    val viewModel: HomeViewModel by activityViewModels()
    private lateinit var adapter: AppointmentsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = viewDataBinding!!


        binding.customBar.hideRightIcon()
        binding.customBar.leftImage = R.drawable.ic_home_menu
        binding.customBar.leftCardView.setOnClickListener {

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


        adapter = AppointmentsAdapter(baseActivity,
            onItemClicked = {


                navController.navigate(HomeFragmentDirections.openappointmentDetailsFragment(it.bookingID.toString()))

            },


            onConfirmClicked = {

                val bottomSheet = ChangeAppointmentStatusBottomSheet(
                    onConfirmed = {


                        baseActivity.showSuccessSnackbar("confirm clicked")

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


                        baseActivity.showSuccessSnackbar("cancel clicked")

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


                        baseActivity.showSuccessSnackbar("no show clicked")

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


                        baseActivity.showSuccessSnackbar("done clicked")

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


                        baseActivity.showSuccessSnackbar("on service clicked")

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



        viewModel.getAppointments()

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
                binding.appointmentsList.adapter
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


    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_home
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