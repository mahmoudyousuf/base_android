package rubikans.rubik.doctor.util


import android.content.res.Resources
import android.text.format.DateUtils
import android.util.Log
import java.net.URLConnection
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "CommonUtilities"

object CommonUtilities {


    fun longLog(str: String) {
        if (str.length > 4000) {
            Log.d(TAG, str.substring(0, 4000))
            longLog(str.substring(4000))
        } else Log.d(TAG, str)
    }

    fun getFirstOfLastMonth(): Long {

        val cal = Calendar.getInstance()
        cal[Calendar.HOUR_OF_DAY] = 0

        cal.clear(Calendar.MINUTE)
        cal.clear(Calendar.SECOND)
        cal.clear(Calendar.MILLISECOND)

        cal[Calendar.DAY_OF_MONTH] = 1
        System.out.println("Start of the month:       " + cal.time)
        System.out.println("... in milliseconds:      " + cal.timeInMillis)
        return cal.timeInMillis


    }

    fun getLastOfLastMonth(): Long {

        val cal = Calendar.getInstance()
        cal[Calendar.HOUR_OF_DAY] = 0

        cal.clear(Calendar.MINUTE)
        cal.clear(Calendar.SECOND)
        cal.clear(Calendar.MILLISECOND)

        cal.add(Calendar.MONTH, -1)
        System.out.println("Start of the next month:  " + cal.time)
        System.out.println("... in milliseconds:      " + cal.timeInMillis)

        return cal.timeInMillis - 86400000
    }


    fun getNumberWithout0(number: String): String {

        return if (number.first().toString() == "0") {
            number.drop(1)
        } else {
            number
        }
    }


    fun convertToDate(milli: Long): String? {
        val fmt = SimpleDateFormat("MMM dd", Locale.US)
        return fmt.format(milli)
    }

    fun convertToDay(milli: Long): String? {
        val fmt = SimpleDateFormat("MMM dd", Locale.US)
        return fmt.format(milli)
    }

    fun convertToFullDate(milli: Long): String? {
        val fmt = SimpleDateFormat("dd-MM-yyyy", Locale.US)
        return fmt.format(milli)
    }


    fun convertToTime(milli: Long): String? {
        val fmt = SimpleDateFormat("hh:mm a", Locale.US)
        return fmt.format(milli)
    }


    fun convertToFullDateFormat(milli: Long): String? {
        val fmt = SimpleDateFormat("dd-MM-yyyy' - 'hh:mm a", Locale.US)
        return fmt.format(milli)
    }

    fun convertToDateSearch(milli: Long): String? {
        val fmt = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return fmt.format(milli)
    }

    fun convertFullDateToMillisNew(oldTime: String?): Long? {

        val date = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US).parse(oldTime)
        val milliseconds = date.time
        return milliseconds
    }



    fun convertDateToMillis(oldTime: String?): Long? {

        val date = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(oldTime)
        val milliseconds = date.time
        return milliseconds
    }


    fun convertFullAllDateToFormattedDate(oldTime: String?): String? {

        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).parse(oldTime)
        val milliseconds = date.time
        val fmt = SimpleDateFormat("dd-MM-yyyy", Locale.US)
        return fmt.format(milliseconds)
    }


    fun convertStringToDate(oldTime: String?): String? {

        val date = SimpleDateFormat("dd-MM-yyyy", Locale.US).parse(oldTime)
        val milliseconds = date.time
        val fmt = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return fmt.format(milliseconds)
    }


    fun convertFullDateToFormattedDate(oldTime: String?): String? {

        val date = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.US).parse(oldTime)
        val milliseconds = date.time
        val fmt = SimpleDateFormat("dd-MM-yyyy", Locale.US)
        return fmt.format(milliseconds)
    }

    fun convertFullDateToFormattedDateTxt(oldTime: String?): String? {

        val date = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.US).parse(oldTime)
        val milliseconds = date.time
        val fmt = SimpleDateFormat("d MMMM - hh:mm a", Locale.US)
        return fmt.format(milliseconds)
    }


    fun convertTimeToFormattedTimeTxt(oldTime: String?): String? {

        val date = SimpleDateFormat("hh:mm:ss", Locale.US).parse(oldTime)
        val milliseconds = date.time
        val fmt = SimpleDateFormat("hh:mm a", Locale.US)
        return fmt.format(milliseconds)
    }


    fun convertFullDateToFormattedDateTxtWithoutTime(oldTime: String?): String? {

        val date = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.US).parse(oldTime)
        val milliseconds = date.time
        val fmt = SimpleDateFormat("d MMMM", Locale.US)
        return fmt.format(milliseconds)
    }

    fun convertFullDateToFormattedDate_(oldTime: String?): String? {

        val date = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US).parse(oldTime)
        val milliseconds = date.time
        val fmt = SimpleDateFormat("dd-MM-yyyy", Locale.US)
        return fmt.format(milliseconds)
    }

    fun convertFullDateToFormattedDateBackSlash(oldTime: String?): String? {
        val date = SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.US).parse(oldTime)
        val milliseconds = date.time
        val fmt = SimpleDateFormat("dd-MM-yyyy", Locale.US)
        return fmt.format(milliseconds)
    }



    fun convertFullDateToFormattedDatex(oldTime: String?): String? {

        val date = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.US).parse(oldTime)
        val milliseconds = date.time
        val fmt = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return fmt.format(milliseconds)
    }


    fun convertFullDateToFormattedDateToTime(oldTime: String?): String? {

        val date = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.US).parse(oldTime)
        val milliseconds = date.time
        val fmt = SimpleDateFormat("hh:mm a", Locale.US)
        return fmt.format(milliseconds)
    }


    fun convertTimeAndDateToRFullDateFormat(oldTime: String?): String? {

        val date = SimpleDateFormat("yyyy-MM-dd-hh:mm a", Locale.US).parse(oldTime)
        val milliseconds = date.time
        val fmt = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.US)
        return fmt.format(milliseconds)
    }

    fun convertFullTimeToTimeA(oldTime: String?): String? {

        val date = SimpleDateFormat("hh:mm:ss", Locale.US).parse(oldTime)
        val milliseconds = date.time
        val fmt = SimpleDateFormat("hh:mm a", Locale.US)
        return fmt.format(milliseconds)
    }

    fun convertFullDateToMillis(oldTime: String?): Long? {
        val formatter =
            SimpleDateFormat("yyyy-MM-dd", Locale.US)
        formatter.isLenient = false
        var oldDate: Date? = null
        try {
            oldDate = formatter.parse(oldTime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val oldMillis = oldDate!!.time
        return oldMillis
    }

    fun hasNavBar(resources: Resources): Boolean {
        val id = resources.getIdentifier("config_showNavigationBar", "bool", "android")
        return id > 0 && resources.getBoolean(id)
    }

    fun getRandomNumber(): Int {


        val rand = Random()
        val n = rand.nextInt(3)
        return n
    }

    fun isVideoFile(path: String?): Boolean {
        val mimeType = URLConnection.guessContentTypeFromName(path)
        return mimeType != null && mimeType.startsWith("video")
    }


    fun get_Date(myDate: String?): String {
        val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm a")
        var date: Date? = null
        try {
            date = sdf.parse(myDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return DateUtils.getRelativeTimeSpanString(
            date!!.time,
            System.currentTimeMillis(),
            DateUtils.SECOND_IN_MILLIS
        ).toString()
    }


}
