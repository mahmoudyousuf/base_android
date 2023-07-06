package rubikans.rubik.doctor.ui.main.makeAppointment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.databinding.ItemTimeBinding
import rubikans.rubik.doctor.model.AppointmentsTimesModelItem
import rubikans.rubik.doctor.util.extensions.hide
import rubikans.rubik.doctor.util.extensions.visible



// just for test
//data class TestTime(
//    var id: Int,
//    var time: String,
//    var status: String
//)

class TimeAdapter(
    private val context: Context,
    private var times: ArrayList<AppointmentsTimesModelItem>,
    var onItemSelected :(model: AppointmentsTimesModelItem, position: Int)->Unit
) : RecyclerView.Adapter<TimeAdapter.ViewHolder>() {
    private var selectedItem = -1



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemTimeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setMyData(myData: ArrayList<AppointmentsTimesModelItem>) {
        this.times = myData
        notifyDataSetChanged()
    }


    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = times[position]
        holder.bind(model, position)

    }

    override fun getItemCount(): Int {
        return times.size
    }
    override fun getItemViewType(position: Int): Int {
        return position
    }
    @SuppressLint("NotifyDataSetChanged")
    fun changeSelectedItem(selectedItem: Int) {
        this.selectedItem = selectedItem
        notifyDataSetChanged()
    }

    inner class ViewHolder(var binding: ItemTimeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint(
            "SuspiciousIndentation", "SetTextI18n", "NotifyDataSetChanged",
            "ResourceAsColor"
        )
        fun bind(model: AppointmentsTimesModelItem, position: Int) {

            binding.timeTv.text = model.startTime

//            if(model.timeAvailable == false){
//                binding.statusTv.hide()
//            }

            if(model.isAvaliable == false){
                binding.statusTv.text = context.getString(R.string.booked)
                binding.statusTv.alpha = .5f
                binding.statusTv.visible()
                itemView.background =
                    ContextCompat.getDrawable(context, R.drawable.unavailable_time_border)
            }

            else{
                binding.statusTv.hide()
                if (position == selectedItem) {
                    itemView.background =
                        ContextCompat.getDrawable(context, R.drawable.select_time_border)
                    binding.timeTv.setTextColor(ContextCompat.getColor(context, R.color.white))
                    binding.statusTv.setTextColor(ContextCompat.getColor(context, R.color.txt_blue_color))
                    binding.statusTv.alpha = .5f

                } else {
                    itemView.background =
                        ContextCompat.getDrawable(context, R.drawable.unselect_time_border)
                    binding.timeTv.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.date_txt_unselect_color
                        )
                    )
                    binding.statusTv.setTextColor(ContextCompat.getColor(context, R.color.txt_blue_color))
                    binding.statusTv.alpha = .5f

                }
            }



            itemView.setOnClickListener {
                if(model.isAvaliable == false){
                    return@setOnClickListener
                }
                if (position != RecyclerView.NO_POSITION) {
                    selectedItem = position
                    notifyDataSetChanged()
                }
                onItemSelected(model, position)

            }


        }
    }

}