package com.etamn.util

sealed class Status {
    data class Success<T>(
        var data: T?
    ) : Status()

    data class Error(
        var code: Int = 0,
        var message: String? = ""
    ) : Status()


    object Loading : Status()


    object Unauthorized : Status()


}