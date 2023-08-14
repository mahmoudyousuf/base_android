package rubikans.rubik.doctor.ui.main


import android.content.*

import android.os.Bundle

import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController

import dagger.hilt.android.AndroidEntryPoint
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.base.BaseActivity
import rubikans.rubik.doctor.databinding.ActivityMainBinding
import rubikans.rubik.doctor.ui.auth.AuthActivity



@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController










    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(!dataManager.isLogin){
//            showToast(getString(R.string.please_login))
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }
        binding = viewDataBinding!!
        navController = findNavController(R.id.fragment_home_nav)




        binding.bottomNavigation.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            val isTopLevelDestination =
                binding.bottomNavigation.menu.findItem(destination.id) != null
            binding.bottomNavigation.isVisible = isTopLevelDestination





        }

        binding.bottomNavigation.itemIconTintList = null






    }






    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }
}