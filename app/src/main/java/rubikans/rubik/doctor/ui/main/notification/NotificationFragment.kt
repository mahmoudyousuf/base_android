package rubikans.rubik.doctor.ui.main.notification

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.etamn.util.Status
import dagger.hilt.android.AndroidEntryPoint
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.base.BaseFragment
import rubikans.rubik.doctor.base.BaseResponse
import rubikans.rubik.doctor.databinding.FragmentNotificationBinding
import rubikans.rubik.doctor.model.NotificationsDataItem
import rubikans.rubik.doctor.ui.auth.AuthActivity
import rubikans.rubik.doctor.util.extensions.observe


import java.util.*

@AndroidEntryPoint
class NotificationFragment() : BaseFragment<FragmentNotificationBinding>() {

    lateinit var binding: FragmentNotificationBinding
    lateinit var adapter: NotificationsAdapter
    val viewModel: NotificationsViewModel by activityViewModels()



    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding!!

        binding.customBar.leftImage = R.drawable.back
        binding.customBar.hideRightIcon()
        binding.customBar.title.text = getString(R.string.notifications)

        binding.customBar.leftCardView.setOnClickListener {
            navController.navigateUp()
        }


        adapter = NotificationsAdapter(
            baseActivity, ArrayList(),
            onItemRead = { notifications ->
                viewModel.readSelectedNotifications(notifications.notificationID.toString())
            },
            onItemClicked = {
                when (it.notificationType.toString()) {
                    "1" -> {
                        viewModel.readSelectedNotifications(it.notificationID.toString())

//                        navController.navigate(NotificationFragmentDirections.openMedicalProfileFragment("from MainHome"))
                    }


                    "2" -> {

                        if(it.Id != null){
                            viewModel.readSelectedNotifications(it.notificationID.toString())

//                            navController.navigate(
//                                NotificationFragmentDirections.openAppointmentsDetailsFragment(
//                                    it.Id
//                                )
//                            )

                        }


                    }


                    else -> {


                    }
                }
            }
        )

        binding.asReadTv.setOnClickListener {
            viewModel.readAllNotifications()
        }

        binding.notificationList.adapter = adapter


        viewModel.getNotifications()

        setObservers()


    }


    private fun setObservers() {
        observe(viewModel.getNotificationsState)
        {
            when (it) {
                is Status.Loading -> {
                    baseActivity.showDialogLoading()
                }
                is Status.Success<*> -> {
                    baseActivity.hideDialogLoading()
                    val response = it.data as BaseResponse<ArrayList<NotificationsDataItem>>


                    adapter.setMyData(response.data!!)

                    viewModel.getNotificationCount()


                }
                is Status.Error -> {
                    baseActivity.hideDialogLoading()
                    baseActivity.showWarningSnackbar(it.message!!)

                }
                is Status.Unauthorized -> {
                    baseActivity.dataManager.saveIsLogin(false)
                    val i = Intent(baseActivity, AuthActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                    baseActivity.finish()
                    baseActivity.showWarningSnackbar(getString(R.string.please_login))
                }
            }
        }

        observe(viewModel.readAllNotificationsState)
        {
            when (it) {
                is Status.Loading -> {
                    baseActivity.showDialogLoading()
                }
                is Status.Success<*> -> {
                    baseActivity.hideDialogLoading()
                    val response = it.data as BaseResponse<BaseResponse.EmptyData>

                    viewModel.getNotifications()
                }
                is Status.Error -> {
                    baseActivity.hideDialogLoading()
                    baseActivity.showWarningSnackbar(it.message!!)

                }
                is Status.Unauthorized -> {
                    baseActivity.dataManager.saveIsLogin(false)
                    val i = Intent(baseActivity, AuthActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                    baseActivity.finish()
                    baseActivity.showWarningSnackbar(getString(R.string.please_login))
                }
            }
        }


        observe(viewModel.readSelectedNotificationsState)
        {
            when (it) {
                is Status.Loading -> {
                    baseActivity.showDialogLoading()
                }
                is Status.Success<*> -> {
                    baseActivity.hideDialogLoading()
                    val response = it.data as BaseResponse<BaseResponse.EmptyData>

                    viewModel.getNotifications()
                }
                is Status.Error -> {
                    baseActivity.hideDialogLoading()
                    baseActivity.showWarningSnackbar(it.message!!)

                }
                is Status.Unauthorized -> {
                    baseActivity.dataManager.saveIsLogin(false)
                    val i = Intent(baseActivity, AuthActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                    baseActivity.finish()
                    baseActivity.showWarningSnackbar(getString(R.string.please_login))
                }
            }
        }


        observe(viewModel.getNotificationCountState)
        {
            when (it) {
                is Status.Loading -> {
                    baseActivity.showDialogLoading()
                }
                is Status.Success<*> -> {
                    baseActivity.hideDialogLoading()
                    val response = it.data as BaseResponse<Int>


                    baseActivity.dataManager.saveNotificationsCount(response.data.toString())


                    if (response.data != 0) {


                        if(baseActivity.dataManager.lang == "ar"){
                            binding.notificationCountTxt.text = "لديك ${response.data.toString()} اشعارات غير مقروءه"
                        }else{
                            binding.notificationCountTxt.text = "You have ${response.data.toString()} unread notifications"
                        }


                    } else {

                        binding.notificationCountTxt.visibility = View.GONE
                        binding.asReadTv.visibility = View.GONE

                    }


//                        val badgeCount = 1
//                        ShortcutBadger.applyCount(context, badgeCount);


                }
                is Status.Error -> {
                    baseActivity.hideDialogLoading()
                    baseActivity.showWarningSnackbar(it.message!!)

                }
                is Status.Unauthorized -> {
                    baseActivity.dataManager.saveIsLogin(false)
                    val i = Intent(baseActivity, AuthActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                    baseActivity.finish()
                    baseActivity.showWarningSnackbar(getString(R.string.please_login))
                }
            }
        }



    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_notification
    }


}


