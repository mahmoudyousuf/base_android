package com.example.carefertask.ui.main

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.carefertask.R
import com.example.carefertask.databinding.HeaderItemBinding
import com.example.carefertask.databinding.MatchItemBinding
import com.example.carefertask.model.HeaderItem
import com.example.carefertask.model.ListItem
import com.example.carefertask.model.MatchItem
import com.example.carefertask.util.extensions.convertToTime
import java.util.*


class MainGroupingAdapter(
    private var myData: ArrayList<ListItem>,
    val addFave: (id: Int) -> Unit
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
            PlanViewHolder(
                MatchItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )
        }
    }


    override fun getItemViewType(position: Int): Int {
        return myData[position].getType()
    }

    fun getMyData(): ArrayList<ListItem> {
        return this.myData
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
            val model = myData[position] as MatchItem
            val holder: PlanViewHolder = viewHolder as PlanViewHolder
            holder.onBind(model, position)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class HeaderViewHolder(var binding: HeaderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(model: HeaderItem) {
            binding.day.text =
                "${model.date.dayOfWeek.name.lowercase()}, ${model.date.dayOfMonth} ${
                    model.date.month.toString().lowercase()
                }"
        }

    }

    inner class PlanViewHolder(var binding: MatchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(model: MatchItem, position: Int) {

            if (model.matchItem.fave) {
                binding.fave.setImageResource(R.drawable.baseline_star_rate_24)
            } else {
                binding.fave.setImageResource(R.drawable.baseline_star_outline_24)
            }

            binding.fave.setOnClickListener {

                val m = myData[position] as MatchItem
                m.matchItem.fave = true
                myData.removeAt(position)
                myData.add(position, m)
                notifyDataSetChanged()

                addFave(model.matchItem.id!!)
            }

            binding.homeTeam.text = model.matchItem.homeTeam?.name
            binding.awayTeam.text = model.matchItem.awayTeam?.name
            binding.time.text = model.matchItem.utcDate?.take(16)?.convertToTime()
        }

    }


}