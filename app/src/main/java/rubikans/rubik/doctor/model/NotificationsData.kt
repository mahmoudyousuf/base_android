package rubikans.rubik.doctor.model

import com.google.gson.annotations.SerializedName

data class NotificationsData(

	@field:SerializedName("NotificationsData")
	val notificationsData: ArrayList<NotificationsDataItem>? = null
)

data class NotificationsDataItem(

	@field:SerializedName("ProfileID")
	val profileID: Int? = null,

	@field:SerializedName("NotificationDetailsMessageAr")
	val notificationDetailsMessageAr: String? = null,

	@field:SerializedName("NotificationID")
	val notificationID: Int? = null,

	@field:SerializedName("ForDoctorID")
	val forDoctorID: Int? = null,

	@field:SerializedName("IsRead")
	val isRead: Boolean? = null,

	@field:SerializedName("LoginUserID")
	val loginUserID: String? = null,


	@field:SerializedName("Id")
	val Id: String? = null,

	@field:SerializedName("UserTypeID")
	val userTypeID: Any? = null,

	@field:SerializedName("NotificationDate")
	val notificationDate: String? = null,

	@field:SerializedName("NotificationMessage")
	val notificationMessage: String? = null,

	@field:SerializedName("NotificationType")
	val notificationType: Int? = null,

	@field:SerializedName("NotificationDetailsMessageEn")
	val notificationDetailsMessageEn: String? = null,

	@field:SerializedName("FromUserImageURL")
	val fromUserImageURL: Any? = null,

	@field:SerializedName("BusinessID")
	val businessID: Int? = null,

	@field:SerializedName("NotificationTitle")
	val notificationTitle: String? = null
)
