package rubikans.rubik.doctor.ui.main.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import rubikans.rubik.doctor.model.AppointmentItem


class AppointmentsDataSource(
    var viewModel: HomeViewModel,
    var repository: HomeRepository
) : PagingSource<Int, AppointmentItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AppointmentItem> {

        return try {
            val nextPage = params.key ?: 1
            val response = repository.getAppointments(
              pPageNumber =   nextPage,
                pBranchId = viewModel.pBranchId.value ?: "",
                pBookingID = viewModel.pBookingID.value ?: "",
                pPatientID = viewModel.pPatientID.value ?: "",
                pStatusID = viewModel.pStatusID.value ?: "",
                pDateFrom = viewModel.pDateFrom.value ?: "",
                pDateTo = viewModel.pDateTo.value ?: "",
                pSearchText = viewModel.pSearchText.value ?: "",

                )

            if (viewModel.checkResponse(response)) {
                val list = response.body()?.data!!.appointment as ArrayList<AppointmentItem>
                LoadResult.Page(
                    data = list,
                    prevKey = if (nextPage == 1) null else nextPage - 1,
                    nextKey = if (response.body()?.data!!.appointment!!.isNotEmpty() && response.body()?.data != null) nextPage.plus(
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

    override fun getRefreshKey(state: PagingState<Int, AppointmentItem>): Int? {
        return null
    }

}


