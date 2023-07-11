package rubikans.rubik.doctor.model

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

data class PatientsListModel(

	@field:SerializedName("PatientsListModel")
	val patientsListModel: ArrayList<PatientsListModelItem>? = null
)

data class PatientsListModelItem(

	@field:SerializedName("ProfileID")
	val profileID: Int? = null,

	@field:SerializedName("PatientProfileID")
	val patientProfileID: Int? = null,

	@field:SerializedName("ProfileMobile")
	val profileMobile: String? = null,

	@field:SerializedName("AssistantName")
	val assistantName: String? = null,

	@field:SerializedName("InsuranceCompanyName")
	val insuranceCompanyName: String? = null,

	@field:SerializedName("Count")
	val count: Int? = null,

	@field:SerializedName("PatientName")
	val patientName: String? = null,

	@field:SerializedName("SupProfilePatientID")
	val supProfilePatientID: Int? = null,

	@field:SerializedName("Gender")
	val gender: String? = null,

	@field:SerializedName("LastAppointment")
	val lastAppointment: String? = null,

	@field:SerializedName("NextAppointment")
	val nextAppointment: String? = null,

	@field:SerializedName("JoiningDate")
	val joiningDate: String? = null,

	@field:SerializedName("EntityBranchID")
	val entityBranchID: Int? = null,

	@field:SerializedName("BlodGroup")
	val blodGroup: Any? = null,

	@field:SerializedName("Age")
	val age: Int? = null,

	@field:SerializedName("ProfileEmail")
	val profileEmail: String? = null
){
	companion object {


		val CALLBACK = object : DiffUtil.ItemCallback<PatientsListModelItem>() {
			override fun areItemsTheSame(
				oldItem: PatientsListModelItem,
				newItem: PatientsListModelItem
			): Boolean {
				return oldItem.profileID === newItem.profileID
			}

			override fun areContentsTheSame(
				oldItem: PatientsListModelItem,
				newItem: PatientsListModelItem
			): Boolean =
				oldItem == newItem
		}
	}

}
