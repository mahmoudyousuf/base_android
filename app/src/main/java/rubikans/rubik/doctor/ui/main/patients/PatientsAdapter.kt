package rubikans.rubik.doctor.ui.main.patients

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.databinding.ItemPatientBinding
import rubikans.rubik.doctor.model.PatientsListModelItem
import rubikans.rubik.doctor.util.CommonUtilities
import rubikans.rubik.doctor.util.extensions.hide
import rubikans.rubik.doctor.util.extensions.visible


class PatientsAdapter(
    private val context: Context,
    private val onItemClicked: (model: PatientsListModelItem) -> Unit,


    ) : PagingDataAdapter<PatientsListModelItem, PatientsAdapter.ViewHolder>(PatientsListModelItem.CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemPatientBinding.inflate(
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


    inner class ViewHolder(var binding: ItemPatientBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(model: PatientsListModelItem) {
            binding.tvNameTxt.text = model.patientName
            binding.tvMobileNumberTxt.text = model.profileMobile
            binding.tvInsuranceTxtTxt.text = model.insuranceCompanyName

            if(model.nextAppointment != null){
                binding.tvFirstAppointmentTxt.text = CommonUtilities.convertFullDateToFormattedDateTxtWithoutTime(model.nextAppointment)

            }

            if(model.lastAppointment != null){
                binding.tvLastAppointmentTxt.text = CommonUtilities.convertFullDateToFormattedDateTxtWithoutTime(model.lastAppointment)
            }

            if(model.gender != null){

                when (model.gender.toString()) {

                    // male
                    "1" -> {
                        binding.tvGenderTxt.text = context.getString(R.string.male)
                        binding.tvGenderTxt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_male, 0, 0, 0);

                    }


                    // female
                    "2" -> {
                        binding.tvGenderTxt.text = context.getString(R.string.female)
                        binding.tvGenderTxt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_female, 0, 0, 0);


                    }


                    else -> {

                    }
                }

            }else{
                binding.tvGenderTxt.hide()
            }









            itemView.setOnClickListener{
                onItemClicked(model)
            }





        }



    }


}