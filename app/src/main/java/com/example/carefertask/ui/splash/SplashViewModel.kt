package com.example.carefertask.ui.splash


import com.example.carefertask.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: SplashRepository
) : BaseViewModel(repository) {

    fun saveXApiKey(key : String){
        repository.saveXApiKey(key)
    }



}


