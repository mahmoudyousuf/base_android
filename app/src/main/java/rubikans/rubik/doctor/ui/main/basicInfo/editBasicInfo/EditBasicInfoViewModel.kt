package rubikans.rubik.doctor.ui.main.basicInfo.editBasicInfo

import android.content.ContentValues

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.etamn.util.SingleLiveEvent
import com.etamn.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel

import okhttp3.RequestBody
import rubikans.rubik.doctor.base.BaseViewModel
import rubikans.rubik.doctor.data.shared.DataManager
import rubikans.rubik.doctor.util.extensions.getImageFilePart

import java.io.File
import javax.inject.Inject


@HiltViewModel
class EditBasicInfoViewModel @Inject constructor(
    private val repository: EditBasicInfoRepository,
    val dataManager: DataManager

) : BaseViewModel(repository) {


    val profileImageFile = MutableLiveData<File?>()






    val updateUserInfoState = SingleLiveEvent<Status>()
    fun updateUserInfo(

        data: MutableMap<String, RequestBody>

    ) {

        Log.d(ContentValues.TAG, "multi part data: ${data}")
        performNetworkCall({

            repository.updateUserInfo(
                if (profileImageFile.value == null) null else profileImageFile.value?.getImageFilePart(
                    "ImagePath"
                )!!,
                data)
        }, updateUserInfoState)
    }




}