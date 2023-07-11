package rubikans.rubik.doctor.model

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

data class ClinicExpenseData(

	@field:SerializedName("ClinicExpenseData")
	val clinicExpenseData: ArrayList<ClinicExpenseDataItem>? = null
)

data class ClinicExpenseDataItem(

	@field:SerializedName("ProfileID")
	val profileID: Any? = null,

	@field:SerializedName("ExpenseTypeName")
	val expenseTypeName: String? = null,

	@field:SerializedName("ClinicExpenseID")
	val clinicExpenseID: Int? = null,

	@field:SerializedName("IsDeleted")
	val isDeleted: Boolean? = null,

	@field:SerializedName("CreatedBy")
	val createdBy: Int? = null,

	@field:SerializedName("EntityBranchID")
	val entityBranchID: Int? = null,

	@field:SerializedName("IsActive")
	val isActive: Boolean? = null,

	@field:SerializedName("Count")
	val count: Int? = null,

	@field:SerializedName("ExpenseTypeID")
	val expenseTypeID: Any? = null,

	@field:SerializedName("Fees")
	val fees: Any? = null,

	@field:SerializedName("CreateDate")
	val createDate: String? = null,

	@field:SerializedName("Notes")
	val notes: String? = null
){
	companion object {


		val CALLBACK = object : DiffUtil.ItemCallback<ClinicExpenseDataItem>() {
			override fun areItemsTheSame(
				oldItem: ClinicExpenseDataItem,
				newItem: ClinicExpenseDataItem
			): Boolean {
				return oldItem.profileID === newItem.profileID
			}

			override fun areContentsTheSame(
				oldItem: ClinicExpenseDataItem,
				newItem: ClinicExpenseDataItem
			): Boolean =
				oldItem == newItem
		}
	}

}
