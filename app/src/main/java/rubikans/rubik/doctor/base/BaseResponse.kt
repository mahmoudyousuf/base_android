package rubikans.rubik.doctor.base

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(

    @field:SerializedName("success")
    var success: Boolean? = null,

    @field:SerializedName("status")
    var status: Boolean? = null,

    @SerializedName("message")
    @Expose
    val message: Any? = null,



    @SerializedName("error")
    @Expose
    val error: Any? = null,


    @SerializedName("error_description")
    @Expose
    val error_description: Any? = null,

    @SerializedName("access_token")
    @Expose
    val token: Any? = null,


    @SerializedName("code")
    @Expose
    val code: Int = 0,

    @SerializedName("data")
    @Expose
    var data: T? = null,

    ) {
    val isSuccessResponse: Boolean
        get() {

            return if (success != null) {
                success!!
            } else if (status != null) {
                status!!
            } else token != null
        }

    val messageResponse: String
        get() {

            return if (message.toString().isNullOrEmpty() || message.toString() == "null") {
                error_description.toString()
            }

            else {
                message.toString()
            }
        }


    data class EmptyData(val message: String = "")


}

