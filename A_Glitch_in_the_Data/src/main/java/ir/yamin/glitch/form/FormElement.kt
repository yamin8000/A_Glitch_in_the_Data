package ir.yamin.glitch.form

import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class FormElement {
    
    private var elementEditText : EditText? = null
    private var elementInputLayout : TextInputLayout? = null
    private var error : String = ""
    var isErrorEnabled = false
    private var hint : String = ""
    var validity = false
    var name = ""
    var isInputLayout = false
    
    constructor(editText : EditText, name : String = "") {
        elementEditText = editText
        this.name = name
    }
    
    constructor(editText : TextInputEditText, name : String = "") {
        elementEditText = editText
        this.name = name
    }
    
    constructor(textInputLayout : TextInputLayout, name : String = "") {
        if (textInputLayout.editText == null) throw NullPointerException()
        elementEditText = textInputLayout.editText!!
        elementInputLayout = textInputLayout
        this.name = name
        isInputLayout = true
    }
    
    fun error(string : String) = this.apply {
        error = string
        isErrorEnabled = true
    }
    
    fun hint(string : String) = this.apply { hint = string }
    
    fun showError() {
        if (elementInputLayout == null) elementEditText!!.error = error
        else elementInputLayout!!.error = error
    }
    
    fun clearError() {
        if (elementInputLayout == null) throw NullPointerException()
        if (isInputLayout) elementInputLayout!!.error = null
    }
    
    fun showHint() {
        if (elementEditText != null) elementEditText!!.hint = hint
    }
    
    fun getEditText() = elementEditText
}