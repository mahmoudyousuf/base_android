package rubikans.rubik.doctor.base


import android.content.Context
import rubikans.rubik.doctor.util.ConnectivityUtils
import rubikans.rubik.doctor.data.shared.DataManager


abstract class BaseRepository(private val dataManager: DataManager, private var context: Context) {


    private val connectivityUtils: ConnectivityUtils = ConnectivityUtils(context)

    fun isNetworkConnected(): Boolean {
        return connectivityUtils.isConnected()
    }

    fun getString(i: Int): String {
        return context.getString(i)
    }


}