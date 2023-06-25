package rubikans.rubik.doctor.ui.auth.signIn

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.etamn.util.Status
import dagger.hilt.android.AndroidEntryPoint
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.base.BaseFragment
import rubikans.rubik.doctor.base.BaseResponse
import rubikans.rubik.doctor.databinding.FragmentSignInBinding
import rubikans.rubik.doctor.ui.clinicBraches.ClinicBranchesActivity
import rubikans.rubik.doctor.ui.main.MainActivity
import rubikans.rubik.doctor.util.extensions.observe

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>() {
    lateinit var binding: FragmentSignInBinding
    val viewModel: SignInViewModel by viewModels()


    override fun onResume() {
        super.onResume()
        binding.email.setText("")
        binding.password.setText("")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        baseActivity.dataManager.patientFound(false)


        binding = viewDataBinding!!


        binding.signUpTv.setOnClickListener {







//            navController.navigate(SignInFragmentDirections.openSignUpFragment())
        }
        binding.forgetPassword.setOnClickListener {
            navController.navigate(SignInFragmentDirections.openForgetPasswordFragment())

        }



        binding.logInBt.setOnClickListener {


            if (binding.email.getText().isEmpty()) {
                binding.email.setError(getString(R.string.email_are_required))
                return@setOnClickListener
            }
            if (binding.password.getText().isEmpty()) {
                binding.password.setError(getString(R.string.password_are_required))
                return@setOnClickListener
            }

            viewModel.getLoginToken(binding.email.getText(),
                binding.password.getText())

        }


        setObservers()


    }

    private fun setObservers() {

        observe(viewModel.loginTokenState)
        {
            when (it) {
                is Status.Loading -> {
                    baseActivity.showDialogLoading()
                }
                is Status.Success<*> -> {
                    baseActivity.hideDialogLoading()
                    val response = it.data as BaseResponse<BaseResponse.EmptyData>

                    baseActivity.showSuccessSnackbar("success")

                    print(response.token)
                    baseActivity.dataManager.saveToken(response.token!!.toString())
                    baseActivity.dataManager.saveIsLogin(true)
                    val i = Intent(baseActivity, ClinicBranchesActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                    baseActivity.finish()


                }
                is Status.Error -> {
                    baseActivity.hideDialogLoading()
                    if(it.message.toString() == "Account Not Verfied."){

                        baseActivity.showWarningSnackbar("Account Not Verfied.")

//
//                        navController.navigate(SignInFragmentDirections.openOtpSignUpVerificationFragment(
//                            isFromLoginScreen = "true",
//                            email = binding.email.getText(),
//                            password = binding.password.getText(),
//                            patientFound = false
//                        ))
//


                    }else{
                        baseActivity.showWarningSnackbar(getString(R.string.check_user_and_password))

                    }


                }

            }
        }


    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_sign_in
    }

}