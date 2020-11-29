package ir.yamin.glitch.validator

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import ir.yamin.glitch.form.FormElement
import ir.yamin.glitch.rules.Rule


class FormValidator : DataValidator() {
    
    private var elements = mutableListOf<FormElement>()
    private var elementAndRule = mutableMapOf<FormElement, List<Rule>>()
    
    override fun ignoreWhiteSpace(ignore : Boolean) = this.apply {
        super.ignoreWhiteSpace(ignore)
    }
    
    fun giveMeSomething() : MutableMap<String, Boolean> {
        val map = mutableMapOf<String, Boolean>()
        for (element in elements) map[element.name] = element.validity
        return map
    }
    
    fun add(element : FormElement?, vararg varElement : FormElement?) = this.apply {
        if (element == null) throw NullPointerException()
        elements.add(element)
        varElement.forEach { element ->
            if (element == null) throw NullPointerException()
            elements.add(element)
        }
    }
    
    fun add(elementList : List<FormElement?>) = this.apply {
        elementList.forEach { element ->
            if (element == null) throw NullPointerException()
            elements.add(element)
        }
    }
    
    fun addWithRule(element : FormElement?, rule : Rule, vararg rules : Rule) = this.apply {
        if (element == null) throw NullPointerException()
        elementAndRule[element] = listOf(rule).plus(rules)
        isMultiRule = true
    }
    
    fun addWithRule(elementAndRule : Map<FormElement?, Rule>) = this.apply {
        this.elementAndRule.putAll(elementAndRule.entries.associate { entry ->
            if (entry.key == null) throw NullPointerException()
            entry.key!! to listOf(entry.value)
        })
        isMultiRule = true
    }
    
    fun addWithRule(element : FormElement?, rules : List<Rule>) = this.apply {
        if (element == null || rules.isEmpty()) throw NullPointerException()
        elementAndRule[element] = rules
        isMultiRule = true
    }
    
    override fun withRule(rule : Rule) = this.apply {
        super.withRule(rule)
        for (element in elements) elementAndRule[element] = listOf(rule)
    }
    
    override fun withRule(rule : Rule, vararg varRule : Rule) = this.apply {
        val listOfAllRules = listOf(rule).plus(varRule)
        super.withRule(listOfAllRules)
        for (element in elements) elementAndRule[element] = listOfAllRules
    }
    
    fun withRule(ruleList : MutableList<Rule>) = this.apply {
        super.withRule(ruleList)
        for (element in elements) elementAndRule[element] = ruleList
    }
    
    override fun checkSingleGlobalRule() = check()
    override fun checkMultipleGlobalRule() = check()
    override fun checkMultiRule() = check()
    
    private fun check() : Boolean {
        for (element in elementAndRule) {
            for (rule in element.value) {
                element.key.validity = checkRule(element.key.getEditText()?.value().orEmpty(), rule)
                validity = validity && element.key.validity
                if (!element.key.validity && element.key.isErrorEnabled) element.key.showError()
                //if (element.key.validity && element.key.isInputLayout) element.key.clearError()
            }
        }
        return validity
    }
    
    fun activeCheck() {
        for (element in elementAndRule) {
            element.key.getEditText()?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s : CharSequence?, start : Int, count : Int, after : Int) {
                    /* no-op */
                    if (element.key.isInputLayout) element.key.clearError()
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