package rubikans.rubik.doctor.ui.main.basicInfo

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.base.BaseFragment
import rubikans.rubik.doctor.databinding.FragmentBasicInformationBinding
import rubikans.rubik.doctor.model.ProfileData
import rubikans.rubik.doctor.util.CommonUtilities
import rubikans.rubik.doctor.util.Constants
import rubikans.rubik.doctor.util.extensions.loadImage
import rubikans.rubik.doctor.util.extensions.toJsonString
import rubikans.rubik.doctor.util.extensions.toObjectFromJson


@AndroidEntryPoint
class BasicInfoFragment : BaseFragment<FragmentBasicInformationBinding>() {
    lateinit var binding: FragmentBasicInformationBinding
    private val args by navArgs<BasicInfoFragmentArgs>()
    lateinit var profileData: ProfileData



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = viewDataBinding!!


        profileData =
            args.profileData.toObjectFromJson<ProfileData>(ProfileData::class.java)


        binding.customBar.leftImage = R.drawable.back
        binding.customBar.rightImage = R.drawable.edit
        binding.customBar.leftCardView.setOnClickListener {
            navController.navigateUp()
        }
        binding.customBar.rightCardView.setOnClickListener {
            navController.navigate(BasicInfoFragmentDirections.openEditBasicInfoFragment(profileData.toJsonString()))
        }




        if(profileData.user!!.profileImage != ""){
            binding.image.loadImage(Constants.BASE_IMAGES_URL  +profileData.user!!.profileImage , R.drawable.doctor)
        }
        binding.tvName.text = profileData.user!!.profileName?.toString()
        binding.tvEmail.text = profileData.user!!.profileEmail?.toString()
        binding.tvMobile.text = profileData.user!!.profileMobile?.toString()
        binding.tvbBirthDate.text = CommonUtilities.convertFullDateToFormattedDate(profileData.user!!.dateOfBirth?.toString())!!

        if(profileData.user!!.gender?.toString() == "1.0" || profileData.user!!.gender?.toString() == "1"){
            binding.tvGenderDate.text = getString(R.string.male)
        }else if(profileData.user!!.gender?.toString() == "2.0" || profileData.user!!.gender?.toString() == "2")
        {
            binding.tvGenderDate.text = getString(R.string.female)

        }else {

        }





    }



    override fun getLayoutId(): Int {
        return R.layout.fragment_basic_information
    }
}