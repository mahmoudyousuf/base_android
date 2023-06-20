package rubikans.rubik.doctor.custom_views

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import android.widget.FrameLayout
import androidx.core.content.withStyledAttributes
import com.google.android.material.textfield.TextInputLayout
import rubikans.rubik.doctor.R



class RubikEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {



    private var editText: EditText
    private var textInputLayout: TextInputLayout
    private var mOnTextChange: OnTextChange? = null

    interface OnTextChange {
        fun onTextChange(text: String)
    }

    fun setOnTextChange(listener: OnTextChange) {
        mOnTextChange = listener
    }

    var txtHint: String? = null
        set(value) {
            field = value
            textInputLayout.hint = value
        }



    fun isEmpty(): Boolean {
        return editText.text.toString().isBlank()
    }

    fun getText(): String {
        return editText.text.toString()
    }

    fun putText(text : String) {
        editText.setText(text)
    }


    fun txtHeight(height : Int) {
        textInputLayout.minimumHeight = height

    }





    fun setError(errorMessage : String) {
         textInputLayout.setError(errorMessage)
    }

    fun removeError() {
        textInputLayout.setError(null)
    }

    fun setText(text: String) {
        return editText.setText(text)
    }

    fun showDate(date:()->Unit){
        date()
    }
    fun setFocusable(){
        editText.isFocusable = false
        editText.isFocusableInTouchMode = false
    }
    fun setOnClickListenerEditText(clickListener: OnClickListener){
        editText.setOnClickListener(clickListener)
    }
    fun setLeftIcon(icon:Int){
        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, icon, 0)
        editText.compoundDrawablePadding = 10
    }
    fun setRightIcon(icon:Int){
        editText.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0)
        editText.compoundDrawablePadding = 10
    }
    var inputType: Int? = null
        set(value) {
            field = value
            editText.inputType = inputType!!
        }
    var passwordToggleEnabled: Boolean? = null
        set(value) {
            field = value

            if (passwordToggleEnabled!!) {

                textInputLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
            } else
                textInputLayout.endIconMode = TextInputLayout.END_ICON_NONE
        }



    init {


        inflate(context, R.layout.custom_edit_text, this)
        editText = findViewById(R.id.edit_text)
        textInputLayout = findViewById(R.id.filledTextField)

        context.withStyledAttributes(attrs, R.styleable.CustomEditText) {
            txtHint = getString(R.styleable.CustomEditText_hint_ed)
            passwordToggleEnabled =
                getBoolean(R.styleable.CustomEditText_passwordToggleEnabled, false)
            inputType = getInt(R.styleable.CustomEditText_txtInputType, 1)


        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textInputLayout.setError(null)

                if (mOnTextChange != null)
                    mOnTextChange?.onTextChange(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

    }







}