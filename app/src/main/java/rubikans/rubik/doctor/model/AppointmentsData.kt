package rubikans.rubik.doctor.model

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

data class AppointmentsData(

	@field:SerializedName("Appointment")
	val appointment: ArrayList<AppointmentItem>? = null,

//	@field:SerializedName("AppointmentCount")
//	val appointmentCount: ArrayList<AppointmentCountItem>? = null
)

data class AppointmentItem(

	@field:SerializedName("ConsultationServiceID")
	val consultationServiceID: Int? = null,

	@field:SerializedName("BookingDate")
	val bookingDate: String? = null,

	@field:SerializedName("PatientProfileID")
	val patientProfileID: Int? = null,

	@field:SerializedName("ConfirmationToken")
	val confirmationToken: Any? = null,

	@field:SerializedName("PatientGender")
	val patientGender: Int? = null,

	@field:SerializedName("ExtraNumber")
	val extraNumber: String? = null,

	@field:SerializedName("Count")
	val count: Int? = null,

	@field:SerializedName("BookingSummery")
	val bookingSummery: Any? = null,

	@field:SerializedName("CreateDate")
	val createDate: String? = null,

	@field:SerializedName("AutomaticSystemActionType")
	val automaticSystemActionType: Any? = null,

	@field:SerializedName("BookingFees")
	val bookingFees: Any? = null,

	@field:SerializedName("PatientPayValue")
	val patientPayValue: Any? = null,

	@field:SerializedName("PatientEmail")
	val patientEmail: String? = null,

	@field:SerializedName("ClinicOnline")
	val clinicOnline: String? = null,

	@field:SerializedName("ReadyToStartVideo")
	val readyToStartVideo: Any? = null,

	@field:SerializedName("BookingReason")
	val bookingReason: String? = null,

	@field:SerializedName("ServiceType")
	val serviceType: String? = null,

	@field:SerializedName("PayMentMethodName")
	val payMentMethodName: String? = null,

	@field:SerializedName("MedicalServiceID")
	val medicalServiceID: Any? = null,

	@field:SerializedName("PatientProfileAddressID")
	val patientProfileAddressID: Any? = null,

	@field:SerializedName("PatientMobile")
	val patientMobile: String? = null,

	@field:SerializedName("CreatedBy")
	val createdBy: Any? = null,

	@field:SerializedName("StartTime")
	val startTime: String? = null,

	@field:SerializedName("PatientWork")
	val patientWork: Any? = null,

	@field:SerializedName("StatusName")
	val statusName: String? = null,

	@field:SerializedName("Duration")
	val duration: Int? = null,

	@field:SerializedName("PatientName")
	val patientName: String? = null,

	@field:SerializedName("Weight")
	val weight: Any? = null,

	@field:SerializedName("Athuntisty")
	val athuntisty: Any? = null,

	@field:SerializedName("SupProfileID")
	val supProfileID: Int? = null,

	@field:SerializedName("PatientAddress")
	val patientAddress: Any? = null,

	@field:SerializedName("EntityBranchID")
	val entityBranchID: Int? = null,

	@field:SerializedName("PatientID")
	val patientID: Int? = null,

	@field:SerializedName("BookingID")
	val bookingID: Int? = null,

	@field:SerializedName("StatusClass")
	val statusClass: String? = null,

	@field:SerializedName("PointRate")
	val pointRate: Any? = null,

	@field:SerializedName("PatientBirthDate")
	val patientBirthDate: String? = null,

	@field:SerializedName("IsAvailableOnlineBooking")
	val isAvailableOnlineBooking: Any? = null,

	@field:SerializedName("EndTime")
	val endTime: Any? = null,

	@field:SerializedName("ShowBookingSummery")
	val showBookingSummery: Any? = null,

	@field:SerializedName("IsOverBooking")
	val isOverBooking: Any? = null,

	@field:SerializedName("StatusColor")
	val statusColor: String? = null,

	@field:SerializedName("ProfileImage")
	val profileImage: String? = null,

	@field:SerializedName("PaymentType")
	val paymentType: Any? = null,

	@field:SerializedName("DoctorInsuranceFk")
	val doctorInsuranceFk: Any? = null,

	@field:SerializedName("BookingStatus")
	val bookingStatus: Int? = null,

	@field:SerializedName("Height")
	val height: Any? = null,

	@field:SerializedName("LookupPointID")
	val lookupPointID: Any? = null,

	@field:SerializedName("Age")
	val age: Int? = null,

	@field:SerializedName("ProfileID")
	val profileID: Int? = null,

	@field:SerializedName("BookingInsuranceFees")
	val bookingInsuranceFees: Any? = null,

	@field:SerializedName("IsFullPaied")
	val isFullPaied: Any? = null,

	@field:SerializedName("CurrencyID")
	val currencyID: Int? = null,

	@field:SerializedName("IsPaied")
	val isPaied: Any? = null,

	@field:SerializedName("InsuranceCompanyID")
	val insuranceCompanyID: Any? = null,

	@field:SerializedName("BookingNo")
	val bookingNo: Int? = null,

	@field:SerializedName("PaiedDate")
	val paiedDate: Any? = null,

	@field:SerializedName("PointAmount")
	val pointAmount: Any? = null,

	@field:SerializedName("PaymentMethodsID")
	val paymentMethodsID: Int? = null,

	@field:SerializedName("ConsultationServiceName")
	val consultationServiceName: String? = null,

	@field:SerializedName("BookingInsuranceRefNo")
	val bookingInsuranceRefNo: Any? = null
){
	companion object {


		val CALLBACK = object : DiffUtil.ItemCallback<AppointmentItem>() {
			override fun areItemsTheSame(
				oldItem: AppointmentItem,
				newItem: AppointmentItem
			): Boolean {
				return oldItem.bookingID === newItem.bookingID
			}

			override fun areContentsTheSame(
				oldItem: AppointmentItem,
				newItem: AppointmentItem
			): Boolean =
				oldItem == newItem
		}
	}

}

//data class AppointmentCountItem(
//
//	@field:SerializedName("BookingDate")
//	val bookingDate: String? = null,
//
//	@field:SerializedName("BookingStatus")
//	val bookingStatus: Int? = null,
//
//	@field:SerializedName("Count")
//	val count: Int? = null,
//
//	@field:SerializedName("StatusColor")
//	val statusColor: String? = null
//)
