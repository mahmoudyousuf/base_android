package rubikans.rubik.doctor.ui.auth

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.base.BaseActivity
import rubikans.rubik.doctor.databinding.ActivityAuthBinding



@AndroidEntryPoint
class AuthActivity : BaseActivity<ActivityAuthBinding>() {

    lateinit var binding: ActivityAuthBinding
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding!!
        navController = findNavController(R.id.fragment_auth_nav)

        }


    override fun getLayoutId(): Int {
        return R.layout.activity_auth
    }
}