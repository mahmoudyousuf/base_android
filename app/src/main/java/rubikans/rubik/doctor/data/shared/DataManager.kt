package rubikans.rubik.doctor.data.shared

import android.content.Context
import android.content.SharedPreferences

import rubikans.rubik.doctor.util.LocaleUtils
import rubikans.rubik.doctor.util.extensions.toObjectFromJson

class DataManager(context: Context?) {

    var mSharedPreferences: SharedPreferences? = null


    init {
        mSharedPreferences = context?.getSharedPreferences("RubikansPreferences", 0)
    }


    fun clear() {
        mSharedPreferences!!.edit().clear().apply()
    }

    ///////////////////////////////////////////////////////////////////


    fun saveToken(x: String) {
        mSharedPreferences!!.edit().putString(TOKEN, x).apply()
    }

    val token: String?
        get() = mSharedPreferences!!.getString(TOKEN, "")


    ///////////////////////////////////////////////////////////////////


    fun saveFCMToken(x: String) {
        mSharedPreferences!!.edit().putString(FCMTOKEN, x).apply()
    }

    val fcmToken: String?
        get() = mSharedPreferences!!.getString(FCMTOKEN, "")


    ///////////////////////////////////////////////////////////////////



    fun saveIsLogin(x: Boolean) {
        mSharedPreferences!!.edit().putBoolean(LOGIN, x).apply()
    }

    val isLogin: Boolean
        get() = mSharedPreferences!!.getBoolean(LOGIN, false)

    ///////////////////////////////////////////////////////////////////

    fun saveLoginData(x: String) {
        mSharedPreferences!!.edit().putString(Login, x).apply()
    }



    ///////////////////////////////////////////////////////////////////

    fun saveClinicData(x: String) {
        mSharedPreferences!!.edit().putString(Clinic, x).apply()
    }

    fun patientFound(x: Boolean) {
        mSharedPreferences!!.edit().putBoolean(PatientFounded, x).apply()
    }

    val isPatientFound: Boolean
        get() = mSharedPreferences!!.getBoolean(PatientFounded, false)

    ///////////////////////////////////////////////////////////////////


    ///////////////////////////////////////////////////////////////////


//
//    fun saveProfile(x: String) {
//        mSharedPreferences!!.edit().putString(Profile, x).apply()
//    }
//
//
//    fun deleteProfile() {
//        mSharedPreferences!!.edit().remove(Profile).apply()
//    }
//
//    val profile: ProfileData?
//        get() {
//            return mSharedPreferences!!.getString(Profile, "")
//                .toObjectFromJson(ProfileData::class.java)
//        }

    ////////////////////////////////////////////////////////////////////////////////



//    fun saveProfile(x: String) {
//        mSharedPreferences!!.edit().putString(Profile, x).apply()
//    }
//
//
//    fun deleteProfile() {
//        mSharedPreferences!!.edit().remove(Profile).apply()
//    }
//
//    val profile: PatientInfoData?
//        get() {
//            return mSharedPreferences!!.getString(Profile, "")
//                .toObjectFromJson(PatientInfoData::class.java)
//        }

    ////////////////////////////////////////////////////////////////////////////////

    fun saveLang(lang: String?) {
        mSharedPreferences!!.edit().putString(LANG, lang).apply()

    }

    val lang: String?
        get() = mSharedPreferences!!.getString(LANG, LocaleUtils.LAN_ENGLISH)

    ////////////////////////////////////////////////////////////////////////////////

    fun saveNotificationType(type: String?) {
        mSharedPreferences!!.edit().putString(NOFICATIONTYPE, type).apply()

    }

    val notificationType: String?
        get() = mSharedPreferences!!.getString(NOFICATIONTYPE, "0")

    ////////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////////////////////////////

    fun saveNotificationDirectionId(id: String?) {
        mSharedPreferences!!.edit().putString(NOFICATIONDIRECTIONID, id).apply()

    }

    val notificationDirectionId: String?
        get() = mSharedPreferences!!.getString(NOFICATIONDIRECTIONID, "0")

    ////////////////////////////////////////////////////////////////////////////////



    fun saveNotificationId(id: String?) {
        mSharedPreferences!!.edit().putString(NOFICATIOND, id).apply()

    }

    val notificationId: String?
        get() = mSharedPreferences!!.getString(NOFICATIOND, "0")

    ////////////////////////////////////////////////////////////////////////////////



    fun saveNotificationIdID(id: String?) {
        mSharedPreferences!!.edit().putString(NOFICATIONDID, id).apply()

    }

    val notificationIdID: String?
        get() = mSharedPreferences!!.getString(NOFICATIONDID, "0")

    ////////////////////////////////////////////////////////////////////////////////



    fun saveIsFromNotifications(id: String?) {
        mSharedPreferences!!.edit().putString(ISFROMNOTIFICTIONS, id).apply()

    }

    val IsFromNotifications: String?
        get() = mSharedPreferences!!.getString(ISFROMNOTIFICTIONS, "0")

    ////////////////////////////////////////////////////////////////////////////////


    fun saveNotificationsCount(count: String?) {
        mSharedPreferences!!.edit().putString(NOTIFICATIONSCOUNT, count).apply()

    }

    val notificationsCount: String?
        get() = mSharedPreferences!!.getString(NOTIFICATIONSCOUNT, "0")

    ////////////////////////////////////////////////////////////////////////////////



    ///////////////////////////////////////////////////////////////////



//    fun saveClinic(x: String) {
//        mSharedPreferences!!.edit().putString(CLINIC, x).apply()
//    }
//
//
//    fun deleteClinic() {
//        mSharedPreferences!!.edit().remove(CLINIC).apply()
//    }
//
//    val clinic: ClinicBranchesDataItem?
//        get() {
//            return mSharedPreferences!!.getString(CLINIC, "")
//                .toObjectFromJson(ClinicBranchesDataItem::class.java)
//        }

    ////////////////////////////////////////////////////////////////////////////////

    fun saveIsRefreshHome(x: String) {
        mSharedPreferences!!.edit().putString(REFRESHHOME, x).apply()
    }

    val refreshHome: String?
        get() = mSharedPreferences!!.getString(REFRESHHOME, "0")


    ///////////////////////////////////////////////////////////////////




    companion object {
        const val Login = "Login"
        const val PatientFounded = "PatientFounded"
        const val Clinic = "Clinic"
        const val USER = "USER"
        const val Profile = "Profile"
        const val CLINIC = "CLINIC"
        const val TOKEN = "TOKEN"
        const val REFRESHHOME = "REFRESHHOME"
        const val FCMTOKEN = "FCMTOKEN"
        const val LOGIN = "LOGIN"
        const val LANG = "LANG"
        const val NOFICATIONTYPE = "NOFICATIONTYPE"
        const val NOFICATIOND = "NOFICATIOND"
        const val NOFICATIONDID = "NOFICATIOND"
        const val ISFROMNOTIFICTIONS = "ISFROMNOTIFICTIONS"
        const val NOTIFICATIONSCOUNT = "NOTIFICATIONSCOUNT"
        const val NOFICATIONDIRECTIONID = "NOFICATIONDIRECTIONID"



    }

}