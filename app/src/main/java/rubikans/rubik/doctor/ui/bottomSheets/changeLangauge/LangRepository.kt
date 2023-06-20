package rubikans.rubik.doctor.ui.bottomSheets.changeLangauge

import android.content.Context
import rubikans.rubik.doctor.base.BaseRepository
import rubikans.rubik.doctor.data.retrofit.ApiServices
import rubikans.rubik.doctor.data.shared.DataManager


import javax.inject.Inject

class LangRepository @Inject constructor(
    private val context: Context,
    private val dataManager: DataManager,
    private val api: ApiServices
) : BaseRepository(dataManager, context) {
    fun saveLang(lang: String) {
        dataManager.saveLang(lang)
    }



}