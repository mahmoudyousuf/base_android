package rubikans.rubik.doctor.ui.bottomSheets.ClinicFinanceFilter


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
import rubikans.rubik.doctor.databinding.BottomSheetClinicFinanceFilterBinding
import rubikans.rubik.doctor.model.*
import rubikans.rubik.doctor.ui.auth.AuthActivity
import rubikans.rubik.doctor.util.extensions.observe

import java.text.SimpleDateFormat
import java.util.*


private const val TAG = "ClinicFinanceFilterBottomSheet"

@AndroidEntryPoint
class ClinicFinanceFilterBottomSheet(
    val applyFilter: (insuranceCompanyID: String , insuranceCompanyName: String, applyFromDate: String, applyToDate: String ) -> Unit,
    val reset: () -> Unit,
    val typeID: String,
    val typeName: String,
    val selectedFromDate: String,
    val selectedToDate: String,
    val isIncome : Boolean ,

    ) : BaseBottomSheet<BottomSheetClinicFinanceFilterBinding>() {
    lateinit var binding: BottomSheetClinicFinanceFilterBinding
    lateinit var  fromDate: DatePickerDialog.OnDateSetListener
    private val myFromCalendar = Calendar.getInstance()

    val viewModel: ClinicFinanceFilterViewModel by viewModels()

    var id = ""
    var name = ""


    lateinit var  toDate: DatePickerDialog.OnDateSetListener
    private val myToCalendar = Calendar.getInstance()


    override fun getLayoutId(): Int {
        return R.layout.bottom_sheet_clinic_finance_filter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        id = typeID
        name = typeName




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



        if(isIncome){
            binding.chooseStatusTxt.text = getString(R.string.income_type)
            binding.typeDropDown.txtHint = getString(R.string.income_type)
        }else {
            binding.chooseStatusTxt.text = getString(R.string.expense_type)
            binding.typeDropDown.txtHint =  getString(R.string.expense_type)
        }
        binding.typeDropDown.setText(name)

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




        if(isIncome){
            viewModel.getClinicFinanceIncomeTypes()

        }else {
            viewModel.getClinicFinanceExpenseTypes()

        }
        setObservers()

    }


    private fun setObservers() {



        observe(viewModel.getClinicFinanceIncomeTypesState)
        {
            when (it) {
                is Status.Loading -> {
                    baseActivity.showDialogLoading()
                }
                is Status.Success<*> -> {
                    baseActivity.hideDialogLoading()
                    val response = it.data as BaseResponse<ClinicFinanceIncomeTypesData>


                    binding.typeDropDown.setOnDropDownClickListner {

                        val paymentMethodUp = PopupMenu(context, binding.typeDropDown)
                        for (i in 0 until response.data!!.incomeType!!.size) {

                            paymentMethodUp.menu.add(
                                i,
                                i,
                                i,
                                response.data!!.incomeType!![i].mainName
                            )

                        }
                        paymentMethodUp.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->


                            binding.typeDropDown.setText(response.data!!.incomeType!![item.itemId].mainName!!)

                            id =
                                response.data!!.incomeType!![item.itemId].incomeTypeID!!.toString()
                            name = response.data!!.incomeType!![item.itemId].mainName!!.toString()




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









        observe(viewModel.getClinicFinanceExpenseTypesState)
        {
            when (it) {
                is Status.Loading -> {
                    baseActivity.showDialogLoading()
                }
                is Status.Success<*> -> {
                    baseActivity.hideDialogLoading()
                    val response = it.data as BaseResponse<ClinicFinanceExpenseTypesData>


                    binding.typeDropDown.setOnDropDownClickListner {

                        val paymentMethodUp = PopupMenu(context, binding.typeDropDown)
                        for (i in 0 until response.data!!.expenseType!!.size) {

                            paymentMethodUp.menu.add(
                                i,
                                i,
                                i,
                                response.data!!.expenseType!![i].mainName
                            )

                        }
                        paymentMethodUp.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->


                            binding.typeDropDown.setText(response.data!!.expenseType!![item.itemId].mainName!!)

                            id =
                                response.data!!.expenseType!![item.itemId].expenseTypeID!!.toString()
                            name = response.data!!.expenseType!![item.itemId].mainName!!.toString()




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


