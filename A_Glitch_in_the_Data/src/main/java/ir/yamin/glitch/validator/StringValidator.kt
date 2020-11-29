package ir.yamin.glitch.validator

import ir.yamin.glitch.rules.Rule

class StringValidator : DataValidator() {
    
    private var strings : MutableList<String> = mutableListOf()
    private var stringsWithValidity : MutableMap<String?, Boolean> = mutableMapOf()
    private var stringWithRule = mutableMapOf<String, List<Rule>>()
    
    fun addString(string : String, vararg varString : String) = this.apply {
        strings.addAll(varString)
        strings.add(string)
    }
    
    fun addString(stringList : List<String>) = this.apply { strings.addAll(stringList) }
    
    fun addStringWithRule(string : String, rule : Rule, vararg varRule : Rule) = this.apply {
        stringWithRule[string] = listOf(rule).plus(varRule)
        isMultiRule = true
    }
    
    fun addStringWithRule(stringAndRule : Map<String, Rule>) = this.apply {
        stringWithRule.putAll(stringAndRule.entries.associate { pair -> pair.key to listOf(pair.value) })
        isMultiRule = true
    }
    
    fun addStringWithRule(string : String, rules : List<Rule>) = this.apply {
        stringWithRule[string] = rules
        isMultiRule = true
    }
    
    fun giveMeMyStrings() : MutableList<String> = strings
    
    fun giveMeMyMaps() : MutableMap<String, List<Rule>> = stringWithRule
    
    fun giveMeMyStringValidity() = stringsWithValidity
    
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