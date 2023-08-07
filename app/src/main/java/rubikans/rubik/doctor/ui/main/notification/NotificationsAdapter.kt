package rubikans.rubik.doctor.ui.main.notification

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.databinding.ItemNotificationBinding

import rubikans.rubik.doctor.model.NotificationsDataItem
import rubikans.rubik.doctor.util.CommonUtilities
import rubikans.rubik.doctor.util.extensions.hide
import rubikans.rubik.doctor.util.extensions.visible


class NotificationsAdapter(
    private val context: Context,
    private var addedList: ArrayList<NotificationsDataItem>,
    var onItemRead :(model: NotificationsDataItem)->Unit,
    var onItemClicked :(model: NotificationsDataItem)->Unit,
) : RecyclerView.Adapter<NotificationsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemNotificationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setMyData(myData: ArrayList<NotificationsDataItem>) {
        this.addedList = myData
        notifyDataSetChanged()
    }


    @SuppressLint("NotifyDataSetChanged")
    fun search(newList: ArrayList<NotificationsDataItem>) {
        addedList = ArrayList()
        addedList.addAll(newList)
        notifyDataSetChanged()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = addedList[position]
        holder.bind(model)

    }

    override fun getItemCount(): Int {
        return addedList.size
    }

    inner class ViewHolder(var binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SuspiciousIndentation", "SetTextI18n")
        fun bind(model: NotificationsDataItem) {

            binding.notificationText.text = model.notificationMessage
            binding.notificationTime.text = CommonUtilities.convertFullDateToFormattedDateTxt(model.notificationDate)
            if (model.isRead!!){
                binding.statusCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.read_notification_status_color))
                binding.notificationLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.read_notification_backGround_color))
                binding.readNotificationBt.hide()
            }else{
                binding.statusCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.unRead_notification_status_color))
                binding.notificationLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.unRead_notification_backGround_color))
                binding.readNotificationBt.visible()
            }
            binding.readNotificationBt.setOnClickListener {
                onItemRead(model)
            }

            itemView.setOnClickListener{
                onItemClicked(model)
            }

        }
    }

}