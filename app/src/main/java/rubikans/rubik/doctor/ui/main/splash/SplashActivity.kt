package rubikans.rubik.doctor.ui.main.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper


import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.base.BaseActivity
import rubikans.rubik.doctor.databinding.ActivitySplashBinding
import rubikans.rubik.doctor.ui.auth.AuthActivity
import rubikans.rubik.doctor.ui.main.MainActivity

import java.util.*


@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        Handler(Looper.getMainLooper()).postDelayed({
            if (dataManager.isLogin) {


                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()

            } else {
                startActivity(Intent(this@SplashActivity, AuthActivity::class.java))
                finish()


            }
        }, 3000)



    }

    companion object {
        fun starActivity(activity: AppCompatActivity) {
            val intent = Intent(activity, SplashActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            activity.startActivity(intent)
        }
    }




    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }




}