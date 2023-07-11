package rubikans.rubik.doctor.ui.main.clinicFinance

import androidx.paging.PagingSource
import androidx.paging.PagingState
import rubikans.rubik.doctor.model.ClinicIncomeDataItem


class ClinicIncomeDataSource(
    var viewModel: ClinicFinanceViewModel,
    var repository: ClinicFinanceRepository
) : PagingSource<Int, ClinicIncomeDataItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ClinicIncomeDataItem> {

        return try {
            val nextPage = params.key ?: 1
            val response = repository.getClinicIncomeList(
              pPageNumber =   nextPage,
                pIncomeTypeID = viewModel.pIncomeTypeID.value ?: "",
                pDateFrom = viewModel.pDateFrom.value ?: "",
                pDateTo = viewModel.pDateTo.value ?: "",
                pSearchText = viewModel.pSearchText.value ?: "",

                )

            if (viewModel.checkResponse(response)) {
                val list = response.body()?.data as ArrayList<ClinicIncomeDataItem>
                LoadResult.Page(
                    data = list,
                    prevKey = if (nextPage == 1) null else nextPage - 1,
                    nextKey = if (response.body()?.data!!.isNotEmpty() && response.body()?.data != null) nextPage.plus(
                        1
                    ) else null
                )
            } else {
                LoadResult.Page(
                    data = ArrayList(),
                    prevKey = null,
                    nextKey = null
                )
            }


        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, ClinicIncomeDataItem>): Int? {
        return null
    }

}


