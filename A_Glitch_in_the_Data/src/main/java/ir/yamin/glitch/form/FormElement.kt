package ir.yamin.glitch.form

import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

/**
 * a Form element is consist of input field
 * which can be EditText, TextInputEditText, TextInputLayout
 * and element validity
 */
class FormElement {
    
    private var elementEditText : EditText? = null
    private var elementInputLayout : TextInputLayout? = null
    var error : String = ""
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
    
    /**
     * sets error message for element
     *
     * @param string error message
     */
    fun error(string : String) = this.apply {
        error = string
        isErrorEnabled = true
    }
    
    /**
     * sets hint message for element
     *
     * @param string hint message
     */
    fun hint(string : String) = this.apply { hint = string }
    
    /**
     * shows error message for element
     *
     */
    fun showError(errorMessage : String) {
        if (elementInputLayout == null) elementEditText!!.error = errorMessage
        else elementInputLayout!!.error = errorMessage
    }
    
    /**
     * clear error message
     *
     */
    internal fun clearError() {
        if (elementInputLayout == null) throw NullPointerException()
        if (isInputLayout) elementInputLayout!!.error = null
    }
    
    /**
     * shows hint message
     *
     */
    fun showHint() {
        if (elementEditText != null) elementEditText!!.hint = hint
    }
    
    /**
     * returns element EditText
     *
     */
    internal fun getEditText() = elementEditText
}