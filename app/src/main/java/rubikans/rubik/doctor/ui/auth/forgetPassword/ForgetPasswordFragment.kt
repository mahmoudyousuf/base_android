package rubikans.rubik.doctor.ui.auth.forgetPassword

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.etamn.util.Status
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.base.BaseFragment
import rubikans.rubik.doctor.base.BaseResponse
import rubikans.rubik.doctor.databinding.FragmentForgetPasswordBinding
import rubikans.rubik.doctor.util.extensions.observe

import java.util.regex.Pattern

@AndroidEntryPoint
class ForgetPasswordFragment : BaseFragment<FragmentForgetPasswordBinding>() {
    lateinit var binding: FragmentForgetPasswordBinding
    val viewModel: ForgetPasswordViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = viewDataBinding!!

        binding.customBar.leftImage = R.drawable.ic_back
        binding.customBar.leftCardView.setOnClickListener {
            navController.navigateUp()
        }
        binding.customBar.hideRightIcon()

        binding.sendBtn.setOnClickListener{


            if (binding.mobileOrEmailEdt.getText().isEmpty()) {
                binding.mobileOrEmailEdt.setError(getString(R.string.email_are_required))
                return@setOnClickListener
            }

            if (!Pattern.compile(
                    "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                            + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                            + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                            + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
                ).matcher(binding.mobileOrEmailEdt.getText()).matches()
            ) {
                binding.mobileOrEmailEdt.setError(getString(R.string.enter_valid_email_format))
                return@setOnClickListener
            }



            val props = JSONObject()
            props.put("Email", binding.mobileOrEmailEdt.getText())
            viewModel.forgetPassword(jsonParser.parse(props.toString()) as JsonObject)

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
                    val response = it.data as BaseResponse<BaseResponse.EmptyData>
                    baseActivity.showSuccessSnackbar(response.message.toString())
                    navController.navigate(ForgetPasswordFragmentDirections.openOtpForgetPasswordFragment(binding.mobileOrEmailEdt.getText()))

                }
                is Status.Error -> {
                    baseActivity.hideDialogLoading()
                    baseActivity.showWarningSnackbar(it.message!!)

                }
            }
        }




    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_forget_password
    }

}