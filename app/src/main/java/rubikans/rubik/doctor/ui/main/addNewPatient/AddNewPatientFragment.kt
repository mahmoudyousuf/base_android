package rubikans.rubik.doctor.ui.main.addNewPatient

import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import androidx.core.view.isNotEmpty
import androidx.fragment.app.viewModels
import com.etamn.util.Status
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.RequestBody
import org.json.JSONObject
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.base.BaseFragment
import rubikans.rubik.doctor.base.BaseResponse
import rubikans.rubik.doctor.databinding.FragmentAddNewPatientBinding
import rubikans.rubik.doctor.model.InsuranceCompanyData
import rubikans.rubik.doctor.model.PaymentMethodsModel
import rubikans.rubik.doctor.ui.auth.AuthActivity
import rubikans.rubik.doctor.ui.bottomSheets.selectInsuranceCompany.SelectInsuranceCompanyBottomSheet
import rubikans.rubik.doctor.ui.bottomSheets.selectPatient.SelectPatientBottomSheet
import rubikans.rubik.doctor.util.extensions.observe
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


@AndroidEntryPoint
class AddNewPatientFragment : BaseFragment<FragmentAddNewPatientBinding>() {
    lateinit var binding: FragmentAddNewPatientBinding
    val viewModel: AddNewPatientViewModel by viewModels()
    var insuranceCopmanyId = 0

    private val myCalendar = Calendar.getInstance()
    lateinit var date: DatePickerDialog.OnDateSetListener

    private var gender: Int = 0
    var gendersList = ArrayList<String>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gendersList.add(getString(R.string.male))
        gendersList.add(getString(R.string.female))
        date =
            DatePickerDialog.OnDateSetListener { view, year, month, day ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, day)
                updateLabel()
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = viewDataBinding!!

        binding.customBar.leftImage = R.drawable.ic_back
        binding.customBar.leftCardView.setOnClickListener {
            navController.navigateUp()
        }
        binding.customBar.hideRightIcon()




        binding.birthDateEdt.setLeftIcon(R.drawable.ic_calender)
        binding.birthDateEdt.setFocusable()
        binding.birthDateEdt.setOnClickListenerEditText {

            binding.birthDateEdt.removeError()
            binding.birthDateEdt.showDate {
                DatePickerDialog(
                    baseActivity,
                    date,
                    myCalendar[Calendar.YEAR],
                    myCalendar[Calendar.MONTH],
                    myCalendar[Calendar.DAY_OF_MONTH]
                ).show()
            }
        }





        binding.insuranceCompanyDropDown.setOnDropDownClickListner {

            val bottomSheet = SelectInsuranceCompanyBottomSheet(viewModel)
            bottomSheet.show(baseActivity.supportFragmentManager, "SelectInsuranceCompanyBottomSheet")
        }




        binding.genderDropDown.setOnDropDownClickListner {

            val allergiesPopUp = PopupMenu(context, binding.genderDropDown)
            for (i in 0 until gendersList.size) {

                allergiesPopUp.menu.add(i,
                    i,
                    i,
                    gendersList[i])

            }
            allergiesPopUp.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->


                binding.genderDropDown.setText(gendersList[item.itemId])


                if (gendersList[item.itemId] == getString(R.string.male)) {
                    gender = 1
                } else {
                    gender = 2
                }


                return@OnMenuItemClickListener false
            })
            allergiesPopUp.show()

        }





        binding.confirmBtn.setOnClickListener {


            if (binding.patientNameEdt.getText().isEmpty()) {
                binding.patientNameEdt.setError(getString(R.string.patient_name_is_required))
                return@setOnClickListener
            }

            if (binding.patientMobileEdt.getText().isEmpty()) {
                binding.patientMobileEdt.setError(getString(R.string.mobile_are_required))
                return@setOnClickListener
            }


            if (!Pattern.compile("[0-9]{10,11}").matcher(binding.patientMobileEdt.getText())
                    .matches()
            ){
                binding.patientMobileEdt.setError(getString(R.string.phone_number_digits_should_be_from_10_to_11_numbers))
                return@setOnClickListener
            }

            if (binding.emailEdt.getText().isNotEmpty()) {

                if (!Pattern.compile(
                        "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
                    ).matcher(binding.emailEdt.getText()).matches()
                ) {
                    binding.emailEdt.setError(getString(R.string.enter_valid_email_format))
                    return@setOnClickListener
                }
            }




            val props = JSONObject()


                props.put("Created_in", baseActivity.dataManager.clinic!!.entityBranchID.toString())
                props.put("FullName", binding.patientNameEdt.getText())
                props.put("mobile", binding.patientMobileEdt.getText())

            if(binding.emailEdt.isNotEmpty()){
                props.put("email", binding.emailEdt.getText())

            }

            if(gender != 0){
                props.put("Gender", gender)

            }
                props.put("PhoneCode","+2")
                props.put("Bussiness_fk", "5")

            if(binding.birthDateEdt.isNotEmpty()){
                props.put("Dateofbirth", binding.birthDateEdt.getText())

            }

            if(insuranceCopmanyId != 0){
                props.put("InsuranceCompanyID", insuranceCopmanyId)

            }

            viewModel.addNewPatient(jsonParser.parse(props.toString()) as JsonObject)

        }






        setObservers()

    }

    private fun setObservers() {


        observe(viewModel.insuranceCompanyModel)
        {

            if (viewModel.insuranceCompanyModel.value!!.mainName != null) {
                binding.insuranceCompanyDropDown.setText(viewModel.insuranceCompanyModel.value!!.mainName!!)
                insuranceCopmanyId = viewModel.insuranceCompanyModel.value!!.insuranceCompanyID!!
            }

        }



        observe(viewModel.addNewPatientState)
        {
            when (it) {
                is Status.Loading -> {
                    baseActivity.showDialogLoading()
                }
                is Status.Success<*> -> {
                    baseActivity.hideDialogLoading()
                    val response = it.data as BaseResponse<BaseResponse.EmptyData>
                    baseActivity.showSuccessSnackbar(response.message.toString())
                    navController.navigateUp()

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

//
//        observe(viewModel.getInsuranceCompaniesState)
//        {
//            when (it) {
//                is Status.Loading -> {
//                    baseActivity.showDialogLoading()
//                }
//                is Status.Success<*> -> {
//                    baseActivity.hideDialogLoading()
//                    val response = it.data as BaseResponse<InsuranceCompanyData>
//
//
////                    binding.insuranceCompanyDropDown.setOnDropDownClickListner {
////
////                        val paymentMethodUp = PopupMenu(context, binding.insuranceCompanyDropDown)
////                        for (i in 0 until response.data!!.insuranceCompany!!.size) {
////
////                            paymentMethodUp.menu.add(
////                                i,
////                                i,
////                                i,
////                                response.data!!.insuranceCompany!![i].mainName
////                            )
////
////                        }
////                        paymentMethodUp.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
////
////
////                            binding.insuranceCompanyDropDown.setText(response.data!!.insuranceCompany!![item.itemId].mainName!!)
////
////                            insuranceCopmanyId =
////                                response.data!!.insuranceCompany!![item.itemId].insuranceCompanyID!!
////
////
////
////
////                            return@OnMenuItemClickListener false
////                        })
////                        paymentMethodUp.show()
////
////                    }
//                }
//                is Status.Error -> {
//                    baseActivity.hideDialogLoading()
//                    baseActivity.showWarningSnackbar(it.message!!)
//
//                }
//                is Status.Unauthorized -> {
//
//                    baseActivity.showWarningSnackbar(getString(R.string.please_login))
//
//                    val i = Intent(baseActivity, AuthActivity::class.java)
//                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                    startActivity(i)
//                    baseActivity.finish()
//
//                }
//            }
//        }





    }


    private fun updateLabel() {
        val myFormat = "YYYY-MM-dd"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        binding.birthDateEdt.setText(dateFormat.format(myCalendar.time))
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_add_new_patient
    }

}