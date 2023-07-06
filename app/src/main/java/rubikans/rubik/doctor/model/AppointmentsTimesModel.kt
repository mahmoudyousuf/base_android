package rubikans.rubik.doctor.model

import com.google.gson.annotations.SerializedName

data class AppointmentsTimesModel(

	@field:SerializedName("AppointmentsTimesModel")
	val appointmentsTimesModel: ArrayList<AppointmentsTimesModelItem>? = null
)

data class AppointmentsTimesModelItem(

	@field:SerializedName("consultationServiceID")
	val consultationServiceID: Int? = null,

	@field:SerializedName("IsTimeSlot")
	val isTimeSlot: Boolean? = null,

	@field:SerializedName("EndTime")
	val endTime: String? = null,

	@field:SerializedName("IsAvaliable")
	val isAvaliable: Boolean? = null,

	@field:SerializedName("StartTime")
	val startTime: String? = null,

	@field:SerializedName("StartTimes")
	val startTimes: String? = null,

	@field:SerializedName("Fees")
	val fees: Any? = null,

	@field:SerializedName("BookingCount")
	val bookingCount: Int? = null,

	@field:SerializedName("TimeAvailable")
	val timeAvailable: Boolean? = null,

	@field:SerializedName("TimeSlotValue")
	val timeSlotValue: Int? = null,

	@field:SerializedName("EntityBranchID")
	val entityBranchID: Int? = null,

	@field:SerializedName("DaysStr")
	val daysStr: String? = null,

	@field:SerializedName("WeekDayName")
	val weekDayName: String? = null,

	@field:SerializedName("DayDate")
	val dayDate: String? = null,

	@field:SerializedName("EndTimes")
	val endTimes: String? = null
)
