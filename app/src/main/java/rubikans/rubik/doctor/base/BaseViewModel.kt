package rubikans.rubik.doctor.base


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.etamn.util.SingleLiveEvent
import com.etamn.util.Status
import rubikans.rubik.doctor.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import rubikans.rubik.doctor.util.extensions.toObjectFromJson

private const val TAG = "BaseViewModel"


abstract class BaseViewModel(private val repository: BaseRepository) : ViewModel() {
    fun <T> performNetworkCall(
        apiCall: suspend () -> Response<BaseResponse<T>>,
        status: SingleLiveEvent<Status> = SingleLiveEvent<Status>(),
        onLogInError: suspend () -> Any = {},
        doOnSuccess: (response: BaseResponse<T>) -> Unit = {}
    ) {
        if (repository.isNetworkConnected())
            viewModelScope.launch {
                withContext(Dispatchers.Default) {


                    try {
                        status.postValue(Status.Loading)
                        val response = apiCall.invoke()
                        status.postValue(handleResponse(response))

                        try {
                            doOnSuccess(response.body() as BaseResponse<T>)
                        } catch (e: Exception) {

                        }

                    } catch (e: Exception) {
                        Log.d(TAG, "performNetworkCall1: ${e.message}")
                        Log.d(TAG, "performNetworkCall1: ${e.localizedMessage}")

                        try {
                            val response =
                                onLogInError.invoke() as Response<BaseResponse<ArrayList<BaseResponse.EmptyData>>>
                            status.postValue(handleResponse(response))

                        } catch (e: Exception) {
                            Log.d(TAG, "performNetworkCall2: ${e.message}")

                            status.postValue(
                                Status.Error(
                                    1,
                                    message = repository.getString(R.string.some_thing_went_wrong_error_msg)
                                )
                            )
                        }

                    }
                }
            }
        else {
            status.postValue(Status.Error(message = repository.getString(R.string.check_internet_connection)))

        }
    }

    fun <T> performNetworkCallWithOutBaseResponse(
        apiCall: suspend () -> Response<T>,
        status: SingleLiveEvent<Status> = SingleLiveEvent<Status>(),
        doOnSuccess: (response: Response<T>) -> Unit = {}
    ) {
        if (repository.isNetworkConnected())
            viewModelScope.launch {
                withContext(Dispatchers.Default) {


                    try {
                        status.postValue(Status.Loading)
                        val response = apiCall.invoke()
                        status.postValue(handleResponseWithOutBaseResponse(response))
                        try {
                            doOnSuccess(response)
                        } catch (e: Exception) {

                        }
                    } catch (e: Exception) {
                        Log.d(TAG, "performNetworkCall: ${e.message}")
                        status.postValue(
                            Status.Error(
                                message = repository.getString(R.string.some_thing_went_wrong_error_msg)
                            )
                        )

                    }
                }
            }
        else {
            status.postValue(Status.Error(message = repository.getString(R.string.check_internet_connection)))

        }
    }

    fun doInBackground(error: (e: Exception) -> Unit = {}, block: suspend () -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                try {
                    block.invoke()
                } catch (e: Exception) {
                    error.invoke(e)
                }
            }
        }
    }


    private fun <T> handleResponse(response: Response<BaseResponse<T>>): Status {

        Log.d(TAG, "handleResponse: ${response.body().toString()}")

        if (response.code() in 200..300) {

            return if (response.body()?.isSuccessResponse!!) {
                Status.Success(response.body())
            } else {

                if (response.body()?.code == 401) {
                    Status.Unauthorized
                } else {

                    try {
                        Status.Error(response.code(), response.body()?.messageResponse!!)
                    } catch (e: Exception) {
                        Status.Error(response.code(), response.message())
                    }
                }
            }
        } else if (response.code() == 401) {

            return Status.Unauthorized
        } else {


            return try {
                val error = response.errorBody()?.string()
                    .toObjectFromJson<BaseResponse<T>>(BaseResponse::class.java)
                Status.Error(
                    response.code(),
                    message = if (error.messageResponse.isNullOrEmpty()) repository.getString(
                        R.string.some_thing_went_wrong_error_msg
                    ) else error.messageResponse
                )


            } catch (e: Exception) {
                Status.Error(response.code(), response.message())
            }

        }
    }


    private fun <T> handleResponseWithOutBaseResponse(response: Response<T>): Status {

        Log.d(TAG, "handleResponse: ${response.body().toString()}")

        if (response.code() in 200..300) {
            return if (response.isSuccessful) {
                Status.Success(response.body())
            } else {

                if (response.code() == 401) {
                    Status.Unauthorized
                } else {

                    try {
                        Status.Error(response.code(), response.message())
                    } catch (e: Exception) {
                        Status.Error(response.code(), response.message())
                    }
                }
            }
        } else if (response.code() == 401) {

            return Status.Unauthorized
        } else {


            return try {
                val error = response.errorBody()?.string()
                    .toObjectFromJson<BaseResponse<T>>(BaseResponse::class.java)
                Status.Error(
                    response.code(),
                    message = if (error.messageResponse.isNullOrEmpty()) repository.getString(
                        R.string.some_thing_went_wrong_error_msg
                    ) else error.messageResponse
                )


            } catch (e: Exception) {
                Status.Error(response.code(), response.message())
            }

        }
    }

    fun <T> checkResponse(response: Response<BaseResponse<T>>): Boolean {
        return when (handleResponse(response)) {
            is Status.Success<*> -> {
                true
            }
            else -> {
                false
            }
        }
    }


}