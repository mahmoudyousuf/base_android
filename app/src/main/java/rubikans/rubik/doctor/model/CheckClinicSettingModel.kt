package rubikans.rubik.doctor.model

import com.google.gson.annotations.SerializedName

data class CheckClinicSettingModel(

	@field:SerializedName("CheckClinicSettingModel")
	val checkClinicSettingModel: ArrayList<CheckClinicSettingModelItem>? = null
)

data class CheckClinicSettingModelItem(

	@field:SerializedName("ColumnType")
	val columnType: Int? = null
)
