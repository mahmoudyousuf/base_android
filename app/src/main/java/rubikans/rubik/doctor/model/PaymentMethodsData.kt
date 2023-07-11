package rubikans.rubik.doctor.model

import com.google.gson.annotations.SerializedName

data class PaymentMethodsModel(

	@field:SerializedName("PaymentMethod")
	val paymentMethod: ArrayList<PaymentMethodItem>? = null
)

data class PaymentMethodItem(

	@field:SerializedName("PayMentMethodsNameAr")
	val payMentMethodsNameAr: String? = null,

	@field:SerializedName("IsDeleted")
	val isDeleted: Boolean? = null,

	@field:SerializedName("MainName")
	val mainName: String? = null,

	@field:SerializedName("IsActive")
	val isActive: Boolean? = null,

	@field:SerializedName("PayMentMethodsNameEn")
	val payMentMethodsNameEn: String? = null,

	@field:SerializedName("PaymentMethodsID")
	val paymentMethodsID: Int? = null,

	@field:SerializedName("OnlineStatusId")
	val onlineStatusId: Int? = null
)
