package rubikans.rubik.doctor.ui.main.changePassword

import android.content.Intent
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
import rubikans.rubik.doctor.databinding.FragmentChangePasswordBinding
import rubikans.rubik.doctor.ui.auth.AuthActivity
import rubikans.rubik.doctor.util.extensions.observe


@AndroidEntryPoint
class ChangePasswordFragment : BaseFragment<FragmentChangePasswordBinding>() {
    lateinit var binding: FragmentChangePasswordBinding
    val viewModel: ChangePasswordViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = viewDataBinding!!
        binding.customBar.leftImage = R.drawable.back
        binding.customBar.leftCardView.setOnClickListener {
            navController.navigateUp()
        }
        binding.customBar.hideRightIcon()

        binding.saveNewPassword.setOnClickListener {


            if (binding.currentPassword.getText().isEmpty()) {
                binding.currentPassword.setError(getString(R.string.password_are_required))
                return@setOnClickListener
            }

            if (binding.newPassword.getText().isEmpty()) {
                binding.newPassword.setError(getString(R.string.password_are_required))
                return@setOnClickListener
            }


            if (binding.newPassword.getText().length < 8) {

                binding.newPassword.setError(getString(R.string.password_should_be_more_than_8_char))
                return@setOnClickListener


            }else{

                var ch: Char
                var numberFlag = false
                var letterFlag = false


                for (element in binding.newPassword.getText()) {
                    ch = element

                    if (Character.isDigit(ch) ) {
                        numberFlag = true
                    }

                    if(Character.isLetter(element) ){
                        letterFlag = true
                    }


                }


                if (numberFlag  ) {
                    if(letterFlag){
                        binding.newPassword.removeError()
                    }else{
                        binding.newPassword.setError(getString(R.string.password_should_contains_number_and_at_least_1_char))
                        return@setOnClickListener
                    }
                }
                else{
                    binding.newPassword.setError(getString(R.string.password_should_contains_number_and_at_least_1_char))
                    return@setOnClickListener
                }

            }




            if (binding.confirmPassword.getText().isEmpty()) {
                binding.confirmPassword.setError(getString(R.string.confirm_Password_are_required))
                return@setOnClickListener
            }

            if (binding.newPassword.getText() != binding.confirmPassword.getText()) {
                binding.newPassword.setError(getString(R.string.passwords_not_match))
                binding.confirmPassword.setError(getString(R.string.passwords_not_match))
                return@setOnClickListener
            }


            val props = JSONObject()
            props.put("oldPassword", binding.currentPassword.getText())
            props.put("NewPassword", binding.newPassword.getText())
            props.put("NewPasswordConfirm", binding.confirmPassword.getText())


            viewModel.changePassword(jsonParser.parse(props.toString()) as JsonObject)

        }


        setObservers()
    }


    private fun setObservers() {

        observe(viewModel.changePasswordState)
        {
            when (it) {
                is Status.Loading -> {
                    baseActivity.showDialogLoading()
                }
                is Status.Success<*> -> {
                    baseActivity.hideDialogLoading()
                    val response = it.data as BaseResponse<BaseResponse.EmptyData>
                    baseActivity.showSuccessSnackbar(response.message.toString())
                    navController.navigateUp()

                }
                is Status.Error -> {
                    baseActivity.hideDialogLoading()
                    baseActivity.showWarningSnackbar(it.message!!)

                }
                is Status.Unauthorized -> {

                    baseActivity.showWarningSnackbar(getString(R.string.please_login))

                    val i = Intent(baseActivity, AuthActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                    baseActivity.finish()

                }
            }
        }





    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_change_password
    }

}