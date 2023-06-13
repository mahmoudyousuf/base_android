package rubikans.rubik.doctor.ui.main

import android.os.Bundle
import android.view.View
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.base.BaseFragment
import rubikans.rubik.doctor.databinding.FragmentBlank1Binding

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