package ir.yamin.glitch

import ir.yamin.glitch.rules.Rule

class StringValidator : Validator() {
    
    private var strings : MutableList<String?> = mutableListOf()
    private var stringsWithValidity : MutableMap<String?, Boolean> = mutableMapOf()
    private var stringWithRule = mutableMapOf<String, List<Rule>>()
    
    fun addString(string : String?) = this.apply { strings.add(string.orEmpty()) }
    
    fun addString(vararg varString : String?) = this.apply {
        varString.mapTo(strings) { string -> string.orEmpty() }
    }
    
    fun addString(stringList : List<String?>) = this.apply {
        stringList.mapTo(strings) { string -> string.orEmpty() }
    }
    
    fun addStringWithRule(string : String?, rule : Rule) = this.apply {
        stringWithRule[string.orEmpty()] = listOf(rule)
        isMultiRule = true
    }
    
    fun addStringWithRule(stringAndRule : Map<String?, Rule>) = this.apply {
        stringWithRule.putAll(
                stringAndRule.entries.associate { pair -> pair.key.orEmpty() to listOf(pair.value) })
        isMultiRule = true
    }
    
    fun addStringWithRule(string : String?, rules : List<Rule>) = this.apply {
        stringWithRule[string.orEmpty()] = rules
        isMultiRule = true
    }
    
    fun addStringWithRule(string : String?, vararg rules : Rule) = this.apply {
        stringWithRule[string.orEmpty()] = rules.toList()
        isMultiRule = true
    }
    
    fun giveMeMyStrings() : MutableList<String?> = strings
    
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
    
    private fun checkRuleHandler(string : String?, rule : Rule) {
        val stringValidity = checkRule(string, rule)
        stringsWithValidity[string] = stringValidity
        validity = validity && stringValidity
    }
}