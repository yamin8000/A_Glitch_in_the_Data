package ir.yamin.glitch

import ir.yamin.glitch.rules.LengthRule
import ir.yamin.glitch.rules.RegexRule
import ir.yamin.glitch.rules.Rule
import ir.yamin.glitch.type.*

abstract class Validator {
    
    protected var isMultiRule = false
    private var isSingleGlobalRule = false
    private var isMultipleGlobalRule = false
    protected lateinit var singleRule : Rule
    protected var singleRules = mutableListOf<Rule>()
    protected var validity = true
    
    private fun ruleCollision() : Unit = throw IllegalStateException(
            "It's either multi rule or single rule, you cannot set them both!")
    
    open fun withRule(rule : Rule) = this.apply {
        isSingleGlobalRule = true
        singleRule = rule
    }
    
    open fun withRule(vararg varRule : Rule) = this.apply {
        isMultipleGlobalRule = true
        singleRules.addAll(varRule)
    }
    
    fun isValid() : Boolean {
        if (isSingleGlobalRule && isMultiRule) ruleCollision()
        return when {
            isMultiRule -> checkMultiRule()
            isSingleGlobalRule -> checkSingleGlobalRule()
            isMultipleGlobalRule -> checkMultipleGlobalRule()
            else -> false
        }
    }
    
    protected fun checkRule(string : String?, rule : Rule) : Boolean {
        return when (rule) {
            Rule.PersianText -> isPersianText(string)
            Rule.ContainsPersianText -> containsPersianText(string)
    
            Rule.PersianNumber -> isPersianNumber(string)
            Rule.ContainsPersianNumber -> containsPersianNumber(string)
    
            Rule.ArabicNumber -> isArabicNumber(string)
            Rule.ContainsArabicNumber -> containsArabicNumber(string)
    
            Rule.Digit -> isDigit(string)
            Rule.ContainsDigit -> containsDigit(string)
    
            Rule.AlphaNumeric -> isAlphaNumeric(string)
            Rule.ContainsAlphaNumeric -> containsAlphaNumeric(string)
    
            Rule.Decimal -> isDecimal(string)
            Rule.ContainsDecimal -> containsDecimal(string)
    
            Rule.IranMobile -> isIranMobile(string)
            Rule.ContainsIranMobile -> containsIranMobile(string)
    
            Rule.IranNationalCode -> isIranNationalCode(string)
    
            is LengthRule -> LengthRule().isValidLength(string, rule.length, rule.min, rule.max)
            is RegexRule -> false
            else -> false
        }
    }
    
    protected abstract fun checkSingleGlobalRule() : Boolean
    
    protected abstract fun checkMultipleGlobalRule() : Boolean
    
    protected abstract fun checkMultiRule() : Boolean
    
    companion object {
        
        fun isPersianText(string : String?) = PersianText(string).isValid()
        fun containsPersianText(string : String?) = PersianText(string).contains()
        fun findPersianText(string : String?) = PersianText(string).find()
        fun findAllPersianText(string : String?) = PersianText(string).findAll()
        
        fun isPersianNumber(string : String?) = PersianNumber(string).isValid()
        fun containsPersianNumber(string : String?) = PersianNumber(string).contains()
        fun findPersianNumber(string : String?) = PersianNumber(string).find()
        fun findAllPersianNumber(string : String?) = PersianNumber(string).findAll()
        
        fun isArabicNumber(string : String?) = ArabicNumber(string).isValid()
        fun containsArabicNumber(string : String?) = ArabicNumber(string).contains()
        fun findArabicNumber(string : String?) = ArabicNumber(string).find()
        fun findAllArabicNumber(string : String?) = ArabicNumber(string).findAll()
        
        fun isDigit(string : String?) = Digit(string).isValid()
        fun containsDigit(string : String?) = Digit(string).contains()
        fun findDigit(string : String?) = Digit(string).find()
        fun findAllDigit(string : String?) = Digit(string).findAll()
        
        fun isAlphaNumeric(string : String?) = AlphaNumeric(string).isValid()
        fun containsAlphaNumeric(string : String?) = AlphaNumeric(string).contains()
        fun findAlphaNumeric(string : String?) = AlphaNumeric(string).find()
        fun findAllAlphaNumeric(string : String?) = AlphaNumeric(string).findAll()
        
        fun isDecimal(string : String?) = Decimal(string).isValid()
        fun containsDecimal(string : String?) = Decimal(string).contains()
        fun findDecimal(string : String?) = Decimal(string).find()
        fun findAllDecimal(string : String?) = Decimal(string).findAll()
        
        fun isIranNationalCode(string : String?) = IranNationalCode(string).isValid()
        fun containsIranNationalCode(string : String?) = NotImplementedError()
        fun findIranNationalCode(string : String?) = NotImplementedError()
        fun findAllIranNationalCode(string : String?) = NotImplementedError()
        
        fun isIranMobile(string : String?) = IranMobile(string).isValid()
        fun containsIranMobile(string : String?) = IranMobile(string).contains()
        fun findIranMobile(string : String?) = IranMobile(string).find()
        fun findAllIranMobile(string : String?) = IranMobile(string).findAll()
    }
}