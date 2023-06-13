package rubikans.rubik.doctor.base

import android.app.Application
import rubikans.rubik.doctor.data.shared.DataManager
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class BaseApplication : Application() {
    var dataManager: DataManager? = null

    companion object {
        lateinit var instance: BaseApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        dataManager = DataManager(applicationContext)
    }


}