package rubikans.rubik.doctor.model

import com.google.gson.annotations.SerializedName

data class InsuranceCompanyData(

	@field:SerializedName("InsuranceCompany")
	val insuranceCompany: ArrayList<InsuranceCompanyItem>? = null
)

data class InsuranceCompanyItem(

	@field:SerializedName("IsDeleted")
	val isDeleted: Boolean? = null,

	@field:SerializedName("CreatedByDoctorFk")
	val createdByDoctorFk: Int? = null,

	@field:SerializedName("InsuranceCompanyEmail")
	val insuranceCompanyEmail: String? = null,

	@field:SerializedName("IsActive")
	val isActive: Boolean? = null,

	@field:SerializedName("InsuranceCompany_MobileCode")
	val insuranceCompanyMobileCode: String? = null,

	@field:SerializedName("WebImage")
	val webImage: Any? = null,

	@field:SerializedName("CreateDate")
	val createDate: String? = null,

	@field:SerializedName("MainName")
	val mainName: String? = null,

	@field:SerializedName("InsuranceCompanyID")
	val insuranceCompanyID: Int? = null,

	@field:SerializedName("InsuranceCompanyNameEn")
	val insuranceCompanyNameEn: String? = null,

	@field:SerializedName("CreatedByPatientFk")
	val createdByPatientFk: Int? = null,

	@field:SerializedName("MobileImage")
	val mobileImage: Any? = null,

	@field:SerializedName("InsuranceCompanyPhone")
	val insuranceCompanyPhone: String? = null,

	@field:SerializedName("InsuranceCompanyAddress")
	val insuranceCompanyAddress: String? = null,

	@field:SerializedName("InsuranceCompanyNameAr")
	val insuranceCompanyNameAr: String? = null
)
