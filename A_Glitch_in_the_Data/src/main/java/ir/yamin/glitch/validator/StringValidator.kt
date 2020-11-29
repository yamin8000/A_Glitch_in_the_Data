package ir.yamin.glitch.validator

import ir.yamin.glitch.rules.Rule

class StringValidator : DataValidator() {
    
    private var strings : MutableList<String> = mutableListOf()
    private var stringsWithValidity : MutableMap<String?, Boolean> = mutableMapOf()
    private var stringWithRule = mutableMapOf<String, List<Rule>>()
    
    override fun ignoreWhiteSpace(ignore : Boolean) = this.apply {
        super.ignoreWhiteSpace(ignore)
    }
    
    fun add(string : String, vararg varString : String) = this.apply {
        strings.addAll(varString)
        strings.add(string)
    }
    
    fun add(stringList : List<String>) = this.apply { strings.addAll(stringList) }
    
    fun addWithRule(string : String, rule : Rule, vararg varRule : Rule) = this.apply {
        stringWithRule[string] = listOf(rule).plus(varRule)
        isMultiRule = true
    }
    
    fun addWithRule(stringAndRule : Map<String, Rule>) = this.apply {
        stringWithRule.putAll(stringAndRule.entries.associate { pair -> pair.key to listOf(pair.value) })
        isMultiRule = true
    }
    
    fun addWithRule(string : String, rules : List<Rule>) = this.apply {
        stringWithRule[string] = rules
        isMultiRule = true
    }
    
    //    fun giveMeMyStrings() : MutableList<String> = strings
    //
    //    fun giveMeMyMaps() : MutableMap<String, List<Rule>> = stringWithRule
    //
    //    fun giveMeMyStringValidity() = stringsWithValidity
    
    //todo refactoring methods
    override fun checkSingleGlobalRule() : Boolean {
        for (string in strings) checkRuleHandler(string, singleRule)
        return validity
    }
    
    override fun checkMultipleGlobalRule() : Boolean {
        for (string in strings) for (rule in singleRules) checkRuleHandler(string, rule)
        return validity
    }
    
    override fun checkMultiRule() : Boolean {
        for ((string, rules) in stringWithRule) for (rule in rules) checkRuleHandler(string, rule)
        return validity
    }
    
    private fun checkRuleHandler(string : String, rule : Rule) {
        val stringValidity = checkRule(string, rule)
        stringsWithValidity[string] = stringValidity
        validity = validity && stringValidity
    }
}