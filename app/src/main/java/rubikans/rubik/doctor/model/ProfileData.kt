package rubikans.rubik.doctor.model

import com.google.gson.annotations.SerializedName

data class ProfileData(

	@field:SerializedName("Role")
	val role: ArrayList<String>? = null,

	@field:SerializedName("User")
	val user: User? = null
)

data class User(

	@field:SerializedName("ProfileNameAr")
	val profileNameAr: String? = null,

	@field:SerializedName("FullProfisionalDetailsEn")
	val fullProfisionalDetailsEn: String? = null,

	@field:SerializedName("ProfileMobile")
	val profileMobile: String? = null,

	@field:SerializedName("MaritalStatusID")
	val maritalStatusID: Any? = null,

	@field:SerializedName("SpecialiytName")
	val specialiytName: String? = null,

	@field:SerializedName("ProfisionalDetailID")
	val profisionalDetailID: Any? = null,

	@field:SerializedName("Gender")
	val gender: Int? = null,

	@field:SerializedName("WorkingHour")
	val workingHour: String? = null,

	@field:SerializedName("DiseaseIDStr")
	val diseaseIDStr: String? = null,

	@field:SerializedName("CreatedIn")
	val createdIn: Any? = null,

	@field:SerializedName("PrefixTitleID")
	val prefixTitleID: Any? = null,

	@field:SerializedName("ClinicName")
	val clinicName: String? = null,

	@field:SerializedName("FullProfisionalDetailsAr")
	val fullProfisionalDetailsAr: String? = null,

	@field:SerializedName("ForgetPasswordtToken")
	val forgetPasswordtToken: Any? = null,

	@field:SerializedName("DateOfBirth")
	val dateOfBirth: String? = null,

	@field:SerializedName("TokenDateTime")
	val tokenDateTime: Any? = null,

	@field:SerializedName("DiseaseName")
	val diseaseName: Any? = null,

	@field:SerializedName("SpecialityID")
	val specialityID: Int? = null,

	@field:SerializedName("ProfisionalDetailsName")
	val profisionalDetailsName: Any? = null,

	@field:SerializedName("CountryIDStr")
	val countryIDStr: String? = null,

	@field:SerializedName("Weight")
	val weight: Any? = null,

	@field:SerializedName("ProfileEntityNameAr")
	val profileEntityNameAr: Any? = null,

	@field:SerializedName("TempPassword")
	val tempPassword: String? = null,

	@field:SerializedName("BackgroundImage")
	val backgroundImage: String? = null,

	@field:SerializedName("EntityTypeID")
	val entityTypeID: Any? = null,

	@field:SerializedName("GraduationTypeName")
	val graduationTypeName: Any? = null,

	@field:SerializedName("ProfileEntityNameEn")
	val profileEntityNameEn: Any? = null,

	@field:SerializedName("BussinessID")
	val bussinessID: Int? = null,

	@field:SerializedName("AssistantPermission")
	val assistantPermission: Any? = null,

	@field:SerializedName("TokenCode")
	val tokenCode: String? = null,

	@field:SerializedName("ClinicAddress")
	val clinicAddress: String? = null,

	@field:SerializedName("ProfileName")
	val profileName: String? = null,

	@field:SerializedName("WorkID")
	val workID: Any? = null,

	@field:SerializedName("OtherProviderTypeID")
	val otherProviderTypeID: Any? = null,

	@field:SerializedName("SupSpecialityIDStr")
	val supSpecialityIDStr: String? = null,

	@field:SerializedName("BlodGroupID")
	val blodGroupID: Any? = null,

	@field:SerializedName("AthuntistyID")
	val athuntistyID: Any? = null,

	@field:SerializedName("ProfileImage")
	val profileImage: String? = null,

	@field:SerializedName("Height")
	val height: Any? = null,

	@field:SerializedName("PrefixTitleName")
	val prefixTitleName: Any? = null,

	@field:SerializedName("ProfileEmail")
	val profileEmail: String? = null,

	@field:SerializedName("GenderName")
	val genderName: String? = null,

	@field:SerializedName("ProfileID")
	val profileID: Int? = null,

	@field:SerializedName("AttachedIDPath")
	val attachedIDPath: Any? = null,

	@field:SerializedName("ProfilePhoneCode")
	val profilePhoneCode: Any? = null,

	@field:SerializedName("ProfileAboutEn")
	val profileAboutEn: String? = null,

	@field:SerializedName("ClinicLocation")
	val clinicLocation: String? = null,

	@field:SerializedName("HasChild")
	val hasChild: Any? = null,

	@field:SerializedName("ProfileAboutAr")
	val profileAboutAr: String? = null,

	@field:SerializedName("CurrencyIDStr")
	val currencyIDStr: Any? = null,

	@field:SerializedName("ProfileNameEn")
	val profileNameEn: String? = null,

	@field:SerializedName("SubSpecialityName")
	val subSpecialityName: String? = null,

	@field:SerializedName("EducationID")
	val educationID: Any? = null
)
