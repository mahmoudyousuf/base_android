package rubikans.rubik.doctor.ui.main.patients

import androidx.paging.PagingSource
import androidx.paging.PagingState
import rubikans.rubik.doctor.model.PatientsListModelItem


class PatientsDataSource(
    var viewModel: PatientsViewModel,
    var repository: PatientsRepository
) : PagingSource<Int, PatientsListModelItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PatientsListModelItem> {

        return try {
            val nextPage = params.key ?: 1
            val response = repository.getPatientList(
              pPageNumber =   nextPage,
                pPhoneNumber = viewModel.pPhoneNumber.value ?: "",
                pDateFrom = viewModel.pDateFrom.value ?: "",
                pDateTo = viewModel.pDateTo.value ?: "",
                pSearchText = viewModel.pSearchText.value ?: "",

                )

            if (viewModel.checkResponse(response)) {
                val list = response.body()?.data as ArrayList<PatientsListModelItem>
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

    override fun getRefreshKey(state: PagingState<Int, PatientsListModelItem>): Int? {
        return null
    }

}


