package rubikans.rubik.doctor.ui.bottomSheets.selectInsuranceCompany

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import com.etamn.util.Status
import dagger.hilt.android.AndroidEntryPoint
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.base.BaseBottomSheet
import rubikans.rubik.doctor.base.BaseResponse
import rubikans.rubik.doctor.databinding.BottomSheetSelectInsuranceCompanyBinding
import rubikans.rubik.doctor.model.InsuranceCompanyData
import rubikans.rubik.doctor.model.InsuranceCompanyItem
import rubikans.rubik.doctor.ui.auth.AuthActivity
import rubikans.rubik.doctor.ui.main.addNewPatient.AddNewPatientViewModel
import rubikans.rubik.doctor.util.extensions.observe
import java.util.*
import kotlin.collections.ArrayList


private const val TAG = "SelectInsuranceCompanyBottomSheet"

@AndroidEntryPoint
class SelectInsuranceCompanyBottomSheet(
    private var viewModel: AddNewPatientViewModel

) : BaseBottomSheet<BottomSheetSelectInsuranceCompanyBinding>(),
    SearchView.OnQueryTextListener {
    lateinit var binding: BottomSheetSelectInsuranceCompanyBinding
    private var insuranceCompanyList: java.util.ArrayList<InsuranceCompanyItem> = arrayListOf()
    lateinit var adapter: SelectInsuranceCompanyAdapter


    override fun getLayoutId(): Int {
        return R.layout.bottom_sheet_select_insurance_company
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding!!



        adapter = SelectInsuranceCompanyAdapter(
            baseActivity.dataManager,
            baseActivity, java.util.ArrayList(),
            onItemSelected = { insurance ->

                viewModel.setSelectedInsuranceCompany(insurance)
                dismiss()
            },
        )

        binding.selectPatientList.adapter = adapter
        binding.searchView.setOnQueryTextListener(this)


        viewModel.getInsuranceCompanies()



        setObservers()
    }


    private fun setObservers() {


        observe(viewModel.getInsuranceCompaniesState)
        {
            when (it) {
                is Status.Loading -> {
                    baseActivity.showDialogLoading()
                }
                is Status.Success<*> -> {
                    baseActivity.hideDialogLoading()
                    val response = it.data as BaseResponse<InsuranceCompanyData>
                    insuranceCompanyList = response.data!!.insuranceCompany!!
                    adapter.setMyData(insuranceCompanyList)



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
        val newList: java.util.ArrayList<InsuranceCompanyItem> = arrayListOf()
        for (insurance in insuranceCompanyList) {
            if (insurance.mainName.toString().lowercase(Locale.getDefault()).contains(userInput.lowercase()) ) {
                newList.add(insurance)
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