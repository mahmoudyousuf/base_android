package rubikans.rubik.doctor.ui.main.home

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.databinding.ItemAppointmentBinding
import rubikans.rubik.doctor.model.AppointmentItem
import rubikans.rubik.doctor.util.CommonUtilities
import rubikans.rubik.doctor.util.extensions.hide
import rubikans.rubik.doctor.util.extensions.visible


class AppointmentsAdapter(
    private val context: Context,
    private val onItemClicked: (model: AppointmentItem) -> Unit,
    private val onConfirmClicked: (model: AppointmentItem) -> Unit,
    private val onCancelClicked: (model: AppointmentItem) -> Unit,
    private val onNoShowClicked: (model: AppointmentItem) -> Unit,
    private val onDoneClicked: (model: AppointmentItem) -> Unit,
    private val onOnServiceClicked: (model: AppointmentItem) -> Unit,

    ) : PagingDataAdapter<AppointmentItem, AppointmentsAdapter.ViewHolder>(AppointmentItem.CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemAppointmentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }




    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = getItem(position)!!
        holder.bind(model)

    }


    inner class ViewHolder(var binding: ItemAppointmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(model: AppointmentItem) {
            binding.tvNameTxt.text = model.patientName
            binding.tvIdTxt.text = "#${model.bookingNo.toString()}"


            if (model.isPaied == true){
                binding.tvPaymentStatusTxt.text = context.getString(R.string.paid)
            }else{
                binding.tvPaymentStatusTxt.text = context.getString(R.string.unpaid)
            }

//            binding.tvPaymentStatusTxt.text = model.payMentMethodName


            if(model.bookingDate != null && model.startTime == null){
                binding.tvDateTxt.text = CommonUtilities.convertFullDateToFormattedDateTxtWithoutTime(model.bookingDate)
            }

            else if (model.bookingDate == null && model.startTime != null){
                binding.tvDateTxt.text = CommonUtilities.convertTimeToFormattedTimeTxt(model.startTime)
            }
            else if(model.bookingDate != null && model.startTime != null){
                binding.tvDateTxt.text = CommonUtilities.convertFullDateToFormattedDateTxtWithoutTime(model.bookingDate) + "-" +CommonUtilities.convertTimeToFormattedTimeTxt(model.startTime)
            }


            when (model.bookingStatus) {

                // pending
                1 -> {
                    binding.statusLayout.background = ContextCompat.getDrawable(context, R.drawable.rounded_pending_container)
                    binding.tvVisitStatusTxt.setTextColor(context.getColor(R.color.rGrayColor))
                    binding.tvVisitStatusTxt.text = model.statusName
                    binding.confirmLayout.visible()
                    binding.cancelLayout.visible()
                    binding.noShowLayout.visible()


                    binding.doneLayout.hide()
                    binding.onServiceLayout.hide()

//                    binding.tvVisitStatusTxt.text = context.getString(R.string.completed)

                }
                // confirmed
                2 -> {
                    binding.statusLayout.background = ContextCompat.getDrawable(context, R.drawable.rounded_confirmed_container)
                    binding.tvVisitStatusTxt.setTextColor(context.getColor(R.color.rGreenColor))
                    binding.tvVisitStatusTxt.text = model.statusName
                    binding.onServiceLayout.visible()
                    binding.cancelLayout.visible()
                    binding.noShowLayout.hide()
                    binding.confirmLayout.hide()
                    binding.doneLayout.hide()
                }


                // on service
                3 -> {
                    binding.statusLayout.background = ContextCompat.getDrawable(context, R.drawable.rounded_on_service_container)
                    binding.tvVisitStatusTxt.setTextColor(context.getColor(R.color.rBlueColor))
                    binding.tvVisitStatusTxt.text = model.statusName
                    binding.doneLayout.visible()


                    binding.confirmLayout.hide()
                    binding.cancelLayout.hide()
                    binding.noShowLayout.hide()
                    binding.onServiceLayout.hide()

                }


                // done
                4 -> {
                    binding.statusLayout.background = ContextCompat.getDrawable(context, R.drawable.rounded_done_container)
                    binding.tvVisitStatusTxt.setTextColor(context.getColor(R.color.rDarkBlueColor))
                    binding.tvVisitStatusTxt.text = model.statusName


                    binding.doneLayout.hide()
                    binding.confirmLayout.hide()
                    binding.cancelLayout.hide()
                    binding.noShowLayout.hide()
                    binding.onServiceLayout.hide()


                }

                //canceled
                5 -> {
                    binding.statusLayout.background = ContextCompat.getDrawable(context, R.drawable.rounded_cancel_container)
                    binding.tvVisitStatusTxt.setTextColor(context.getColor(R.color.rReedColor))
                    binding.tvVisitStatusTxt.text = model.statusName

                    binding.doneLayout.hide()
                    binding.confirmLayout.hide()
                    binding.cancelLayout.hide()
                    binding.noShowLayout.hide()
                    binding.onServiceLayout.hide()
                }


                //no Show
                6 -> {
                    binding.statusLayout.background = ContextCompat.getDrawable(context, R.drawable.rounded_no_show_container)
                    binding.tvVisitStatusTxt.setTextColor(context.getColor(R.color.rnoShowColor))
                    binding.tvVisitStatusTxt.text = model.statusName


                    binding.doneLayout.hide()
                    binding.confirmLayout.hide()
                    binding.cancelLayout.hide()
                    binding.noShowLayout.hide()
                    binding.onServiceLayout.hide()
                }

                else -> {
                    binding.tvVisitStatusTxt.text = model.statusName

                    binding.doneLayout.hide()
                    binding.confirmLayout.hide()
                    binding.cancelLayout.hide()
                    binding.noShowLayout.hide()
                    binding.onServiceLayout.hide()
                }
            }

            itemView.setOnClickListener{
                onItemClicked(model)
            }

            binding.confirmLayout.setOnClickListener {
                onConfirmClicked(model)
            }

            binding.cancelLayout.setOnClickListener {
                onCancelClicked(model)
            }

            binding.noShowLayout.setOnClickListener {
                onNoShowClicked(model)
            }

            binding.doneLayout.setOnClickListener {
                onDoneClicked(model)
            }

            binding.onServiceLayout.setOnClickListener {
                onOnServiceClicked(model)
            }



        }



    }


}