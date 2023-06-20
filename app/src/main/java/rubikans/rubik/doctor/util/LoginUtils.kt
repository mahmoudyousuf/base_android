package rubikans.rubik.doctor.util

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat

import rubikans.rubik.doctor.base.BaseApplication
import rubikans.rubik.doctor.ui.auth.AuthActivity
import java.util.*

object LoginUtils {

    fun logoutFromApp(showToast: Boolean = false, xApiKey: String? = null, msg: String = "") {
        val baseApplication = (getAppContext() as BaseApplication)


        val dataManager = baseApplication.dataManager!!
        var lang = dataManager.lang
        dataManager.clear()

        if(lang == "ar"){
            dataManager.saveLang(LocaleUtils.LAN_ARABIC)

        }else{
            dataManager.saveLang(LocaleUtils.LAN_ENGLISH)
        }


        if (showToast)
            ContextCompat.getMainExecutor(getAppContext()).execute {
                Toast.makeText(
                    getAppContext(),
                    msg,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }


        val intent = Intent(getAppContext(), AuthActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
        getAppContext().startActivity(intent)
    }


    fun getAppContext(): Context {
        return BaseApplication.instance.applicationContext
    }


}