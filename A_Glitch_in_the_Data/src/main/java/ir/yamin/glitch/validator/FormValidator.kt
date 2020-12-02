package ir.yamin.glitch.validator

import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import ir.yamin.glitch.form.FormElement
import ir.yamin.glitch.rules.Rule


/**
 * Form Validator class
 *
 */
class FormValidator : DataValidator() {
    
    private var elements = mutableListOf<FormElement>()
    private var elementAndRule = mutableMapOf<FormElement, List<Rule>>()
    private var submitButton : Button? = null
    
    override fun ignoreWhiteSpace(ignore : Boolean) = this.apply {
        super.ignoreWhiteSpace(ignore)
    }
    
    /**
     * method is for Debugging only
     *
     * @return a map consist of pair of element name and element validity
     */
    fun getFormState() : MutableMap<String, Boolean> {
        val map = mutableMapOf<String, Boolean>()
        for (element in elements) map[element.name] = element.validity
        return map
    }
    
    /**
     * adds variable number of elements to form without rule
     *
     * @param element single rule
     * @param varElement more rules in variable argument format
     */
    fun add(element : FormElement?, vararg varElement : FormElement?) = this.apply {
        if (element == null) throw NullPointerException()
        elements.add(element)
        varElement.forEach { element ->
            if (element == null) throw NullPointerException()
            elements.add(element)
        }
    }
    
    /**
     * adds list of elements to form without rule
     *
     * @param elementList lists of elements
     */
    fun add(elementList : List<FormElement?>) = this.apply {
        elementList.forEach { element ->
            if (element == null) throw NullPointerException()
            elements.add(element)
        }
    }
    
    /**
     * adds a single element with a variable number of rules
     *
     * @param element element with a rule
     * @param rule at least one rule is needed
     * @param rules more rules
     */
    fun addWithRule(element : FormElement?, rule : Rule, vararg rules : Rule) = this.apply {
        if (element == null) throw NullPointerException()
        elementAndRule[element] = listOf(rule).plus(rules)
        isMultiRule = true
    }
    
    /**
     * adds pair of element and rule
     *
     * @param elementAndRule a map consist of pairs of element and rule
     */
    fun addWithRule(elementAndRule : Map<FormElement?, Rule>) = this.apply {
        this.elementAndRule.putAll(elementAndRule.entries.associate { entry ->
            if (entry.key == null) throw NullPointerException()
            entry.key!! to listOf(entry.value)
        })
        isMultiRule = true
    }
    
    /**
     * adds a element with list of rules
     *
     * @param element single element
     * @param rules list of rules
     */
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
    
    /**
     * sets list of rules for all elements
     *
     * @param ruleList list of rules
     */
    fun withRule(ruleList : MutableList<Rule>) = this.apply {
        super.withRule(ruleList)
        for (element in elements) elementAndRule[element] = ruleList
    }
    
    override fun isValid() : Boolean {
        ruleCollision()
        
        var validity = true
        for (element in elementAndRule) {
            for (rule in element.value) {
                element.key.validity = checkRule(element.key.getEditText()?.value().orEmpty(), rule)
                validity = validity && element.key.validity
                createError(rule, element)
            }
        }
        if (submitButton != null) submitButton!!.isEnabled = validity
        return validity
    }
    
    private fun createError(rule : Rule, element : MutableMap.MutableEntry<FormElement, List<Rule>>) {
        val errorMessage = if (rule.error == "") element.key.error else rule.error
        if (!element.key.validity && element.key.isErrorEnabled) element.key.showError(errorMessage)
    }
    
    fun addSubmitButton(submitButton : Button) = this.apply {
        this.submitButton = submitButton
        this.submitButton!!.isEnabled = false
    }
    
    /**
     * when using active check:
     * anytime user is typing validation is checked for all elements
     * TODO checking only element that user is interacting with
     *
     */
    fun activeCheck() {
        var validity : Boolean
        for (element in elementAndRule) {
            element.key.getEditText()?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s : CharSequence?, start : Int, count : Int, after : Int) {
                    if (element.key.isInputLayout) element.key.clearError()
                }
    
                override fun onTextChanged(s : CharSequence?, start : Int, before : Int, count : Int) {
                    validity = isValid()
                    if (submitButton != null) submitButton!!.isEnabled = validity
                }
    
                override fun afterTextChanged(s : Editable?) {
                    validity = isValid()
                    if (submitButton != null) submitButton!!.isEnabled = validity
                }
            })
        }
    }
    
    private fun EditText.value() : String = this.text.toString()
}