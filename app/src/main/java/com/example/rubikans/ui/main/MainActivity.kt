package com.example.rubikans.ui.main

import android.os.Bundle
import androidx.core.view.WindowCompat
import com.example.rubikans.R
import com.example.rubikans.base.BaseActivity
import com.example.rubikans.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

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