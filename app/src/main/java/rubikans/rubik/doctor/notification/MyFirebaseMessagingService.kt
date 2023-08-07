package rubikans.rubik.doctor.notification


import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONException
import org.json.JSONObject
import rubikans.rubik.doctor.base.BaseApplication
import rubikans.rubik.doctor.ui.main.MainActivity
import rubikans.rubik.doctor.util.extensions.toJsonString


class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val TAG = "MyFirebaseMsgService"

    private val baseApplication = (BaseApplication.instance.applicationContext as BaseApplication)
    val dataManager = baseApplication.dataManager!!

    override fun onNewToken(s: String) {
        super.onNewToken(s)
        //Displaying token on logcat
        Log.d("MyFirebaseIIDService", "Refreshed token: $s")
        //calling the method store token and passing token
        storeToken(s)

    }

    private fun storeToken(token: String) {
        // if u need save token local
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        if (remoteMessage.data.isNotEmpty()) {
            Log.e(TAG, "Data Payload: " + remoteMessage.data.toString())
            try {
                val json = JSONObject(remoteMessage.data.toJsonString())
                sendPushNotification(json, null, true)
            } catch (e: Exception) {
                Log.e(TAG, "Exception: " + e.message)
            }
        } else {
            Log.e(TAG, "notification Payload: " + remoteMessage.notification!!.body)
            sendPushNotification(null, remoteMessage.notification, false)

        }

    }


    private fun sendPushNotification(
        json: JSONObject?,
        notification: RemoteMessage.Notification?,
        fromData: Boolean
    ) {
        var title = ""
        var message = ""
        try {





            if (fromData) {
//                val data = json!!.getJSONObject("data")
                title = json!!.getString("title")
                message = json!!.getString("body")

                dataManager.saveNotificationType(json!!.getString("type"))
                dataManager.saveNotificationDirectionId(json!!.getString("id"))
                dataManager.saveNotificationIdID(json!!.getString("notificationId"))
//                Log.e(TAG, "testdatamanager " + dataManager.fcmToken)
//                Log.e(TAG, "testType " + json!!.getString("type"))

            } else {
                title = notification!!.title.toString()
                message = notification.body.toString()
            }

            //creating MyNotificationManager object
            val mNotificationManager = MyNotificationManager(applicationContext)

            //creating an intent for the notification

            val intent = Intent(applicationContext, MainActivity::class.java)
           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            intent.putExtra("NotificationMessage", message)
            intent.putExtra("isFromPushNotifications", "1")

            val pendingIntent =
                PendingIntent.getActivity(this, 0, intent,  PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)



            mNotificationManager.textNotification(title, message, intent)

        } catch (e: JSONException) {
            Log.e(TAG, "Json Exception: " + e.message)
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "Exception: " + e.message)
        }
    }


}