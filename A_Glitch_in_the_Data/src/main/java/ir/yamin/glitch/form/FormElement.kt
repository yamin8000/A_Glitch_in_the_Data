package ir.yamin.glitch.form

import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import ir.yamin.glitch.rules.Rule

class FormElement {
    
    private var rules = mutableListOf<Rule>()
    private var elementEditText : EditText
    private var elementInputLayout : TextInputLayout? = null
    private var error : String? = ""
    var isErrorEnabled = false
    private var hint : String? = ""
    var validity = false
    var name = ""
    var isInputLayout = false
    
    constructor(editText : EditText?, name : String) {
        if (editText == null) throw NullPointerException()
        elementEditText = editText
        this.name = name
    }
    
    constructor(editText : TextInputEditText?, name : String) {
        if (editText == null) throw NullPointerException()
        elementEditText = editText
        this.name = name
    }
    
    constructor(textInputLayout : TextInputLayout?, name : String) {
        if (textInputLayout == null || textInputLayout.editText == null) throw NullPointerException()
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
        if (elementInputLayout == null) elementEditText.error = error
        else elementInputLayout!!.error = error
    }
    
    fun clearError() {
        if (elementInputLayout == null) throw NullPointerException()
        if (isInputLayout) elementInputLayout!!.error = null
    }
    
    fun showHint() {
        elementEditText.hint = hint
    }
    
    
    fun setRule(rule : Rule) = this.apply { rules.add(rule) }
    
    //todo preventing user from adding zero rules
    fun setRule(vararg varRule : Rule) = this.apply { rules.addAll(varRule) }
    
    fun setRule(ruleList : MutableList<Rule>) = this.apply { rules.addAll(ruleList) }
    
    fun getEditText() = elementEditText
}