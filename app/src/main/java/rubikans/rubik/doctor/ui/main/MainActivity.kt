package rubikans.rubik.doctor.ui.main

import android.os.Bundle
import androidx.core.view.WindowCompat
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : rubikans.rubik.doctor.base.BaseActivity<ActivityMainBinding>() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = viewDataBinding!!

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }
}