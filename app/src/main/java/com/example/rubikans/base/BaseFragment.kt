package com.example.rubikans.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.rubikans.util.extensions.ifNull
import com.google.gson.JsonParser

abstract class BaseFragment<T : ViewDataBinding?> : Fragment() {
    lateinit var baseActivity: BaseActivity<*>
        private set
    private var mRootView: View? = null
    var viewDataBinding: T? = null
        private set

    val jsonParser = JsonParser()

    @LayoutRes
    abstract fun getLayoutId(): Int

    protected lateinit var navController: NavController

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*>) {
            val activity = context
            baseActivity = activity
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mRootView = viewDataBinding?.root
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding!!.lifecycleOwner = this
        viewDataBinding!!.executePendingBindings()
        try {
            navController = Navigation.findNavController(requireView())
        } catch (e: Exception) {

        }

    }

    fun hideKeyboard() {
        if (baseActivity != null) {
            baseActivity.hideKeyboard()
        }
    }

    val isNetworkConnected: Boolean
        get() = baseActivity != null && baseActivity.isNetworkConnected


    fun showToast(text: String?) {
        Toast.makeText(baseActivity, text.ifNull(), Toast.LENGTH_SHORT).show()
    }

    fun showDialogLoading() {
        baseActivity.showDialogLoading()
    }

    fun hideDialogLoading() {
        baseActivity.hideDialogLoading()
    }

    fun showSuccessSnackbar(text: String?) {
        baseActivity.showSuccessSnackbar(text)
    }

    fun showWarningSnackbar(text: String?) {
        baseActivity.showWarningSnackbar(text)
    }

    fun finish() {
        baseActivity.finish()
    }

    val supportFragmentManager: FragmentManager
        get() = baseActivity.supportFragmentManager

}