package rubikans.rubik.doctor.ui.main.notification.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.databinding.HeaderItemBinding
import rubikans.rubik.doctor.databinding.ItemNotificationBinding
import rubikans.rubik.doctor.util.extensions.hide
import rubikans.rubik.doctor.util.extensions.visible

import java.util.*


class NotificationGroupingAdapter(
    private val context: Context,
    private var myData: ArrayList<ListItem>,
    var read:()->Unit,
 ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType === ListItem.TYPE_HEADER) {
            HeaderViewHolder(
                HeaderItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )
        } else {
            NotificationViewHolder(
                ItemNotificationBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )
        }


        }


    override fun getItemViewType(position: Int): Int {
        return myData[position].getType()
    }

    fun setMyData(myData: ArrayList<ListItem>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val type = getItemViewType(position)
        if (type == ListItem.TYPE_HEADER) {
            val model = myData[position] as HeaderItem
            val holder = viewHolder as HeaderViewHolder
            holder.onBind(model)
        } else {
            val model = myData[position] as NotificationItem
            val holder: NotificationViewHolder = viewHolder as NotificationViewHolder
            holder.onBind(model)
        }





    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class HeaderViewHolder(var binding: HeaderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(model: HeaderItem) {
            binding.history.text = model.date
        }

    }

    inner class NotificationViewHolder(var binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(model: NotificationItem) {

            binding.notificationText.text = model.notificationItem.text
            binding.notificationTime.text = model.notificationItem.time
           if (model.notificationItem.isRead){
               binding.statusCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.read_notification_status_color))
               binding.notificationLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.read_notification_backGround_color))
               binding.readNotificationBt.hide()
           }else{
               binding.statusCard.setCardBackgroundColor(ContextCompat.getColor(context,R.color.unRead_notification_status_color))
               binding.notificationLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.unRead_notification_backGround_color))
               binding.readNotificationBt.visible()
           }
            binding.readNotificationBt.setOnClickListener {
                read()
            }




        }

    }




}