package rubikans.rubik.doctor.ui.bottomSheets.appointmentsFilter


import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.bottom_sheet_appointments_filter.*
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.base.BaseBottomSheet
import rubikans.rubik.doctor.databinding.BottomSheetAppointmentsFilterBinding
import rubikans.rubik.doctor.util.extensions.hide
import rubikans.rubik.doctor.util.extensions.visible
import java.text.SimpleDateFormat
import java.util.*


private const val TAG = "AppointmentsFilterBottomSheet"

@AndroidEntryPoint
class AppointmentsFilterBottomSheet(
    val applyFilter: ( statusId: String, applyFromDate: String, applyToDate: String ) -> Unit,
    val reset: () -> Unit,
    val statusId: String,
    val selectedFromDate: String,
    val selectedToDate: String

    ) : BaseBottomSheet<BottomSheetAppointmentsFilterBinding>() {
    lateinit var binding: BottomSheetAppointmentsFilterBinding
    lateinit var  fromDate: DatePickerDialog.OnDateSetListener
    private val myFromCalendar = Calendar.getInstance()


    var id = ""
    var name = ""



    lateinit var  toDate: DatePickerDialog.OnDateSetListener
    private val myToCalendar = Calendar.getInstance()


    override fun getLayoutId(): Int {
        return R.layout.bottom_sheet_appointments_filter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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


        binding.closeBtn.setOnClickListener {
            dismiss()
            reset()
        }

        binding.cancelBtn.setOnClickListener {
            dismiss()
        }




        binding.fromDate.setText(selectedFromDate)
        binding.toDate.setText(selectedToDate)





        when (statusId) {

            // pending
            "1" -> {
                binding.pendingRB.isChecked = true


            }
            // confirmed
            "2 "-> {
                binding.confirmedRB.isChecked = true

            }


            // on service
            "3" -> {

                binding.onServiceRB.isChecked = true

            }


            // done
            "4" -> {

                binding.doneRB.isChecked = true

            }

            //canceled
            "5" -> {
                binding.canceledRB.isChecked = true

            }

            //no Show
            "6" -> {
                binding.noShowRB.isChecked = true

            }

            else -> {

            }
        }

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



        binding.pendingRB.setOnClickListener {
            name = binding.pendingRB.text.toString()
            id = "1"
        }
        binding.confirmedRB.setOnClickListener {

            name = binding.confirmedRB.text.toString()
            id = "2"

        }
        binding.onServiceRB.setOnClickListener {
            name = binding.onServiceRB.text.toString()
            id = "3"
        }
        binding.doneRB.setOnClickListener {
            name = binding.doneRB.text.toString()
            id = "4"
        }
        binding.canceledRB.setOnClickListener {
            name = binding.canceledRB.text.toString()
            id = "5"
        }

        binding.noShowRB.setOnClickListener {
            name = binding.noShowRB.text.toString()
            id = "6"
        }


        binding.applyBtn.setOnClickListener {
            dismiss()
            applyFilter(
                id,
                binding.fromDate.getText(),
                binding.toDate.getText(),

            )
        }


    }


    private fun updateFromDate() {
        val myFormat = "YYYY/MM/dd"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        binding.fromDate.setText(dateFormat.format(myFromCalendar.time))
    }

    private fun updateToDate() {
        val myFormat = "YYYY/MM/dd"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        binding.toDate.setText(dateFormat.format(myToCalendar.time))
    }

}


