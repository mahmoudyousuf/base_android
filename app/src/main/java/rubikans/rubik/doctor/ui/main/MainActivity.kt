package rubikans.rubik.doctor.ui.main


import android.content.*
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.Nullable
import androidx.core.view.isVisible
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.etamn.util.Status
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.base.BaseActivity
import rubikans.rubik.doctor.base.BaseResponse
import rubikans.rubik.doctor.databinding.ActivityMainBinding
import rubikans.rubik.doctor.model.ProfileData
import rubikans.rubik.doctor.ui.auth.AuthActivity
import rubikans.rubik.doctor.ui.main.home.HomeViewModel
import rubikans.rubik.doctor.util.Constants
import rubikans.rubik.doctor.util.extensions.observe
import rubikans.rubik.doctor.util.extensions.toJsonString


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController


    lateinit var broadcastReceiver: BroadcastReceiver



    val viewModel: HomeViewModel by viewModels()


    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(broadcastReceiver)

    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(broadcastReceiver, IntentFilter("UpdateProfileEvent"))
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(!dataManager.isLogin){
            showToast(getString(R.string.please_login))
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }
        binding = viewDataBinding!!
        navController = findNavController(R.id.fragment_home_nav)




        binding.bottomNavigation.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            val isTopLevelDestination =
                binding.bottomNavigation.menu.findItem(destination.id) != null
            binding.bottomNavigation.isVisible = isTopLevelDestination





        }

        binding.bottomNavigation.itemIconTintList = null




        viewModel.getProfile()
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                viewModel.getProfile()
            }
        }
        setObservers()


    }





    private fun setObservers() {

        observe(viewModel.getProfileState)
        {
            when (it) {
                is Status.Loading -> {
                    this.showDialogLoading()
                }
                is Status.Success<*> -> {
                    this.hideDialogLoading()
                    val response = it.data as BaseResponse<ProfileData>
                    this.dataManager.saveProfile(response.data.toJsonString())

//                    this.showWarningSnackbar(response.data!!.profileImage.toString());
                    Glide.with(applicationContext)
                        .asBitmap()
                        .placeholder( R.drawable.doctor)
                        .error( R.drawable.doctor)
                        .load(Constants.BASE_IMAGES_URL  + this.dataManager.profile!!.user!!.profileImage)
                        .apply(
                            RequestOptions.circleCropTransform()
                                .placeholder(R.drawable.doctor)
                        )
                        .into(object : CustomTarget<Bitmap?>() {

                            override fun onLoadCleared(@Nullable placeholder: Drawable?) {}
                            override fun onResourceReady(
                                resource: Bitmap,
                                transition: Transition<in Bitmap?>?
                            ) {
                                val bitmapDrawable: Drawable = BitmapDrawable(resources, resource)
                                binding.bottomNavigation.menu.findItem(R.id.ProfileFragment).icon =
                                    bitmapDrawable
                            }
                        })

                    LocalBroadcastManager.getInstance(this)
                        .sendBroadcast(Intent("UpdatePatientNameEvent"))

                }
                is Status.Error -> {
                    this.hideDialogLoading()
                    this.showWarningSnackbar(it.message!!)
                }
                is Status.Unauthorized -> {
                    this.dataManager.saveIsLogin(false)
                    val i = Intent(this, AuthActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                    this.finish()
                    this.showWarningSnackbar(getString(R.string.please_login))
                }
            }
        }

    }


    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }
}