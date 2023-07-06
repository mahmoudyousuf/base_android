package rubikans.rubik.doctor.ui.dialogs

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.base.BaseDialog
import rubikans.rubik.doctor.databinding.DialogCompleteClinicSettingBinding
import rubikans.rubik.doctor.databinding.DialogConfirmAppointmentBinding


@AndroidEntryPoint
class CompleteClinicSettingDialog(
    context: Context,
) : BaseDialog<DialogCompleteClinicSettingBinding>() {
    override fun getTheme() = R.style.RoundedCornersDialog




    lateinit var binding: DialogCompleteClinicSettingBinding



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding!!



        binding.sendBtn.setOnClickListener {

            dismiss()

        }




    }




    override fun getLayoutId(): Int {
        return R.layout.dialog_complete_clinic_setting
    }


}