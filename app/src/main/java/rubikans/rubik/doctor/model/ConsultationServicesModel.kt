package rubikans.rubik.doctor.model

import com.google.gson.annotations.SerializedName

data class ConsultationServicesModel(

	@field:SerializedName("ConsultationServicesModel")
	val consultationServicesModel: ArrayList<ConsultationServicesModelItem>? = null
)

data class ConsultationServicesModelItem(

	@field:SerializedName("consultationServiceID")
	val consultationServiceID: Int? = null,

	@field:SerializedName("Fees")
	val fees: Any? = null,

	@field:SerializedName("ConsultationServiceName")
	val consultationServiceName: String? = null
)
