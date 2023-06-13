package com.example.rubikans.base


import android.content.Context
import com.etamn.util.ConnectivityUtils
import com.example.rubikans.data.shared.DataManager


abstract class BaseRepository(private val dataManager: DataManager, private var context: Context) {


    private val connectivityUtils: ConnectivityUtils = ConnectivityUtils(context)

    fun isNetworkConnected(): Boolean {
        return connectivityUtils.isConnected()
    }

    fun getString(i: Int): String {
        return context.getString(i)
    }


}