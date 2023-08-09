package rubikans.rubik.doctor.ui.main.splash

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import androidx.activity.viewModels

import androidx.appcompat.app.AppCompatActivity
import com.etamn.util.Status
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.base.BaseActivity
import rubikans.rubik.doctor.base.BaseResponse
import rubikans.rubik.doctor.databinding.ActivitySplashBinding
import rubikans.rubik.doctor.ui.auth.AuthActivity
import rubikans.rubik.doctor.ui.main.clinicBraches.ClinicBranchesActivity
import rubikans.rubik.doctor.ui.main.MainActivity
import rubikans.rubik.doctor.util.LocaleUtils
import rubikans.rubik.doctor.util.extensions.observe

import java.util.*


@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    val viewModel: SplashViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onTokenRefresh()

        if(dataManager.lang == "ar"){
            LocaleUtils.setLocale(Locale(LocaleUtils.LAN_ARABIC))
            LocaleUtils.updateConfig(
                this.application,
                resources.configuration
            )

        }else{
            LocaleUtils.setLocale(Locale(LocaleUtils.LAN_ENGLISH))
            LocaleUtils.updateConfig(
                this.application,
                resources.configuration
            )

        }


        Handler(Looper.getMainLooper()).postDelayed({
            if (dataManager.isLogin) {

//                val i = Intent(this, MainActivity::class.java)
//                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                startActivity(i)
//                this.finish()


                if(this.dataManager.clinic == null ){
                    startActivity(Intent(this@SplashActivity, ClinicBranchesActivity::class.java))
                    finish()

                }else{
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }


            } else {
                startActivity(Intent(this@SplashActivity, AuthActivity::class.java))
                finish()


            }
        }, 3000)


        setObservers()

    }

    companion object {
        fun starActivity(activity: AppCompatActivity) {
            val intent = Intent(activity, SplashActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            activity.startActivity(intent)
        }
    }


    private fun onTokenRefresh() {
        // Get updated InstanceID token.
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result != null && !TextUtils.isEmpty(task.result)) {
                        val token: String = task.result!!
                        Log.d(ContentValues.TAG, "Refreshed token: $token")
                        this.dataManager.saveFCMToken(token)

                        if(dataManager.isLogin){
                            viewModel.refreshFCMToken(token)
                        }
                    }
                }
            }
    }

    private fun setObservers() {



        observe(viewModel.refreshFCMTokenState)
        {
            when (it) {
                is Status.Loading -> {
                    this.showDialogLoading()
                }
                is Status.Success<*> -> {
                    this.hideDialogLoading()
                    val response = it.data as BaseResponse<BaseResponse.EmptyData>

                }
                is Status.Error -> {
                    this.hideDialogLoading()
                    this.showWarningSnackbar(it.message!!)

                }
            }
        }




    }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }




}