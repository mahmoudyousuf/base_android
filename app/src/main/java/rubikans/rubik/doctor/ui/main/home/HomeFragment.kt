package rubikans.rubik.doctor.ui.main.home

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
import rubikans.rubik.doctor.databinding.FragmentForgetPasswordBinding
import rubikans.rubik.doctor.databinding.FragmentHomeBinding
import rubikans.rubik.doctor.ui.clinicBraches.ClinicBranchesActivity
import rubikans.rubik.doctor.util.extensions.observe

import java.util.regex.Pattern

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = viewDataBinding!!


        binding.customBar.hideRightIcon()
        binding.customBar.leftImage = R.drawable.ic_home_menu
        binding.customBar.leftCardView.setOnClickListener {

        }

        binding.customBar.leftCardView.setOnClickListener {
            startActivity(Intent(baseActivity, ClinicBranchesActivity::class.java))

        }


    }



    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

}