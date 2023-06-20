package rubikans.rubik.doctor.base


import android.annotation.TargetApi
import android.app.Dialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.util.extensions.ifNull
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonParser
import rubikans.rubik.doctor.data.shared.DataManager
import rubikans.rubik.doctor.util.LocaleUtils


private const val TAG = "BaseActivity"

abstract class BaseActivity<T : ViewDataBinding?> : AppCompatActivity() {

    val jsonParser = JsonParser()


    val REQUEST_PHONE_CODE: Int = 500

    var viewDataBinding: T? = null
        private set


    private var pd: Dialog? = null

    /**
     * @return layout resource id
     */

    @LayoutRes
    abstract fun getLayoutId(): Int


    init {
        LocaleUtils.updateConfig(this)
    }


    lateinit var dataManager: DataManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createCustomProgressDialog()
        dataManager = (application as BaseApplication).dataManager!!

        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )

        performDataBinding()

    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String?): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission!!) == PackageManager.PERMISSION_GRANTED
    }

    //  return InternetConnectionDetector.IsInternetAvailable(getApplicationContext());
    val isNetworkConnected: Boolean
        get() =//  return InternetConnectionDetector.IsInternetAvailable(getApplicationContext());
            true

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String?>?, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions!!, requestCode)
        }
    }

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        viewDataBinding?.executePendingBindings()
    }

    private fun createCustomProgressDialog() {
        this.let {
            pd = Dialog(it, R.style.DialogCustomTheme)
            pd?.setContentView(R.layout.progress_layout)
            pd?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            pd?.setCancelable(false)
        }
    }

    fun showDialogLoading() {
        pd?.let {
            if (!it.isShowing)
                it.show()
        }
    }

    fun hideDialogLoading() {
        pd?.let {
            if (it.isShowing)
                it.dismiss()
        }
    }


    fun showSuccessSnackbar(text: String?) {
        val snack: Snackbar =
            Snackbar.make(viewDataBinding?.root!!, text.ifNull(), Snackbar.LENGTH_LONG)
        snack.animationMode = Snackbar.ANIMATION_MODE_SLIDE
        snack.setBackgroundTint(resources.getColor(R.color.green)).show()
        val view = snack.view
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        view.layoutParams = params
        snack.show()

    }

    fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    fun showWarningSnackbar(text: String?) {

        val snack: Snackbar =
            Snackbar.make(viewDataBinding?.root!!, text.ifNull(), Snackbar.LENGTH_LONG)
        snack.animationMode = Snackbar.ANIMATION_MODE_SLIDE
        snack.setBackgroundTint(resources.getColor(R.color.orange)).show()
        val view = snack.view
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        view.layoutParams = params
        snack.show()
    }




}