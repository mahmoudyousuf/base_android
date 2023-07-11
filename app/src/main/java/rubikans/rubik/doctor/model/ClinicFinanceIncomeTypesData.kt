package rubikans.rubik.doctor.model

import com.google.gson.annotations.SerializedName

data class ClinicFinanceIncomeTypesData(

	@field:SerializedName("IncomeType")
	val incomeType: ArrayList<IncomeTypeItem>? = null
)

data class IncomeTypeItem(

	@field:SerializedName("IsDeleted")
	val isDeleted: Boolean? = null,

	@field:SerializedName("MainName")
	val mainName: String? = null,

	@field:SerializedName("CreatedBy")
	val createdBy: Any? = null,

	@field:SerializedName("IncomeTypeNameEn")
	val incomeTypeNameEn: String? = null,

	@field:SerializedName("IsActive")
	val isActive: Boolean? = null,

	@field:SerializedName("IncomeTypeID")
	val incomeTypeID: Int? = null,

	@field:SerializedName("CreateDate")
	val createDate: Any? = null,

	@field:SerializedName("IncomeTypeNameAr")
	val incomeTypeNameAr: String? = null
)
