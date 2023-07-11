package rubikans.rubik.doctor.ui.main.clinicFinance

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.databinding.ItemExpensesBinding
import rubikans.rubik.doctor.model.ClinicExpenseDataItem
import rubikans.rubik.doctor.util.CommonUtilities



class ClinicExpenseAdapter(
    private val context: Context,
    private val onItemClicked: (model: ClinicExpenseDataItem) -> Unit,


    ) : PagingDataAdapter<ClinicExpenseDataItem, ClinicExpenseAdapter.ViewHolder>(ClinicExpenseDataItem.CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemExpensesBinding.inflate(
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


    inner class ViewHolder(var binding: ItemExpensesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(model: ClinicExpenseDataItem) {

            binding.tvExpenseTypeTxt.text = model.expenseTypeName
            binding.tvFeesTxt.text = "${model.fees} ${context.getString(R.string.egp)}"
            binding.tvDateAndTimeTxt.text = CommonUtilities.convertFullDateToFormattedDateTxt(model.createDate)


            itemView.setOnClickListener{
                onItemClicked(model)
            }


        }



    }


}