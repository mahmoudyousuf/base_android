package com.example.carefertask.ui.main.fragments.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.example.carefertask.R
import com.example.carefertask.base.BaseFragment
import com.example.carefertask.databinding.FragmentDetailsBinding
import com.example.carefertask.model.MatchesItem
import com.example.carefertask.util.extensions.convertToTime
import com.example.carefertask.util.extensions.toObjectFromJson


class DetailsFragment : BaseFragment<FragmentDetailsBinding>() {

    lateinit var binding: FragmentDetailsBinding
    var model : MatchesItem?=null
    val args by navArgs<DetailsFragmentArgs>()

    override fun getLayoutId(): Int {
        return  R.layout.fragment_details
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding!!

        binding.back.setOnClickListener {
            navController.navigateUp()
        }

       setUiData()


    }

    private fun setUiData() {

        model = args.model.toObjectFromJson(MatchesItem::class.java)

        binding.homeTeam.text = model?.homeTeam?.name
        binding.awayTeam.text = model?.awayTeam?.name

        if (model?.score?.fullTime?.homeTeam==null){

            binding.time.text = model?.utcDate?.take(16)?.convertToTime()
            binding.fullTimeLabel.isVisible = false
            binding.cardView3.isVisible = false
            binding.halfTimeLabel.isVisible = false
            binding.cardView2.isVisible = false

        }
        else{
            binding.time.text = "${model?.score?.fullTime?.homeTeam} - ${model?.score?.fullTime?.awayTeam}"

            binding.hafTime.text = "${model?.score?.halfTime?.homeTeam} - ${model?.score?.halfTime?.awayTeam}"
            binding.fullTime.text = "${model?.score?.fullTime?.homeTeam} - ${model?.score?.fullTime?.awayTeam}"
        }

        binding.area.text =model?.stage

    }


}