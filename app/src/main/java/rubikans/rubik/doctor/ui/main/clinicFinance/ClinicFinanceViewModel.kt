package rubikans.rubik.doctor.ui.main.clinicFinance

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow

import androidx.paging.cachedIn
import com.etamn.util.SingleLiveEvent
import com.etamn.util.Status
import com.google.gson.JsonObject
import rubikans.rubik.doctor.base.BaseViewModel
import rubikans.rubik.doctor.data.shared.DataManager
import rubikans.rubik.doctor.model.ClinicExpenseDataItem
import rubikans.rubik.doctor.model.ClinicIncomeDataItem

import javax.inject.Inject

@HiltViewModel
class ClinicFinanceViewModel @Inject constructor(
    private val repository: ClinicFinanceRepository,
    val dataManager: DataManager

) : BaseViewModel(repository) {




    val isIncome = SingleLiveEvent<Boolean>()


    init {
        isIncome.value = true
    }

    fun setIncomeState(state: Boolean) {
        isIncome.value = state
        isIncome.value = isIncome.value
    }


    val pIncomeTypeID = SingleLiveEvent<String>()
    val pIncomeTypeName = SingleLiveEvent<String>()
    val pDateFrom = SingleLiveEvent<String>()
    val pDateTo = SingleLiveEvent<String>()
    val pSearchText = SingleLiveEvent<String>()



    var incomeList = getClinicIncomeList()
    fun getClinicIncomeList(): Flow<PagingData<ClinicIncomeDataItem>> {
        return Pager(config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { ClinicIncomeDataSource(this, repository) }).flow.cachedIn(
            viewModelScope
        )
    }



    var expenseList = getClinicExpenseList()
    //    @JvmName("getExpenseList1")
    fun getClinicExpenseList(): Flow<PagingData<ClinicExpenseDataItem>> {
        return Pager(config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { ClinicExpenseDataSource(this, repository) }).flow.cachedIn(
            viewModelScope
        )
    }


}


