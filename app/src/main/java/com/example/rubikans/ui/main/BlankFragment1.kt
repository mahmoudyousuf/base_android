package com.example.rubikans.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rubikans.R
import com.example.rubikans.base.BaseFragment
import com.example.rubikans.databinding.FragmentBlank1Binding

class BlankFragment1 : BaseFragment<FragmentBlank1Binding>() {
    lateinit var binding: FragmentBlank1Binding
    override fun getLayoutId(): Int {
        return R.layout.fragment_blank1
    }
    // TODO: Rename and change types of parameters

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding!!
    }

}