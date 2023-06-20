package rubikans.rubik.doctor.ui.bottomSheets.changeLangauge

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.SplashActivity
import rubikans.rubik.doctor.base.BaseBottomSheet
import rubikans.rubik.doctor.databinding.BottomSheetChangeLangaugeBinding
import rubikans.rubik.doctor.util.LocaleUtils


import java.util.*


private const val TAG = "ChangeLanguageBottomSheet"

@AndroidEntryPoint
class ChangeLanguageBottomSheet() : BaseBottomSheet<BottomSheetChangeLangaugeBinding>() {
    lateinit var binding: BottomSheetChangeLangaugeBinding
    val viewModel: LangViewModel by viewModels()


    override fun getLayoutId(): Int {
        return R.layout.bottom_sheet_change_langauge
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding!!


        binding.english.setOnClickListener {


            dismiss()
            LocaleUtils.setLocale(Locale(LocaleUtils.LAN_ENGLISH))
            LocaleUtils.updateConfig(
                baseActivity.application,
                resources.configuration
            )
            viewModel.saveLang(LocaleUtils.LAN_ENGLISH)
            SplashActivity.starActivity(baseActivity)


        }

        binding.arabic.setOnClickListener {

            dismiss()
            LocaleUtils.setLocale(Locale(LocaleUtils.LAN_ARABIC))
            LocaleUtils.updateConfig(
                baseActivity.application,
                resources.configuration
            )
            viewModel.saveLang(LocaleUtils.LAN_ARABIC)
            SplashActivity.starActivity(baseActivity)
        }
    }


}


