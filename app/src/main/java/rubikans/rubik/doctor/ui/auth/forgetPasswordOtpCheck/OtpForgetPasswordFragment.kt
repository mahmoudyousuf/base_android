package rubikans.rubik.doctor.ui.auth.forgetPasswordOtpCheck

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.etamn.util.Status
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_sign_in.*
import org.json.JSONObject
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.base.BaseFragment
import rubikans.rubik.doctor.base.BaseResponse
import rubikans.rubik.doctor.databinding.FragmentOtpForgetPasswordBinding
import rubikans.rubik.doctor.util.extensions.observe


@AndroidEntryPoint
class OtpForgetPasswordFragment : BaseFragment<FragmentOtpForgetPasswordBinding>() {
    lateinit var binding: FragmentOtpForgetPasswordBinding
    val viewModel: ForgetPasswordOtpCheckViewModel by viewModels()
    private val args by navArgs<OtpForgetPasswordFragmentArgs>()
    var pin: String = ""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = viewDataBinding!!

        binding.customBar.leftImage = R.drawable.ic_back
        binding.customBar.leftCardView.setOnClickListener {
            navController.navigateUp()
        }
        binding.customBar.hideRightIcon()

        binding.pinCode.setOnPinEnteredListener {
            pin = it.toString()
            binding.pinCode.setText("")

            val props = JSONObject()
            props.put("EmailOrMobile", args.email)
            props.put("Tokencode", it.toString())
            props.put("MobileCode", "")
            props.put("Mobile", "")
            viewModel.forgetPasswordOtpCheck(jsonParser.parse(props.toString()) as JsonObject)
        }


        binding.resendCode.setOnClickListener{
            val props = JSONObject()
            props.put("Email", args.email)
            viewModel.resendCode(jsonParser.parse(props.toString()) as JsonObject)
        }


        setObservers()
    }

    private fun setObservers() {

        observe(viewModel.forgetPasswordState)
        {
            when (it) {
                is Status.Loading -> {
                    baseActivity.showDialogLoading()
                }
                is Status.Success<*> -> {
                    baseActivity.hideDialogLoading()
                    val response = it.data as BaseResponse<String>
                    baseActivity.showSuccessSnackbar(response.message.toString())
                    navController.navigate(
                        OtpForgetPasswordFragmentDirections.openConfirmForgetPasswordFragment(
                            accountId = response.data!!, email = args.email, code = pin
                        )
                    )


                }
                is Status.Error -> {
                    baseActivity.hideDialogLoading()
                    baseActivity.showWarningSnackbar(it.message!!)

                }
            }
        }



        observe(viewModel.resendCodeState)
        {
            when (it) {
                is Status.Loading -> {
                    baseActivity.showDialogLoading()
                }
                is Status.Success<*> -> {
                    baseActivity.hideDialogLoading()
                    val response = it.data as BaseResponse<BaseResponse.EmptyData>
                    baseActivity.showSuccessSnackbar(response.message.toString())

                }
                is Status.Error -> {
                    baseActivity.hideDialogLoading()
                    baseActivity.showWarningSnackbar(it.message!!)

                }
            }
        }

    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_otp_forget_password
    }

}