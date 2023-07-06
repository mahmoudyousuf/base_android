package rubikans.rubik.doctor.ui.dialogs

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.base.BaseDialog
import rubikans.rubik.doctor.databinding.DialogConfirmAppointmentBinding


@AndroidEntryPoint
class ConfirmationAppointmentDialog(
    context: Context,
) : BaseDialog<DialogConfirmAppointmentBinding>() {
    override fun getTheme() = R.style.RoundedCornersDialog




    lateinit var binding: DialogConfirmAppointmentBinding



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding!!



        binding.sendBtn.setOnClickListener {

            dismiss()

        }




    }


    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

            baseActivity.dataManager.saveIsRefreshHome("1")
            baseActivity.onBackPressedDispatcher.onBackPressed()




    }

    override fun getLayoutId(): Int {
        return R.layout.dialog_confirm_appointment
    }


}