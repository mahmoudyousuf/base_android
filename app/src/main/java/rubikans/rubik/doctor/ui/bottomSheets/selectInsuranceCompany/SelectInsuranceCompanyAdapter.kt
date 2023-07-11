package rubikans.rubik.doctor.ui.bottomSheets.selectInsuranceCompany

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import rubikans.rubik.doctor.data.shared.DataManager
import rubikans.rubik.doctor.databinding.ItemSelectPatientBinding
import rubikans.rubik.doctor.model.InsuranceCompanyItem


class SelectInsuranceCompanyAdapter(
    private val dataManager: DataManager,
    private val context: Context,
    private var addedList: ArrayList<InsuranceCompanyItem>,
    var onItemSelected :(model: InsuranceCompanyItem)->Unit
) : RecyclerView.Adapter<SelectInsuranceCompanyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemSelectPatientBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setMyData(myData: ArrayList<InsuranceCompanyItem>) {
        this.addedList = myData
        notifyDataSetChanged()
    }


    @SuppressLint("NotifyDataSetChanged")
    fun search(newList: ArrayList<InsuranceCompanyItem>) {
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

    inner class ViewHolder(var binding: ItemSelectPatientBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SuspiciousIndentation", "SetTextI18n")
        fun bind(model: InsuranceCompanyItem) {

            binding.nameTxt.text = model.mainName

            itemView.setOnClickListener{
                onItemSelected(model)
            }

        }
    }

}