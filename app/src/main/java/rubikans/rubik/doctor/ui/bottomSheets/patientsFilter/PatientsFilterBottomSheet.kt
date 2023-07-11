package rubikans.rubik.doctor.ui.bottomSheets.patientsFilter


import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import com.etamn.util.Status
import dagger.hilt.android.AndroidEntryPoint
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.base.BaseBottomSheet
import rubikans.rubik.doctor.base.BaseResponse
import rubikans.rubik.doctor.databinding.BottomSheetPatientsFilterBinding
import rubikans.rubik.doctor.model.InsuranceCompanyData
import rubikans.rubik.doctor.model.InsuranceCompanyItem
import rubikans.rubik.doctor.ui.auth.AuthActivity
import rubikans.rubik.doctor.util.extensions.observe

import java.text.SimpleDateFormat
import java.util.*


private const val TAG = "PatientsFilterBottomSheet"

@AndroidEntryPoint
class PatientsFilterBottomSheet(
    val applyFilter: (insuranceCompanyID: String , insuranceCompanyName: String, applyFromDate: String, applyToDate: String ) -> Unit,
    val reset: () -> Unit,
    val insuranceCompanyID: String,
    val insuranceCompanyName: String,
    val selectedFromDate: String,
    val selectedToDate: String

    ) : BaseBottomSheet<BottomSheetPatientsFilterBinding>() {
    lateinit var binding: BottomSheetPatientsFilterBinding
    lateinit var  fromDate: DatePickerDialog.OnDateSetListener
    private val myFromCalendar = Calendar.getInstance()

    val viewModel: PatientsFilterViewModel by viewModels()

    var id = ""
    var name = ""


    lateinit var  toDate: DatePickerDialog.OnDateSetListener
    private val myToCalendar = Calendar.getInstance()


    override fun getLayoutId(): Int {
        return R.layout.bottom_sheet_patients_filter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        id = insuranceCompanyID
        name = insuranceCompanyName




        fromDate =
            DatePickerDialog.OnDateSetListener { view, year, month, day ->
                myFromCalendar.set(Calendar.YEAR, year)
                myFromCalendar.set(Calendar.MONTH, month)
                myFromCalendar.set(Calendar.DAY_OF_MONTH, day)
                updateFromDate()
            }


        toDate =
            DatePickerDialog.OnDateSetListener { view, year, month, day ->
                myToCalendar.set(Calendar.YEAR, year)
                myToCalendar.set(Calendar.MONTH, month)
                myToCalendar.set(Calendar.DAY_OF_MONTH, day)
                updateToDate()
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding!!



        binding.insuranceCompanyDropDown.setText(name)

        binding.closeBtn.setOnClickListener {
            dismiss()
            reset()
        }

        binding.cancelBtn.setOnClickListener {
            dismiss()
        }




        binding.fromDate.setText(selectedFromDate)
        binding.toDate.setText(selectedToDate)







        binding.fromDate.setLeftIcon(R.drawable.ic_calender)
        binding.fromDate.setFocusable()

        binding.toDate.setLeftIcon(R.drawable.ic_calender)
        binding.toDate.setFocusable()


        binding.fromDate.setOnClickListenerEditText {
            binding.fromDate.showDate {
                DatePickerDialog(
                    baseActivity,
                    fromDate,
                    myFromCalendar[Calendar.YEAR],
                    myFromCalendar[Calendar.MONTH],
                    myFromCalendar[Calendar.DAY_OF_MONTH]
                ).show()
            }
        }


        binding.toDate.setOnClickListenerEditText {
            binding.toDate.showDate {
                DatePickerDialog(
                    baseActivity,
                    toDate,
                    myToCalendar[Calendar.YEAR],
                    myToCalendar[Calendar.MONTH],
                    myToCalendar[Calendar.DAY_OF_MONTH]
                ).show()
            }
        }






        binding.applyBtn.setOnClickListener {
            dismiss()
            applyFilter(
                id,
                name,
                binding.fromDate.getText(),
                binding.toDate.getText(),

            )
        }



        viewModel.getInsuranceCompanies()
        setObservers()

    }


    private fun setObservers() {



        observe(viewModel.getInsuranceCompaniesState)
        {
            when (it) {
                is Status.Loading -> {
                    baseActivity.showDialogLoading()
                }
                is Status.Success<*> -> {
                    baseActivity.hideDialogLoading()
                    val response = it.data as BaseResponse<InsuranceCompanyData>


                    binding.insuranceCompanyDropDown.setOnDropDownClickListner {

                        val paymentMethodUp = PopupMenu(context, binding.insuranceCompanyDropDown)
                        for (i in 0 until response.data!!.insuranceCompany!!.size) {

                            paymentMethodUp.menu.add(
                                i,
                                i,
                                i,
                                response.data!!.insuranceCompany!![i].mainName
                            )

                        }
                        paymentMethodUp.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->


                            binding.insuranceCompanyDropDown.setText(response.data!!.insuranceCompany!![item.itemId].mainName!!)

                            id =
                                response.data!!.insuranceCompany!![item.itemId].insuranceCompanyID!!.toString()
                            name = response.data!!.insuranceCompany!![item.itemId].mainName!!.toString()




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





    }



    private fun updateFromDate() {
        val myFormat = "YYYY-MM-dd"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        binding.fromDate.setText(dateFormat.format(myFromCalendar.time))
    }

    private fun updateToDate() {
        val myFormat = "YYYY-MM-dd"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        binding.toDate.setText(dateFormat.format(myToCalendar.time))
    }

}


