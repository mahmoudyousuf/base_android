package rubikans.rubik.doctor.ui.main.profile

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.base.BaseFragment
import rubikans.rubik.doctor.databinding.FragmentProfileBinding
import rubikans.rubik.doctor.ui.bottomSheets.changeLangauge.ChangeLanguageBottomSheet
import rubikans.rubik.doctor.ui.bottomSheets.signOut.SignOutBottomSheet


@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    lateinit var binding: FragmentProfileBinding



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


    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_profile
    }
}