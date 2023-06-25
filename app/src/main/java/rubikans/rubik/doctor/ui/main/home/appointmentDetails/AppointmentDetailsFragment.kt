package rubikans.rubik.doctor.ui.main.home.appointmentDetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.etamn.util.Status
import dagger.hilt.android.AndroidEntryPoint
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.base.BaseFragment
import rubikans.rubik.doctor.base.BaseResponse
import rubikans.rubik.doctor.databinding.FragmentAppointmentDetailsBinding
import rubikans.rubik.doctor.model.AppointmentsData
import rubikans.rubik.doctor.ui.auth.AuthActivity
import rubikans.rubik.doctor.util.CommonUtilities
import rubikans.rubik.doctor.util.extensions.hide
import rubikans.rubik.doctor.util.extensions.observe


@AndroidEntryPoint
class AppointmentDetailsFragment : BaseFragment<FragmentAppointmentDetailsBinding>() {
    lateinit var binding: FragmentAppointmentDetailsBinding
    val viewModel: AppointmentDetailsViewModel by viewModels()
    private val args by navArgs<AppointmentDetailsFragmentArgs>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = viewDataBinding!!
        binding.customBar.leftImage = R.drawable.back
        binding.customBar.leftCardView.setOnClickListener {
            navController.navigateUp()
        }
        binding.customBar.hideRightIcon()



        viewModel.getAppointmentsDetails(args.appointmentId)
        setObservers()
    }


    private fun setObservers() {

        observe(viewModel.getAppointmentsDetailsState)
        {
            when (it) {
                is Status.Loading -> {
                    baseActivity.showDialogLoading()
                }
                is Status.Success<*> -> {
                    baseActivity.hideDialogLoading()
                    val response = it.data as BaseResponse<AppointmentsData>
                    val model = response.data!!.appointment!![0]

                    binding.tvPatientNameTxt.text = model.patientName
                    binding.tvPatientMobileTxt.text = model.patientMobile
                    binding.tvPatientMobileTxt.setOnClickListener {
                        val dialIntent = Intent(Intent.ACTION_DIAL)
                        dialIntent.data = Uri.parse("tel:" + model.patientMobile)
                        startActivity(dialIntent)
                    }
                    binding.tvPatientIdTxt.text = "#${model.patientID}"

                    if(model.patientBirthDate != null ){
                        binding.tvPatientBirthDateTxt.text = CommonUtilities.convertFullDateToFormattedDate(model.patientBirthDate)
                    }
                    binding.tvPatientHeightTxt.text = "${model.height} ${getString(R.string.cm)}"
                    binding.tvPatientWeightTxt.text =  "${model.weight} ${getString(R.string.kg)}"
                    binding.tvServiceTypeTxt.text = model.consultationServiceName
                    binding.tvStatusTxt.text = model.statusName


                    if(model.bookingDate != null && model.startTime == null){
                        binding.tvDateTxt.text = CommonUtilities.convertFullDateToFormattedDateTxtWithoutTime(model.bookingDate)
                    }

                    else if (model.bookingDate == null && model.startTime != null){
                        binding.tvDateTxt.text = CommonUtilities.convertTimeToFormattedTimeTxt(model.startTime)
                    }
                   else if(model.bookingDate != null && model.startTime != null){
                        binding.tvDateTxt.text = CommonUtilities.convertFullDateToFormattedDateTxtWithoutTime(model.bookingDate) + "-" +CommonUtilities.convertTimeToFormattedTimeTxt(model.startTime)
                    }

                    if (model.duration == null){

                        binding.durationTxt.hide()
                        binding.tvDurationTxt.hide()
                    }else {
                        binding.tvDurationTxt.text = "${model.duration} ${getString(R.string.minutes)}"

                    }
                    binding.tvAmountTxt.text = "${model.bookingFees} ${getString(R.string.egp)}"

                    if (model.isPaied == true){
                        binding.tvPaymentStatusTxt.text = getString(R.string.paid)
                    }else{
                        binding.tvPaymentStatusTxt.text = getString(R.string.unpaid)
                    }

                    binding.tvPaymentMethodTxt.text = model.payMentMethodName

                    if(model.extraNumber == "" || model.extraNumber == null){
                        binding.extraPhoneNumberTxt.hide()
                        binding.tvExtraPhoneNumberTxt.hide()
                    }else{
                        binding.tvExtraPhoneNumberTxt.text = model.extraNumber
                        binding.tvExtraPhoneNumberTxt.setOnClickListener {
                            val dialIntent = Intent(Intent.ACTION_DIAL)
                            dialIntent.data = Uri.parse("tel:" + model.extraNumber)
                            startActivity(dialIntent)
                        }

                    }

                    binding.tvPatientProblemTxt.text = model.bookingReason





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
        return R.layout.fragment_appointment_details
    }

}