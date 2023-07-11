package rubikans.rubik.doctor.ui.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.etamn.util.Status
import dagger.hilt.android.AndroidEntryPoint
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.base.BaseFragment
import rubikans.rubik.doctor.base.BaseResponse
import rubikans.rubik.doctor.databinding.FragmentProfileBinding
import rubikans.rubik.doctor.model.ProfileData
import rubikans.rubik.doctor.ui.auth.AuthActivity
import rubikans.rubik.doctor.ui.bottomSheets.changeLangauge.ChangeLanguageBottomSheet
import rubikans.rubik.doctor.ui.bottomSheets.signOut.SignOutBottomSheet
import rubikans.rubik.doctor.util.Constants
import rubikans.rubik.doctor.util.extensions.loadImage
import rubikans.rubik.doctor.util.extensions.observe
import rubikans.rubik.doctor.util.extensions.toJsonString


@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    lateinit var binding: FragmentProfileBinding
    val viewModel: ProfileViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = viewDataBinding!!

       binding.changePasswordLayout.setOnClickListener {
            navController.navigate(ProfileFragmentDirections.openChangePasswordFragment())
        }




        binding.logOutLayout.setOnClickListener{
            val bottomSheet = SignOutBottomSheet()
            bottomSheet.show(baseActivity.supportFragmentManager, "SignOutBottomSheet")
        }

        binding.changeLanguageLayout.setOnClickListener{
            val bottomSheet = ChangeLanguageBottomSheet()
            bottomSheet.show(baseActivity.supportFragmentManager, "ChangeLanguageBottomSheet")
        }

        viewModel.getProfile()
        setObservers()
    }


    private fun setObservers() {

        observe(viewModel.getProfileState)
        {
            when (it) {
                is Status.Loading -> {
                    baseActivity.showDialogLoading()
                }
                is Status.Success<*> -> {
                    baseActivity.hideDialogLoading()
                    val response = it.data as BaseResponse<ProfileData>
                    binding.titleTxt.text = response.data!!.user!!.profileName?.toString()
                    binding.subTitleTxt.text = response.data!!.user!!.specialiytName?.toString()
                    if(response.data!!.user!!.profileImage != ""){
                        binding.image.loadImage(Constants.BASE_IMAGES_URL  + response.data!!.user!!.profileImage , R.drawable.doctor)
                    }

                    binding.basicInfoLayout.setOnClickListener {
                        navController.navigate(ProfileFragmentDirections.openBasicInfoFragment(response.data.toJsonString()))

                    }


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
        return R.layout.fragment_profile
    }
}