@file:Suppress("unused")

package ir.yamin.glitch.validator

import ir.yamin.glitch.rules.Rule

/**
 * String Validator class
 *
 */
class StringValidator : DataValidator() {
    
    private var strings : MutableList<String> = mutableListOf()
    private var stringsWithValidity : MutableMap<String?, Boolean> = mutableMapOf()
    private var stringWithRule = mutableMapOf<String, List<Rule>>()
    
    override fun ignoreWhiteSpace(ignore : Boolean) = this.apply {
        super.ignoreWhiteSpace(ignore)
    }
    
    /**
     * adds variable number of strings to check
     * at least one is needed
     *
     * @param string single string
     * @param varString variable number of strings in variable argument format | optional
     */
    fun add(string : String, vararg varString : String) = this.apply {
        strings.addAll(varString)
        strings.add(string)
    }
    
    /**
     * adds list of strings to check
     *
     * @param stringList list of strings
     */
    fun add(stringList : List<String>) = this.apply { strings.addAll(stringList) }
    
    /**
     * adds a single string with variable number of rules to check,
     * at least one rule is needed
     *
     * @param string single string
     * @param rule single rule
     * @param varRule variable number of rules | optional
     */
    fun addWithRule(string : String, rule : Rule, vararg varRule : Rule) = this.apply {
        stringWithRule[string] = listOf(rule).plus(varRule)
        isMultiRule = true
    }
    
    /**
     * adds a map of strings and rules consist of pair of string and rule to check
     *
     * @param stringAndRule map of strings with rules for them
     */
    fun addWithRule(stringAndRule : Map<String, Rule>) = this.apply {
        stringWithRule.putAll(stringAndRule.entries.associate { pair -> pair.key to listOf(pair.value) })
        isMultiRule = true
    }
    
    /**
     * adds a single string with a list of rules to check
     *
     * @param string single string
     * @param rules list of rules
     */
    fun addWithRule(string : String, rules : List<Rule>) = this.apply {
        stringWithRule[string] = rules
        isMultiRule = true
    }
    
    /**
     * returns added strings with their validity
     */
    fun giveMeMyStringValidity() = stringsWithValidity
    
    //todo refactoring methods
    override fun checkSingleGlobalRule() {
        for (string in strings) checkRuleHandler(string, singleRule)
    }
    
    override fun checkMultipleGlobalRule() {
        for (string in strings) for (rule in singleRules) checkRuleHandler(string, rule)
    }
    
    override fun checkMultiRule() {
        for ((string, rules) in stringWithRule) for (rule in rules) checkRuleHandler(string, rule)
    }
    
    private fun checkRuleHandler(string : String, rule : Rule) {
        val stringValidity = checkRule(string, rule)
        stringsWithValidity[string] = stringValidity
    }
    
    override fun isValid() : Boolean {
        super.isValid()
        
        var isValid = true
        
        for ((_, valid) in stringsWithValidity) {
            isValid = isValid && valid
        }
        return isValid
    }
}