package com.example.carefertask.ui.main.fragments.home


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.etamn.util.SingleLiveEvent
import com.etamn.util.Status
import com.example.carefertask.base.BaseViewModel
import com.example.carefertask.data.room.faveMatches.FaveTeamEntity
import com.example.carefertask.model.MatchesItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : BaseViewModel(repository) {


    val matchesList = MutableLiveData<ArrayList<MatchesItem>?>()


    val getMatchesState = SingleLiveEvent<Status>()
    fun getMatches() {
        getFaveMatch()
        performNetworkCall({
            repository.getMatches()
        }, getMatchesState)
    }

    fun addFaveMatch(int: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.addFaveMatch(int)
            }
        }
    }


    val faveList = SingleLiveEvent<List<FaveTeamEntity?>>()
    fun getFaveMatch() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                faveList.postValue(repository.getFaveMatch())
            }
        }
    }
}


