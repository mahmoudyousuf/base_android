package rubikans.rubik.doctor.ui.main.clinicBraches

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.data.shared.DataManager
import rubikans.rubik.doctor.databinding.ItemClinicBinding
import rubikans.rubik.doctor.model.ClinicBranchesDataItem
import rubikans.rubik.doctor.util.Constants
import rubikans.rubik.doctor.util.extensions.hide
import rubikans.rubik.doctor.util.extensions.loadImage


class ClinicBranchesAdapter(
    private var addedList: ArrayList<ClinicBranchesDataItem>,
    var onItemSelected :(addLabsAndXRayModel: ClinicBranchesDataItem)->Unit
) : RecyclerView.Adapter<ClinicBranchesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemClinicBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setMyData(myData: ArrayList<ClinicBranchesDataItem>) {
        this.addedList = myData
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

    inner class ViewHolder(var binding: ItemClinicBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SuspiciousIndentation", "SetTextI18n")
        fun bind(model: ClinicBranchesDataItem) {

            if(model.branchName != null){
                binding.tvNameTxt.text =  model.branchName

            }else{
                binding.tvNameTxt.hide()

            }


            if(model.addressName != null){
                binding.tvLocationTxt.text =  model.addressName

            }else{
                binding.tvLocationTxt.hide()
            }








            binding.image.loadImage(Constants.BASE_IMAGES_URL  + model.branchImagePath , R.drawable.logo_final)


            itemView.setOnClickListener{
                onItemSelected(model)
            }

        }
    }

}