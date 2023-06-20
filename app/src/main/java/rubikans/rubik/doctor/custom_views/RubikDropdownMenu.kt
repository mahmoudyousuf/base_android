package rubikans.rubik.doctor.custom_views

import android.content.Context
import android.util.AttributeSet
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.core.content.withStyledAttributes
import com.google.android.material.textfield.TextInputLayout
import rubikans.rubik.doctor.R


class RubikDropdownMenu @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {



    private var editText: EditText
    private var textInputLayout: TextInputLayout




    fun getText(): String {
        return editText.text.toString()
    }

    fun setOnClick(onClick: () -> Unit) {
        editText.setOnClickListener{
            onClick
        }
    }

    fun setOnDropDownClickListner( onClickListener: OnClickListener ) = editText.setOnClickListener(onClickListener)

    fun setText(txt : String) {
         editText.setText(txt)
    }

    fun setError(errorMessage : String?) {
        textInputLayout.setError(errorMessage)
    }

    fun removeError() {
        textInputLayout.setError(null)
    }

    var txtHint: String? = null
        set(value) {
            field = value
            textInputLayout.hint = value
        }


     fun setUp(context: Context,items: List<String>){
         val adapter = ArrayAdapter(context, R.layout.item_drop_down_list, items)
         (textInputLayout.editText as? AutoCompleteTextView)?.setAdapter(adapter)
     }

//    fun getSelectedITem(onItemSelectedListener:AdapterView.OnItemClickListener){
//        (textInputLayout.editText as? AutoCompleteTextView)?.onItemClickListener =  onItemSelectedListener
//    }

    fun getSelectedITem(selectedItem:(item:String)->Unit,items: List<String>){
        (textInputLayout.editText as AutoCompleteTextView?)!!.onItemClickListener =
            OnItemClickListener { adapterView, view, position, id ->
                selectedItem(items[position])
            }
    }


    init {


        inflate(context, R.layout.custom_drop_down_menu, this)
        editText = findViewById(R.id.dropdown_menu)
        textInputLayout = findViewById(R.id.filledTextField)

        context.withStyledAttributes(attrs, R.styleable.CustomEditText) {
            txtHint = getString(R.styleable.CustomEditText_hint_ed)

        }



    }







}