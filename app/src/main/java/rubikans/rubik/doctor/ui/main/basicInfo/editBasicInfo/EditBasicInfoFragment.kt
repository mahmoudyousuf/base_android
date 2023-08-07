package rubikans.rubik.doctor.ui.main.basicInfo.editBasicInfo

import android.app.Activity
import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.navArgs
import com.etamn.util.Status
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.RequestBody
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.base.BaseFragment
import rubikans.rubik.doctor.base.BaseResponse
import rubikans.rubik.doctor.databinding.FragmentEditBasicInformationBinding
import rubikans.rubik.doctor.model.ProfileData
import rubikans.rubik.doctor.ui.auth.AuthActivity
import rubikans.rubik.doctor.util.CommonUtilities
import rubikans.rubik.doctor.util.Constants
import rubikans.rubik.doctor.util.extensions.getStringPart
import rubikans.rubik.doctor.util.extensions.loadImage
import rubikans.rubik.doctor.util.extensions.observe
import rubikans.rubik.doctor.util.extensions.toObjectFromJson

import java.io.File
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class EditBasicInfoFragment : BaseFragment<FragmentEditBasicInformationBinding>() {
    lateinit var binding: FragmentEditBasicInformationBinding

    private val args by navArgs<EditBasicInfoFragmentArgs>()
    lateinit var profileData: ProfileData

    val viewModel: EditBasicInfoViewModel by activityViewModels()


    private val myCalendar = Calendar.getInstance()
    lateinit var  date: DatePickerDialog.OnDateSetListener



    var flag = 0
    var genderId = 0

    var gendersList = ArrayList<String>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gendersList.add(getString(R.string.male))
        gendersList.add(getString(R.string.female))

        date =
            DatePickerDialog.OnDateSetListener { view, year, month, day ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, day)
                updateLabel()
            }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileData =
            args.profileData.toObjectFromJson<ProfileData>(ProfileData::class.java)

        binding = viewDataBinding!!
        binding.customBar.leftImage = R.drawable.back
        binding.customBar.rightImage = R.drawable.ic_save
        binding.customBar.leftCardView.setOnClickListener {
            navController.navigateUp()
        }



        binding.birthDate.setLeftIcon(R.drawable.ic_calender)
        binding.birthDate.setFocusable()
        binding.birthDate.setOnClickListenerEditText {
            binding.birthDate.showDate {
                DatePickerDialog(
                    baseActivity,
                    date,
                    myCalendar[Calendar.YEAR],
                    myCalendar[Calendar.MONTH],
                    myCalendar[Calendar.DAY_OF_MONTH]
                ).show()
            }
        }


        if(profileData.user!!.profileImage != ""){
            binding.image.loadImage(Constants.BASE_IMAGES_URL  +profileData.user!!.profileImage , R.drawable.doctor)
        }
        profileData.user!!.profileName?.let { binding.fullName.setText(it) }

        if(profileData.user!!.gender?.toString() == "1.0" || profileData.user!!.gender?.toString() == "1"){
            profileData.user!!.gender?.let { binding.gender.setText(getString(R.string.male)) }

        }else if(profileData.user!!.gender?.toString() == "2.0" || profileData.user!!.gender?.toString() == "2")
        {
            profileData.user!!.gender?.let { binding.gender.setText(getString(R.string.female)) }


        }else {

        }

        profileData.user!!.dateOfBirth?.let { binding.birthDate.setText(CommonUtilities.convertFullDateToFormattedDatex(it.toString())!!) }

        Log.d(ContentValues.TAG, "bithcdate: ${binding.birthDate.getText().toString()}")
//        LOG(TAG , "bithcdate ${binding.birthDate.getText().toString()}")







        binding.editImageTxt.setOnClickListener {
            flag = 1
            ImagePicker.with(this)
                .crop()
                .compress(1080)
                .maxResultSize(
                    1080,
                    1080
                )
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }






        binding.gender.setOnDropDownClickListner {

            val allergiesPopUp = PopupMenu(context, binding.gender)
            for (i in 0 until gendersList.size) {

                allergiesPopUp.menu.add(i,
                    i,
                    i,
                    gendersList[i])

            }
            allergiesPopUp.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->


                binding.gender.setText(gendersList[item.itemId])


                if (gendersList[item.itemId] == getString(R.string.male)) {
                    genderId = 1
                } else {
                    genderId = 2
                }


                return@OnMenuItemClickListener false
            })
            allergiesPopUp.show()

        }





        binding.customBar.rightCardView.setOnClickListener {


            if (binding.fullName.getText().isEmpty()) {
                binding.fullName.setError(getString(R.string.full_name_are_required))
                return@setOnClickListener
            }



            val data: MutableMap<String, RequestBody> = HashMap()
            data["ProfileName"] = binding.fullName.getText().getStringPart()!!
            data["ProfileID"] = baseActivity.dataManager.profile!!.user!!.profileID.toString().getStringPart()!!


            if (genderId != 0) {
                data["Gender"] = genderId.toString().getStringPart()
            }



            if (binding.birthDate.getText().isNotEmpty()) {
                data["Dateofbirth"] = binding.birthDate.getText().getStringPart()
            }



            viewModel.updateUserInfo(data)


        }

        setObservers()


    }


     private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            if (resultCode == Activity.RESULT_OK) {
                val resultUri = data?.data!!
                Log.d(ContentValues.TAG, "onActivityResult: ${resultUri.path!!}")

                when (flag) {
                    0 -> {}
                    1 -> {
                        viewModel.profileImageFile.value = File(resultUri.path!!)
                        binding.image.setImageURI(Uri.fromFile(viewModel.profileImageFile.value))

                    }

                }


            }
        }
    private fun setObservers() {



        observe(viewModel.updateUserInfoState)
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
                    navController.navigateUp()
                    LocalBroadcastManager.getInstance(baseActivity)
                        .sendBroadcast(Intent("UpdateProfileEvent"))


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
        return R.layout.fragment_edit_basic_information
    }
    private fun updateLabel() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
         binding.birthDate.setText(dateFormat.format(myCalendar.time))
    }
}