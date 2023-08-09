package rubikans.rubik.doctor.model

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

data class ClinicIncomeData(

	@field:SerializedName("ClinicIncomeData")
	val clinicIncomeData: ArrayList<ClinicIncomeDataItem>? = null
)

data class ClinicIncomeDataItem(

	@field:SerializedName("ProfileID")
	val profileID: Any? = null,

	@field:SerializedName("ClinicEarningID")
	val clinicEarningID: Int? = null,

	@field:SerializedName("IsDeleted")
	val isDeleted: Any? = null,

	@field:SerializedName("PatientProfileID")
	val patientProfileID: Int? = null,

	@field:SerializedName("IsActive")
	val isActive: Any? = null,

	@field:SerializedName("Count")
	val count: Int? = null,

	@field:SerializedName("PatientName")
	val patientName: String? = null,

	@field:SerializedName("Fees")
	val fees: Any? = null,

	@field:SerializedName("CreateDate")
	val createDate: String? = null,

	@field:SerializedName("ConsulationServiceID")
	val consulationServiceID: Int? = null,

	@field:SerializedName("EntityBranchID")
	val entityBranchID: Int? = null,

	@field:SerializedName("IncomeTypeID")
	val incomeTypeID: Int? = null,

	@field:SerializedName("BookingID")
	val bookingID: Int? = null,

	@field:SerializedName("MedicalServiceID")
	val medicalServiceID: Any? = null,

	@field:SerializedName("IsPaid")
	val isPaid: Any? = null,

	@field:SerializedName("IncomeTypeName")
	val incomeTypeName: String? = null,

	@field:SerializedName("ConsultationServiceName")
	val consultationServiceName: String? = null,


	@field:SerializedName("ServiceAndConsulationName")
	val ServiceAndConsulationName: String? = null,

	@field:SerializedName("Notes")
	val notes: Any? = null,

	@field:SerializedName("MedicalServiceName")
	val medicalServiceName: Any? = null
){
	companion object {


		val CALLBACK = object : DiffUtil.ItemCallback<ClinicIncomeDataItem>() {
			override fun areItemsTheSame(
				oldItem: ClinicIncomeDataItem,
				newItem: ClinicIncomeDataItem
			): Boolean {
				return oldItem.profileID === newItem.profileID
			}

			override fun areContentsTheSame(
				oldItem: ClinicIncomeDataItem,
				newItem: ClinicIncomeDataItem
			): Boolean =
				oldItem == newItem
		}
	}

}
