package rubikans.rubik.doctor.ui.bottomSheets.changeAppointmentstatus

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.etamn.util.Status
import dagger.hilt.android.AndroidEntryPoint
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.base.BaseBottomSheet
import rubikans.rubik.doctor.base.BaseResponse
import rubikans.rubik.doctor.databinding.BottomSheetConfirmationBinding
import rubikans.rubik.doctor.ui.auth.AuthActivity
import rubikans.rubik.doctor.util.LoginUtils
import rubikans.rubik.doctor.util.extensions.observe


private const val TAG = "ChangeAppointmentStatusBottomSheet"

@AndroidEntryPoint
class ChangeAppointmentStatusBottomSheet(
    val onConfirmed: () -> Unit,
) : BaseBottomSheet<BottomSheetConfirmationBinding>() {
    lateinit var binding: BottomSheetConfirmationBinding



    override fun getLayoutId(): Int {
        return R.layout.bottom_sheet_confirmation
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding!!





//        binding.confirmTxt.text = getString(R.string.cancel_this_appointment)


        binding.confirmTxt.text = getString(R.string.are_you_sure_change_status)
        binding.confirmBtnTxt.text = getString(R.string.yes)


        binding.confirmBtn.setOnClickListener {
            dismiss()
            onConfirmed()
        }

        binding.cancelBtn.setOnClickListener {
            dismiss()
        }


    }




}