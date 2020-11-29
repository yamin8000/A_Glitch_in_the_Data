package ir.yamin.glitch.validator

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import ir.yamin.glitch.form.FormElement
import ir.yamin.glitch.rules.Rule


class FormValidator : DataValidator() {
    
    private var elements = mutableListOf<FormElement>()
    private var elementAndRule = mutableMapOf<FormElement, List<Rule>>()
    
    fun giveMeSomething() : MutableMap<String, Boolean> {
        val map = mutableMapOf<String, Boolean>()
        for (element in elements) map[element.name] = element.validity
        return map
    }
    
    fun addField(element : FormElement?, vararg varElement : FormElement?) = this.apply {
        if (element == null) throw NullPointerException()
        elements.add(element)
        varElement.forEach { element ->
            if (element == null) throw NullPointerException()
            elements.add(element)
        }
    }
    
    fun addField(elementList : List<FormElement?>) = this.apply {
        elementList.forEach { element ->
            if (element == null) throw NullPointerException()
            elements.add(element)
        }
    }
    
    fun addFieldWithRule(element : FormElement?, rule : Rule, vararg varRules : Rule) = this.apply {
        if (element == null) throw NullPointerException()
        val rules = mutableListOf<Rule>()
        rules.add(rule)
        if (varRules.isNotEmpty()) rules.addAll(varRules)
        elementAndRule[element] = rules
        isMultiRule = true
    }
    
    fun addFieldWithRule(elementAndRule : Map<FormElement?, Rule>) = this.apply {
        this.elementAndRule.putAll(elementAndRule.entries.associate { entry ->
            if (entry.key == null) throw NullPointerException()
            entry.key!! to listOf(entry.value)
        })
        isMultiRule = true
    }
    
    fun addFieldWithRule(element : FormElement?, rules : List<Rule>) = this.apply {
        if (element == null || rules.isEmpty()) throw NullPointerException()
        elementAndRule[element] = rules
        isMultiRule = true
    }
    
    //    fun addFieldWithRule(element : FormElement?, vararg varRules : Rule) = this.apply {
    //        if (element == null) throw NullPointerException()
    //        elementAndRule[element] = listOf(*varRules)
    //        isMultiRule = true
    //    }
    
    override fun withRule(rule : Rule) = this.apply {
        super.withRule(rule)
        for (element in elements) elementAndRule[element] = listOf(rule)
    }
    //todo preventing user from adding zero rules
    override fun withRule(vararg varRule : Rule) = this.apply {
        super.withRule(*varRule)
        for (element in elements) elementAndRule[element] = listOf(*varRule)
    }
    
    override fun checkSingleGlobalRule() = check()
    override fun checkMultipleGlobalRule() = check()
    override fun checkMultiRule() = check()
    
    private fun check() : Boolean {
        for (element in elementAndRule) {
            for (rule in element.value) {
                element.key.validity = checkRule(element.key.getEditText().value(), rule)
                validity = validity && element.key.validity
                if (!element.key.validity && element.key.isErrorEnabled) element.key.showError()
                if (element.key.validity && element.key.isInputLayout) element.key.clearError()
            }
        }
        return validity
    }
    
    fun activeCheck() {
        for (element in elementAndRule) {
            element.key.getEditText()?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s : CharSequence?, start : Int, count : Int, after : Int) {
                    /* no-op */
                }
    
                override fun onTextChanged(s : CharSequence?, start : Int, before : Int, count : Int) {
                    isValid()
                }
    
                override fun afterTextChanged(s : Editable?) {
                    isValid()
                }
    
            })
        }
    }
    
    private fun EditText.value() : String = this.text.toString()
}