package rubikans.rubik.doctor.ui.bottomSheets.changeLangauge

import dagger.hilt.android.lifecycle.HiltViewModel
import rubikans.rubik.doctor.base.BaseViewModel


import javax.inject.Inject


@HiltViewModel
class LangViewModel @Inject constructor(
    private val repository: LangRepository
) : BaseViewModel(repository) {

    fun saveLang(lang: String) {
        repository.saveLang(lang)
    }


}


