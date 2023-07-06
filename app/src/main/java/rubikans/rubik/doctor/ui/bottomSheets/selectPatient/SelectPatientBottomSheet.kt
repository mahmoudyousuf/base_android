package rubikans.rubik.doctor.ui.bottomSheets.selectPatient

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import com.etamn.util.Status
import dagger.hilt.android.AndroidEntryPoint
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.base.BaseBottomSheet
import rubikans.rubik.doctor.base.BaseResponse
import rubikans.rubik.doctor.databinding.BottomSheetConfirmationBinding
import rubikans.rubik.doctor.databinding.BottomSheetSelectPatientBinding
import rubikans.rubik.doctor.model.SelectPatientModelItem
import rubikans.rubik.doctor.ui.auth.AuthActivity
import rubikans.rubik.doctor.ui.main.makeAppointment.MakeAppointmentViewModel
import rubikans.rubik.doctor.util.LoginUtils
import rubikans.rubik.doctor.util.extensions.observe
import java.util.*
import kotlin.collections.ArrayList


private const val TAG = "SelectPatientBottomSheet"

@AndroidEntryPoint
class SelectPatientBottomSheet(
    private var viewModel: MakeAppointmentViewModel

) : BaseBottomSheet<BottomSheetSelectPatientBinding>(),
    SearchView.OnQueryTextListener {
    lateinit var binding: BottomSheetSelectPatientBinding
    private var patientList: java.util.ArrayList<SelectPatientModelItem> = arrayListOf()
    lateinit var adapter: SelectPatientAdapter


    override fun getLayoutId(): Int {
        return R.layout.bottom_sheet_select_patient
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding!!



        adapter = SelectPatientAdapter(
            baseActivity.dataManager,
            baseActivity, java.util.ArrayList(),
            onItemSelected = { patient ->

                viewModel.setSelectedPatient(patient)
                dismiss()
            },
        )

        binding.selectPatientList.adapter = adapter
        binding.searchView.setOnQueryTextListener(this)


        viewModel.getSelectPatientList(baseActivity.dataManager.clinic!!.entityBranchID.toString())



        setObservers()
    }


    private fun setObservers() {


        observe(viewModel.getSelectPatientListState)
        {
            when (it) {
                is Status.Loading -> {
                    baseActivity.showDialogLoading()
                }
                is Status.Success<*> -> {
                    baseActivity.hideDialogLoading()
                    val response = it.data as BaseResponse<ArrayList<SelectPatientModelItem>>
                    patientList = response.data!!
                    adapter.setMyData(patientList)



                }
                is Status.Error -> {
                    baseActivity.hideDialogLoading()
                    baseActivity.showWarningSnackbar(it.message!!)
                }
                is Status.Unauthorized -> {
                    baseActivity.dataManager.saveIsLogin(false)
                    val i = Intent(baseActivity, AuthActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                    baseActivity.finish()
                    baseActivity.showWarningSnackbar(getString(R.string.please_login))
                }
            }
        }



    }


    override fun onQueryTextSubmit(query: String): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        val userInput = newText.lowercase(Locale.getDefault())
        val newList: java.util.ArrayList<SelectPatientModelItem> = arrayListOf()
        for (city in patientList) {
            if (city.profileName.toString().lowercase(Locale.getDefault()).contains(userInput.lowercase()) || city.profileMobile.toString().lowercase(Locale.getDefault()).contains(userInput.lowercase())) {
                newList.add(city)
            }
        }
        if (newList.isEmpty()) {
            baseActivity.showWarningSnackbar(getString(R.string.no_match_found))
            adapter.search(newList)
            return true
        }
        adapter.search(newList)
        return true

    }


}