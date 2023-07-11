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
import rubikans.rubik.doctor.databinding.ItemIncomeBinding
import rubikans.rubik.doctor.model.ClinicIncomeDataItem
import rubikans.rubik.doctor.util.CommonUtilities



class ClinicIncomeAdapter(
    private val context: Context,
    private val onItemClicked: (model: ClinicIncomeDataItem) -> Unit,


    ) : PagingDataAdapter<ClinicIncomeDataItem, ClinicIncomeAdapter.ViewHolder>(ClinicIncomeDataItem.CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemIncomeBinding.inflate(
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


    inner class ViewHolder(var binding: ItemIncomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(model: ClinicIncomeDataItem) {
            binding.tvNameTxt.text = model.patientName
            binding.tvIdTxt.text =  "# ${model.patientProfileID.toString()}"
            binding.tvIncomeTypeTxt.text = model.incomeTypeName
            binding.tvIncomeNameTxt.text = model.consultationServiceName
            binding.tvFeesTxt.text = "${model.fees} ${context.getString(R.string.egp)}"
            binding.tvDateAndTimeTxt.text = CommonUtilities.convertFullDateToFormattedDateTxt(model.createDate)


            itemView.setOnClickListener{
                onItemClicked(model)
            }


        }



    }


}