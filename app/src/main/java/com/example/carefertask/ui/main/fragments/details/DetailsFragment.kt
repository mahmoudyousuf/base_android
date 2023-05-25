package com.example.carefertask.ui.main.fragments.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.carefertask.R
import com.example.carefertask.base.BaseFragment
import com.example.carefertask.databinding.FragmentDetailsBinding


class DetailsFragment : BaseFragment<FragmentDetailsBinding>() {
    lateinit var binding: FragmentDetailsBinding

    override fun getLayoutId(): Int {
        return  R.layout.fragment_details
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}