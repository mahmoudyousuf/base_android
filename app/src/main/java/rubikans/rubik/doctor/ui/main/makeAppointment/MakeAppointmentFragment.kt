package rubikans.rubik.doctor.ui.main.makeAppointment

import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.etamn.util.Status
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.base.BaseFragment
import rubikans.rubik.doctor.base.BaseResponse
import rubikans.rubik.doctor.databinding.FragmentAddAppointmentBinding
import rubikans.rubik.doctor.databinding.FragmentChangePasswordBinding
import rubikans.rubik.doctor.model.AppointmentsTimesModelItem
import rubikans.rubik.doctor.model.ConsultationServicesModelItem
import rubikans.rubik.doctor.model.PaymentMethodItem
import rubikans.rubik.doctor.model.PaymentMethodsModel
import rubikans.rubik.doctor.ui.auth.AuthActivity
import rubikans.rubik.doctor.ui.bottomSheets.selectPatient.SelectPatientBottomSheet
import rubikans.rubik.doctor.ui.bottomSheets.signOut.SignOutBottomSheet
import rubikans.rubik.doctor.ui.dialogs.ConfirmationAppointmentDialog
import rubikans.rubik.doctor.util.extensions.hide
import rubikans.rubik.doctor.util.extensions.observe
import rubikans.rubik.doctor.util.extensions.visible
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint
class MakeAppointmentFragment : BaseFragment<FragmentAddAppointmentBinding>() {
    lateinit var binding: FragmentAddAppointmentBinding
    val viewModel: MakeAppointmentViewModel by viewModels()
    lateinit var appointmentDate: DatePickerDialog.OnDateSetListener
    private val myCalendar = Calendar.getInstance()
    var consultationServiceId = 0
    var paymentMethodIdId = 0
    var bookingFees = "0"
    lateinit var timeAdapter: TimeAdapter
    var appointmentsTimesModelItem: AppointmentsTimesModelItem = AppointmentsTimesModelItem()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        appointmentDate =
            DatePickerDialog.OnDateSetListener { view, year, month, day ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, day)
                updateAppointmentDate()
            }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = viewDataBinding!!
        binding.customBar.leftImage = R.drawable.back
        binding.customBar.leftCardView.setOnClickListener {
            navController.navigateUp()
        }
        binding.customBar.hideRightIcon()


        binding.amountEdtTxt.setFocusable()




        if (baseActivity.dataManager.clinic!!.isTimeSlot!!) {
            binding.firstComeDataLayout.hide()
            binding.timeList.visible()
            timeAdapter = TimeAdapter(baseActivity, arrayListOf()) { it, position ->

                appointmentsTimesModelItem = it
                baseActivity.showSuccessSnackbar(it.startTime)
            }

            binding.timeList.adapter = timeAdapter

        } else {
            binding.timeList.hide()
            binding.firstComeDataLayout.visible()

        }








        binding.appointDateEdt.setLeftIcon(R.drawable.ic_calender)
        binding.appointDateEdt.setFocusable()
        binding.appointDateEdt.setOnClickListenerEditText {


            if (consultationServiceId == 0) {
                baseActivity.showWarningSnackbar(getString(R.string.select_consultation_service_first))
            } else {


                binding.appointDateEdt.showDate {
                    DatePickerDialog(
                        baseActivity,
                        appointmentDate,
                        myCalendar[Calendar.YEAR],
                        myCalendar[Calendar.MONTH],
                        myCalendar[Calendar.DAY_OF_MONTH]
                    ).show()
                }
            }


        }


        binding.patientNameDropDown.setOnDropDownClickListner() {
            val bottomSheet = SelectPatientBottomSheet(viewModel)
            bottomSheet.show(baseActivity.supportFragmentManager, "SelectPatientBottomSheet")
        }


        binding.confirmBtn.setOnClickListener {
            if (binding.serviceTypeDropDown.getText().isEmpty()) {
                binding.serviceTypeDropDown.setError(getString(R.string.consultation_service_are_required))
                return@setOnClickListener
            } else {
                binding.serviceTypeDropDown.removeError()
            }

            if (binding.patientNameDropDown.getText().isEmpty()) {
                binding.patientNameDropDown.setError(getString(R.string.patient_name_are_required))
                return@setOnClickListener
            } else {
                binding.patientNameDropDown.removeError()
            }


            if (binding.paymentMethodDropDown.getText().isEmpty()) {
                binding.paymentMethodDropDown.setError(getString(R.string.payment_method_are_required))
                return@setOnClickListener
            } else {
                binding.paymentMethodDropDown.removeError()
            }


            if (binding.appointDateEdt.getText().isEmpty()) {
                binding.appointDateEdt.setError(getString(R.string.appointment_date_are_required))
                return@setOnClickListener
            } else {
                binding.appointDateEdt.removeError()
            }


            if (appointmentsTimesModelItem.entityBranchID == null) {
                baseActivity.showWarningSnackbar(getString(R.string.select_appointment_time_first))
                return@setOnClickListener
            }



            if(baseActivity.dataManager.clinic!!.isTimeSlot == false && binding.tvAvailableRemainingCasesTxt.text == "0"){
                baseActivity.showWarningSnackbar(getString(R.string.no_available_appointments_at_this_day))
                return@setOnClickListener
            }



            val props = JSONObject()
            props.put("PatientProfileID", viewModel.selectedPatient.value!!.profileID.toString())
            props.put("EntityBranchID", baseActivity.dataManager.clinic!!.entityBranchID.toString())
            props.put("ConsultationServiceID", consultationServiceId)
            props.put("BookingDate", appointmentsTimesModelItem.dayDate)
            props.put("StartTime", appointmentsTimesModelItem.startTimes)
            props.put("EndTime", appointmentsTimesModelItem.endTimes)
            props.put("BookingReason", binding.patientComplaintEdt.text)
            props.put("BookingFees", bookingFees)
            props.put("CurrencyID", "1")
            props.put("BookingStatus", "1")
            props.put("PaymentMethodsID", paymentMethodIdId)


            viewModel.addAppointment(jsonParser.parse(props.toString()) as JsonObject)




        }





        viewModel.getConsultationServices(baseActivity.dataManager.clinic!!.entityBranchID.toString())
        viewModel.getPaymentMethods()
        setObservers()
    }

    private fun updateAppointmentDate() {
        val myDate = "YYYY-MM-dd"
        val dateFormat = SimpleDateFormat(myDate, Locale.US)
        binding.appointDateEdt.setText(dateFormat.format(myCalendar.time))
        appointmentsTimesModelItem = AppointmentsTimesModelItem()
        viewModel.getAppointmentsTimes(
            pEntityBranchID = baseActivity.dataManager.clinic!!.entityBranchID.toString(),
            pConsultationServicesID = consultationServiceId.toString(),
            pDate = dateFormat.format(myCalendar.time)
        )
    }


    private fun setObservers() {


        observe(viewModel.selectedPatient)
        {

            if (viewModel.selectedPatient.value!!.profileName != null) {
                binding.patientNameDropDown.setText(viewModel.selectedPatient.value!!.profileName!!)

            }

        }



        observe(viewModel.getAppointmentsTimesState)
        {
            when (it) {
                is Status.Loading -> {
                    baseActivity.showDialogLoading()
                }
                is Status.Success<*> -> {
                    baseActivity.hideDialogLoading()
                    val response = it.data as BaseResponse<ArrayList<AppointmentsTimesModelItem>>


                    if (baseActivity.dataManager.clinic!!.isTimeSlot!!) {
                        timeAdapter.setMyData(response.data!!)
                        timeAdapter.changeSelectedItem(-1)

                    } else {
                        if(response.data!!.isNotEmpty()){

                            appointmentsTimesModelItem = response.data!![0]

                            binding.tvNumberOfCaseTxt.text =
                                appointmentsTimesModelItem.timeSlotValue.toString()
                            binding.tvNumberOfReservationTxt.text =
                                appointmentsTimesModelItem.bookingCount.toString()
                            binding.tvAvailableRemainingCasesTxt.text =
                                (appointmentsTimesModelItem.timeSlotValue!! - appointmentsTimesModelItem.bookingCount!!).toString()
                        }else{
                            baseActivity.showWarningSnackbar(getString(R.string.no_booking_found_in_this_date))
                        }


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


        observe(viewModel.getConsultationServicesState)
        {
            when (it) {
                is Status.Loading -> {
                    baseActivity.showDialogLoading()
                }
                is Status.Success<*> -> {
                    baseActivity.hideDialogLoading()
                    val response = it.data as BaseResponse<ArrayList<ConsultationServicesModelItem>>

                    binding.serviceTypeDropDown.setOnDropDownClickListner {

                        val serviceTypePopUp = PopupMenu(context, binding.serviceTypeDropDown)
                        for (i in 0 until response.data!!.size) {

                            serviceTypePopUp.menu.add(
                                i,
                                i,
                                i,
                                response.data!![i].consultationServiceName
                            )

                        }
                        serviceTypePopUp.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->


                            binding.serviceTypeDropDown.setText(response.data!![item.itemId].consultationServiceName!!)

                            consultationServiceId =
                                response.data!![item.itemId].consultationServiceID!!

                            binding.amountEdtTxt.setText(
                                response.data!![item.itemId].fees.toString() + getString(
                                    R.string.egp
                                )
                            )
                            bookingFees = response.data!![item.itemId].fees.toString()


                            Log.d(ContentValues.TAG, "onViewCreated: $consultationServiceId")
                            return@OnMenuItemClickListener false
                        })
                        serviceTypePopUp.show()

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



        observe(viewModel.getPaymentMethodsState)
        {
            when (it) {
                is Status.Loading -> {
                    baseActivity.showDialogLoading()
                }
                is Status.Success<*> -> {
                    baseActivity.hideDialogLoading()
                    val response = it.data as BaseResponse<PaymentMethodsModel>


                    binding.paymentMethodDropDown.setOnDropDownClickListner {

                        val paymentMethodUp = PopupMenu(context, binding.paymentMethodDropDown)
                        for (i in 0 until response.data!!.paymentMethod!!.size) {

                            paymentMethodUp.menu.add(
                                i,
                                i,
                                i,
                                response.data!!.paymentMethod!![i].mainName
                            )

                        }
                        paymentMethodUp.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->


                            binding.paymentMethodDropDown.setText(response.data!!.paymentMethod!![item.itemId].mainName!!)

                            paymentMethodIdId =
                                response.data!!.paymentMethod!![item.itemId].paymentMethodsID!!




                            Log.d(ContentValues.TAG, "onViewCreated: $consultationServiceId")
                            return@OnMenuItemClickListener false
                        })
                        paymentMethodUp.show()

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




        observe(viewModel.addAppointmentState)
        {
            when (it) {
                is Status.Loading -> {
                    baseActivity.showDialogLoading()
                }
                is Status.Success<*> -> {
                    baseActivity.hideDialogLoading()
                    val response = it.data as BaseResponse<BaseResponse.EmptyData>
                    val confirmationBookDialog = ConfirmationAppointmentDialog(baseActivity)
                    confirmationBookDialog.show(
                        baseActivity.supportFragmentManager,
                        "ConfirmationAppointmentDialog"
                    )

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
        return R.layout.fragment_add_appointment
    }

}