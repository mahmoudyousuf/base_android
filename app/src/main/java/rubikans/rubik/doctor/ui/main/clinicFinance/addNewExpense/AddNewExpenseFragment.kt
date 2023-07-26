package rubikans.rubik.doctor.ui.main.clinicFinance.addNewExpense

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.etamn.util.Status
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.base.BaseFragment
import rubikans.rubik.doctor.base.BaseResponse
import rubikans.rubik.doctor.databinding.FragmentAddNewExpenseBinding
import rubikans.rubik.doctor.model.ClinicExpenseDataItem
import rubikans.rubik.doctor.model.ClinicFinanceExpenseTypesData
import rubikans.rubik.doctor.ui.auth.AuthActivity
import rubikans.rubik.doctor.util.CommonUtilities
import rubikans.rubik.doctor.util.extensions.observe
import rubikans.rubik.doctor.util.extensions.toObjectFromJson
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*


@AndroidEntryPoint
class AddNewExpenseFragment : BaseFragment<FragmentAddNewExpenseBinding>() {
    lateinit var binding: FragmentAddNewExpenseBinding
    val viewModel: AddNewExpenseViewModel by viewModels()
    var expenseTypeId = 0

    private val myCalendar = Calendar.getInstance()
    private val myTimeCalendar = Calendar.getInstance()
    lateinit var date: DatePickerDialog.OnDateSetListener
    lateinit var time: TimePickerDialog.OnTimeSetListener

    val currentDate = LocalDateTime.now()



    private val args by navArgs<AddNewExpenseFragmentArgs>()
    lateinit var clinicExpenseDataItem: ClinicExpenseDataItem


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(args.isEdit == "true"){
            clinicExpenseDataItem =
                args.expenseModel.toObjectFromJson<ClinicExpenseDataItem>(ClinicExpenseDataItem::class.java)
        }

        time =
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                myTimeCalendar[Calendar.HOUR_OF_DAY] = hourOfDay
                myTimeCalendar[Calendar.MINUTE] = minute
                updateTimeLabel()
            }


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




        if(args.isEdit == "true"){
            binding.expenseTypeDropDown.setText(clinicExpenseDataItem.expenseTypeName!!)
            binding.dateEdt.setText(CommonUtilities.convertFullDateToFormattedDatex(clinicExpenseDataItem.createDate)!!)
            binding.timeEdt.setText(CommonUtilities.convertFullDateToFormattedDateToTime(clinicExpenseDataItem.createDate)!!)
            binding.amountEdt.setText(clinicExpenseDataItem.fees.toString()!!)
            binding.noteEdt.setText(clinicExpenseDataItem.notes)
            expenseTypeId = clinicExpenseDataItem.expenseTypeID!!

        }else{

            binding.dateEdt.setText(CommonUtilities.convertFullDateToFormattedDatex(currentDate.toString())!!)
            binding.timeEdt.setText(CommonUtilities.convertFullDateToFormattedDateToTime(currentDate.toString())!!)
        }


//        CommonUtilities.convertTimeAndDateToRFullDateFormat(binding.dateEdt.getText() + "-" + binding.timeEdt.getText()!!)
//            ?.let { binding.amountEdt.setText(it) }


        binding.dateEdt.setLeftIcon(R.drawable.ic_calender)
        binding.dateEdt.setFocusable()
        binding.dateEdt.setOnClickListenerEditText {

            binding.dateEdt.removeError()
            binding.dateEdt.showDate {
               var myDatePicker =  DatePickerDialog(
                    baseActivity,
                    date,
                    myCalendar[Calendar.YEAR],
                    myCalendar[Calendar.MONTH],
                    myCalendar[Calendar.DAY_OF_MONTH],

                )

                myDatePicker.datePicker.maxDate = System.currentTimeMillis();

                myDatePicker.show()
            }
        }



        binding.timeEdt.setLeftIcon(R.drawable.ic_calender)
        binding.timeEdt.setFocusable()
        binding.timeEdt.setOnClickListenerEditText {

            binding.timeEdt.removeError()
            binding.timeEdt.showDate {

                TimePickerDialog(
                    baseActivity,
                    time,
                    myTimeCalendar[Calendar.HOUR_OF_DAY],
                    myTimeCalendar[Calendar.MINUTE],
                    false
                ).show()




            }
        }











        binding.confirmBtn.setOnClickListener {



            if (binding.expenseTypeDropDown.getText().isEmpty()) {
                binding.expenseTypeDropDown.setError(getString(R.string.expense_type_is_required))
                return@setOnClickListener
            }


            if (binding.dateEdt.getText().isEmpty()) {
                binding.dateEdt.setError(getString(R.string.date_is_required))
                return@setOnClickListener
            }

            if (binding.timeEdt.getText().isEmpty()) {
                binding.timeEdt.setError(getString(R.string.time_is_required))
                return@setOnClickListener
            }

            if (binding.amountEdt.getText().isEmpty()) {
                binding.amountEdt.setError(getString(R.string.fees_is_required))
                return@setOnClickListener
            }








            val props = JSONObject()

            val date12Format = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
            val date24Format = SimpleDateFormat("HH:mm:ss")
            val time = date24Format.format(date12Format.parse(binding.timeEdt.getText())!!)


            if(args.isEdit == "true"){

                props.put("ClinicExpenseID", clinicExpenseDataItem.clinicExpenseID.toString())

            }

                props.put("ExpenseTypeID", expenseTypeId.toString())
                props.put("CreateDate", CommonUtilities.convertTimeAndDateToRFullDateFormat (binding.dateEdt.getText() + "-" + time ))
                props.put("EntityBranchID", baseActivity.dataManager.clinic!!.entityBranchID.toString())
                props.put("Fees", binding.amountEdt.getText())
                props.put("Notes", binding.noteEdt.text)









            viewModel.addNewExpense(jsonParser.parse(props.toString()) as JsonObject)

        }






        viewModel.getClinicFinanceExpenseTypes()
        setObservers()

    }

    private fun setObservers() {






        observe(viewModel.addNewExpenseState)
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



        observe(viewModel.getClinicFinanceExpenseTypesState)
        {
            when (it) {
                is Status.Loading -> {
                    baseActivity.showDialogLoading()
                }
                is Status.Success<*> -> {
                    baseActivity.hideDialogLoading()
                    val response = it.data as BaseResponse<ClinicFinanceExpenseTypesData>


                    binding.expenseTypeDropDown.setOnDropDownClickListner {

                        val paymentMethodUp = PopupMenu(context, binding.expenseTypeDropDown)
                        for (i in 0 until response.data!!.expenseType!!.size) {

                            paymentMethodUp.menu.add(
                                i,
                                i,
                                i,
                                response.data!!.expenseType!![i].mainName
                            )

                        }
                        paymentMethodUp.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->


                            binding.expenseTypeDropDown.setText(response.data!!.expenseType!![item.itemId].mainName!!)

                            expenseTypeId = response.data!!.expenseType!![item.itemId].expenseTypeID!!




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


    private fun updateLabel() {
        val myFormat = "yyyy-MM-dd"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        binding.dateEdt.setText(dateFormat.format(myCalendar.time))
    }


    private fun updateTimeLabel() {
        val myFormat = "hh:mm a"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)

//        val timeComeFromServer = "3:30 PM"
//        val date12Format = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
//        val date24Format = SimpleDateFormat("HH:mm")
//        baseActivity.showWarningSnackbar(date24Format.format(date12Format.parse(timeComeFromServer)!!))

//        val cmp = dateFormat.format(currentDate).compareTo(dateFormat.format(myTimeCalendar.time))
//        baseActivity.showWarningSnackbar(cmp.toString())

        binding.timeEdt.setText(dateFormat.format(myTimeCalendar.time))
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_add_new_expense
    }

}