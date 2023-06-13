package rubikans.rubik.doctor.util.extensions

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.lang.reflect.Type
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


fun Any?.toJsonString(): String = Gson().toJson(this)


fun Any?.toJsonObject(): JsonObject = Gson().toJsonTree(this).asJsonObject

fun <T> Any?.toJsonArray(type: ArrayList<T>): JsonArray = Gson().toJsonTree(type).asJsonArray


inline fun <reified T> String.getListFromString(): ArrayList<T> {

    if (this.isNullOrEmpty())
        return ArrayList()

    return Gson().fromJson<ArrayList<T>>(this, object : TypeToken<ArrayList<T>>() {}.type)
}


fun <T> String?.toObjectFromJson(type: Type): T = Gson().fromJson(this, type)

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L?, body: (T) -> Unit) =
    liveData?.observe(this, Observer(body))

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}
@SuppressLint("SimpleDateFormat")
fun String.formatDate(inputDateFormat:String, outputDateFormat:String): String {
    return SimpleDateFormat(outputDateFormat).format(SimpleDateFormat(inputDateFormat).parse(this)!!)
}
fun Double.format(digits: Int) = "%.${digits}f".format(this)
fun Any?.format() = "%.${2}f".format(this.toString().toDouble())

fun Int.getFormattedNumberAccordingToLocal(): String {
    return String.format(Locale.getDefault(), "%d", this)
}

fun Float.getFormattedNumberAccordingToLocal(): String {
    return String.format(Locale.getDefault(), "%.2f", this)
}

fun Double?.getPriceAfterDiscount(percentage: Double?, max: Double?): Double {

    if (this == null) {
        return 0.0
    } else {

        return if (percentage == null) {
            this
        } else {

            val discount = ((this / 100) * percentage)
            if (max == null) {
                this - discount
            } else {
                if (max < discount) {
                    this - max
                } else {
                    this - discount
                }
            }
        }

    }

}


fun String.getFormattedNumber(): String {
    val format = DecimalFormat("0.#")
    return try {
        format.format(this.toDouble()).toString()
    } catch (e: Exception) {
        this
    }

}




fun Int.isColorDark(): Boolean {
    val darkness =
        1 - (0.299 * Color.red(this) + 0.587 * Color.green(this) + 0.114 * Color.blue(this)) / 255
    return darkness >= 0.5
}

fun File.getImageFilePart(name: String): MultipartBody.Part {
    return MultipartBody.Part.createFormData(
        name,
        this.name,
        this.asRequestBody("image/*".toMediaType())
    )
}

fun String?.getStringPart(): RequestBody {
    return this?.toRequestBody("text/plain".toMediaType())
        ?: "".toRequestBody("text/plain".toMediaType())
}


