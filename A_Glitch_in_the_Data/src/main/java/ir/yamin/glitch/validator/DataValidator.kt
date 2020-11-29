package ir.yamin.glitch.validator

import ir.yamin.glitch.rules.CustomRule
import ir.yamin.glitch.rules.RegexRule
import ir.yamin.glitch.rules.Rule
import ir.yamin.glitch.rules.Rules
import ir.yamin.glitch.validity.RegexValidity
import ir.yamin.glitch.validity.iran.*
import ir.yamin.glitch.validity.length.LengthValidity
import ir.yamin.glitch.validity.length.MaximumValidity
import ir.yamin.glitch.validity.length.MinimumValidity
import ir.yamin.glitch.validity.numbers.DecimalValidity
import ir.yamin.glitch.validity.numbers.DigitValidity

abstract class DataValidator {
    
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
    
    protected fun checkRule(string : String, rule : Rule) : Boolean {
        return when (rule) {
            Rules.PersianText -> isPersianText(string)
            Rules.ContainsPersianText -> containsPersianText(string)
    
            Rules.PersianNumber -> isPersianNumber(string)
            Rules.ContainsPersianNumber -> containsPersianNumber(string)
    
            Rules.ArabicNumber -> isArabicNumber(string)
            Rules.ContainsArabicNumber -> containsArabicNumber(string)
    
            Rules.Digit -> isDigit(string)
            Rules.ContainsDigit -> containsDigit(string)
    
            Rules.AlphaNumeric -> isAlphaNumeric(string)
            Rules.ContainsAlphaNumeric -> containsAlphaNumeric(string)
    
            Rules.Decimal -> isDecimal(string)
            Rules.ContainsDecimal -> containsDecimal(string)
    
            Rules.IranMobile -> isIranMobile(string)
            Rules.ContainsIranMobile -> containsIranMobile(string)
    
            Rules.IranNationalCode -> isIranNationalCode(string)
    
            is Rules.Length.Exact -> isExactLength(string, rule.length)
            is Rules.Length.Max -> isMaxLength(string, rule.max)
            is Rules.Length.Min -> isMinLength(string, rule.min)
    
            is RegexRule -> RegexValidity(string, rule.pattern).isValid()
    
            is CustomRule -> rule.logic()
            else -> false
        }
    }
    
    protected abstract fun checkSingleGlobalRule() : Boolean
    
    protected abstract fun checkMultipleGlobalRule() : Boolean
    
    protected abstract fun checkMultiRule() : Boolean
    
    companion object {
        
        fun isPersianText(string : String) = PersianTextValidity(string).isValid()
        fun containsPersianText(string : String) = PersianTextValidity(string).contains()
        fun findPersianText(string : String) = PersianTextValidity(string).find()
        fun findAllPersianText(string : String) = PersianTextValidity(string).findAll()
        
        fun isPersianNumber(string : String) = PersianNumberValidity(string).isValid()
        fun containsPersianNumber(string : String) = PersianNumberValidity(string).contains()
        fun findPersianNumber(string : String) = PersianNumberValidity(string).find()
        fun findAllPersianNumber(string : String) = PersianNumberValidity(string).findAll()
        
        fun isArabicNumber(string : String) = ArabicNumberValidity(string).isValid()
        fun containsArabicNumber(string : String) = ArabicNumberValidity(string).contains()
        fun findArabicNumber(string : String) = ArabicNumberValidity(string).find()
        fun findAllArabicNumber(string : String) = ArabicNumberValidity(string).findAll()
        
        fun isDigit(string : String) = DigitValidity(string).isValid()
        fun containsDigit(string : String) = DigitValidity(string).contains()
        fun findDigit(string : String) = DigitValidity(string).find()
        fun findAllDigit(string : String) = DigitValidity(string).findAll()
        
        fun isAlphaNumeric(string : String) = AlphaNumericValidity(string).isValid()
        fun containsAlphaNumeric(string : String) = AlphaNumericValidity(string).contains()
        fun findAlphaNumeric(string : String) = AlphaNumericValidity(string).find()
        fun findAllAlphaNumeric(string : String) = AlphaNumericValidity(string).findAll()
        
        fun isDecimal(string : String) = DecimalValidity(string).isValid()
        fun containsDecimal(string : String) = DecimalValidity(string).contains()
        fun findDecimal(string : String) = DecimalValidity(string).find()
        fun findAllDecimal(string : String) = DecimalValidity(string).findAll()
        
        fun isIranNationalCode(string : String) = IranNationalCodeValidity(string).isValid()
        fun containsIranNationalCode(string : String) = NotImplementedError()
        fun findIranNationalCode(string : String) = NotImplementedError()
        fun findAllIranNationalCode(string : String) = NotImplementedError()
        
        fun isIranMobile(string : String) = IranMobileValidity(string).isValid()
        fun containsIranMobile(string : String) = IranMobileValidity(string).contains()
        fun findIranMobile(string : String) = IranMobileValidity(string).find()
        fun findAllIranMobile(string : String) = IranMobileValidity(string).findAll()
        
        fun isExactLength(string : String, exact : Int) = LengthValidity(string, exact).isValid()
        fun isMinLength(string : String, min : Int) = MinimumValidity(string, min).isValid()
        fun isMaxLength(string : String, max : Int) = MaximumValidity(string, max).isValid()
    }
}