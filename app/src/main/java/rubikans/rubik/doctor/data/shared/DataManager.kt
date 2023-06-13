package rubikans.rubik.doctor.data.shared

import android.content.Context
import android.content.SharedPreferences


class DataManager(context: Context?) {

    var mSharedPreferences: SharedPreferences? = null

    init {
        mSharedPreferences = context?.getSharedPreferences("Rubikans", 0)
    }

    fun clear() {
        mSharedPreferences!!.edit().clear().apply()

    }

    ///////////////////////////////////////////////////////////////////

    fun saveXAPIKey(key: String?) {
        mSharedPreferences!!.edit().putString(X_API_KEY, key).apply()

    }
    val apiKey: String?
        get() = mSharedPreferences!!.getString(X_API_KEY, "")
    ///////////////////////////////////////////////////////////////////

    companion object {
        const val X_API_KEY = "X_API_KEY"

    }

}