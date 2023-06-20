package rubikans.rubik.doctor.ui.bottomSheets.signOut

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


private const val TAG = "SignOutBottomSheet"

@AndroidEntryPoint
class SignOutBottomSheet() : BaseBottomSheet<BottomSheetConfirmationBinding>() {
    lateinit var binding: BottomSheetConfirmationBinding
    val viewModel: SignOutViewModel by viewModels()


    override fun getLayoutId(): Int {
        return R.layout.bottom_sheet_confirmation
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding!!




        binding.confirmBtn.setOnClickListener {
            viewModel.logout()
        }

        binding.cancelBtn.setOnClickListener {
            dismiss()
        }

        setObservers()
    }


    private fun setObservers() {


        observe(viewModel.logoutState)
        {
            when (it) {
                is Status.Loading -> {
                    baseActivity.showDialogLoading()
                }
                is Status.Success<*> -> {
                    baseActivity.hideDialogLoading()
                    val response = it.data as BaseResponse<BaseResponse.EmptyData>

                    LoginUtils.logoutFromApp()


                    baseActivity.dataManager.saveIsLogin(false)


//                    baseActivity.dataManager.deleteProfile()


                    val i = Intent(baseActivity, AuthActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                    baseActivity.finish()
                }
                is Status.Error -> {
                    baseActivity.hideDialogLoading()
                    baseActivity.showWarningSnackbar(it.message!!)
                }
                is Status.Unauthorized -> {
                    baseActivity.dataManager.saveIsLogin(false)
                    val i = Intent(baseActivity, AuthActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                    baseActivity.finish()
                    baseActivity.showWarningSnackbar(getString(R.string.please_login))
                }
            }
        }



    }


}