package com.example.carefertask.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import com.example.carefertask.R
import com.example.carefertask.base.BaseActivity
import com.example.carefertask.databinding.ActivityMainBinding
import com.example.carefertask.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivityMainBinding>() {

    val viewModel: SplashViewModel by viewModels()
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = viewDataBinding!!


        Handler(Looper.getMainLooper()).postDelayed({

            viewModel.saveXApiKey("55e9a603b8c84313a7200baed39e5925")

            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()


        }, 3000)


    }


    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }


}