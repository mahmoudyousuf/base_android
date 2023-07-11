package rubikans.rubik.doctor.model

import com.google.gson.annotations.SerializedName

data class ClinicFinanceExpenseTypesData(

	@field:SerializedName("ExpenseType")
	val expenseType: ArrayList<ExpenseTypeItem>? = null
)

data class ExpenseTypeItem(

	@field:SerializedName("IsDeleted")
	val isDeleted: Boolean? = null,

	@field:SerializedName("MainName")
	val mainName: String? = null,

	@field:SerializedName("CreatedBy")
	val createdBy: Any? = null,

	@field:SerializedName("ExpenseTypeNameAr")
	val expenseTypeNameAr: String? = null,

	@field:SerializedName("IsActive")
	val isActive: Boolean? = null,

	@field:SerializedName("ExpenseTypeID")
	val expenseTypeID: Int? = null,

	@field:SerializedName("ExpenseTypeNameEn")
	val expenseTypeNameEn: String? = null,

	@field:SerializedName("CreateDate")
	val createDate: String? = null
)
