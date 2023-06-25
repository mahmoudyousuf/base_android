package rubikans.rubik.doctor.ui.main.clinicBraches

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.etamn.util.Status

import dagger.hilt.android.AndroidEntryPoint
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.base.BaseActivity
import rubikans.rubik.doctor.base.BaseResponse
import rubikans.rubik.doctor.databinding.ActivityClinicBranchesBinding
import rubikans.rubik.doctor.model.ClinicBranchesDataItem
import rubikans.rubik.doctor.ui.auth.AuthActivity
import rubikans.rubik.doctor.ui.main.MainActivity
import rubikans.rubik.doctor.util.extensions.observe
import rubikans.rubik.doctor.util.extensions.toJsonString


@AndroidEntryPoint
class ClinicBranchesActivity : BaseActivity<ActivityClinicBranchesBinding>() {
    lateinit var binding: ActivityClinicBranchesBinding

    val viewModel: ClinicBranchesViewModel by viewModels()
    lateinit var adapter: ClinicBranchesAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = viewDataBinding!!

        if (this.dataManager.clinic == null ){
            binding.customBar.hideRightIcon()
            binding.customBar.hideLeftIcon()
        }else{
            binding.customBar.hideRightIcon()
            binding.customBar.leftImage = R.drawable.ic_back
            binding.customBar.leftCardView.setOnClickListener {
                this.onBackPressedDispatcher.onBackPressed()
            }
        }

        adapter = ClinicBranchesAdapter(
             java.util.ArrayList(),
            onItemSelected = { clinic ->
                this.dataManager.saveClinic(clinic.toJsonString())
                val i = Intent(this, MainActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
                this.finish()
            },
        )

        binding.clinicsList.adapter = adapter



        viewModel.getClinicBranches()
        setObservers()
    }






    override fun getLayoutId(): Int {
        return R.layout.activity_clinic_branches
    }



    private fun setObservers() {

        observe(viewModel.getClinicBranchesState)
        {
            when (it) {
                is Status.Loading -> {
                    this.showDialogLoading()
                }
                is Status.Success<*> -> {
                    this.hideDialogLoading()
                    val response = it.data as BaseResponse<ArrayList<ClinicBranchesDataItem>>
                    adapter.setMyData(response.data!!)


                }
                is Status.Error -> {
                    this.hideDialogLoading()
                    this.showWarningSnackbar(it.message!!)

                }
                is Status.Unauthorized -> {

                    this.showWarningSnackbar(getString(R.string.please_login))

                    val i = Intent(this, AuthActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                    this.finish()

                }
            }
        }

    }


}