package rubikans.rubik.doctor.model

import com.google.gson.annotations.SerializedName

data class ClinicBranchesData(

	@field:SerializedName("ClinicBranchesData")
	val clinicBranchesData: ArrayList<ClinicBranchesDataItem>? = null
)

data class ClinicBranchesDataItem(

	@field:SerializedName("CountryID")
	val countryID: Int? = null,

	@field:SerializedName("UserActive")
	val userActive: Boolean? = null,

	@field:SerializedName("BranchLat")
	val branchLat: String? = null,

	@field:SerializedName("IsAvailableOnlineBooking")
	val isAvailableOnlineBooking: Any? = null,

	@field:SerializedName("DeliveryTimeInMinuts")
	val deliveryTimeInMinuts: Any? = null,

	@field:SerializedName("IsActive")
	val isActive: Boolean? = null,

	@field:SerializedName("BranchID")
	val branchID: Int? = null,

	@field:SerializedName("BranchBuildingNameAr")
	val branchBuildingNameAr: String? = null,

	@field:SerializedName("DeliveryFees")
	val deliveryFees: Any? = null,

	@field:SerializedName("BranchFloor")
	val branchFloor: String? = null,

	@field:SerializedName("CreateDate")
	val createDate: String? = null,

	@field:SerializedName("OpeninigTime")
	val openinigTime: Any? = null,

	@field:SerializedName("BranchStreetNameEn")
	val branchStreetNameEn: String? = null,

	@field:SerializedName("BranchImagePath")
	val branchImagePath: String? = null,

	@field:SerializedName("SpecialNotes")
	val specialNotes: Any? = null,

	@field:SerializedName("BranchBuildingNameEn")
	val branchBuildingNameEn: String? = null,

	@field:SerializedName("BranchBuldingNo")
	val branchBuldingNo: String? = null,

	@field:SerializedName("BranchNameEn")
	val branchNameEn: String? = null,

	@field:SerializedName("BranchLandMarkAn")
	val branchLandMarkAn: String? = null,

	@field:SerializedName("PaymentType")
	val paymentType: Any? = null,

	@field:SerializedName("IsTwintyFourHoursService")
	val isTwintyFourHoursService: Any? = null,

	@field:SerializedName("BranchName")
	val branchName: String? = null,

	@field:SerializedName("BranchLandMarkEn")
	val branchLandMarkEn: String? = null,

	@field:SerializedName("HasDelivery")
	val hasDelivery: Any? = null,

	@field:SerializedName("BranchNameAr")
	val branchNameAr: String? = null,

	@field:SerializedName("ProfileID")
	val profileID: Int? = null,

	@field:SerializedName("BranchEmail")
	val branchEmail: String? = null,

	@field:SerializedName("IsDeleted")
	val isDeleted: Boolean? = null,

	@field:SerializedName("ClosingTime")
	val closingTime: Any? = null,

	@field:SerializedName("IsTimeSlot")
	val isTimeSlot: Boolean? = null,

	@field:SerializedName("OnlineBookingStatus")
	val onlineBookingStatus: Int? = null,

	@field:SerializedName("BranchFullAddressAr")
	val branchFullAddressAr: String? = null,

	@field:SerializedName("BranchLang")
	val branchLang: String? = null,

	@field:SerializedName("AreaID")
	val areaID: Int? = null,

	@field:SerializedName("BranchStreetNameAr")
	val branchStreetNameAr: String? = null,

	@field:SerializedName("IsMain")
	val isMain: Any? = null,

	@field:SerializedName("AddressName")
	val addressName: String? = null,

	@field:SerializedName("UserBlock")
	val userBlock: Boolean? = null,

	@field:SerializedName("BranchPhoneCode")
	val branchPhoneCode: Any? = null,

	@field:SerializedName("BackgroundImage")
	val backgroundImage: Any? = null,

	@field:SerializedName("CityID")
	val cityID: Int? = null,

	@field:SerializedName("LicenceImagePath")
	val licenceImagePath: String? = null,

	@field:SerializedName("ProvideServiceInKm")
	val provideServiceInKm: Any? = null,

	@field:SerializedName("TimeSlotValue")
	val timeSlotValue: Int? = null,

	@field:SerializedName("EntityBranchID")
	val entityBranchID: Int? = null,

	@field:SerializedName("BranchAboutEn")
	val branchAboutEn: Any? = null,

	@field:SerializedName("BranchFullAddressEn")
	val branchFullAddressEn: String? = null,

	@field:SerializedName("BranchPhone")
	val branchPhone: String? = null
)
