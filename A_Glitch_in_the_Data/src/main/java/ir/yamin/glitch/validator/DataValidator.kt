package ir.yamin.glitch.validator

import ir.yamin.glitch.RegexPatterns.WHITE_SPACE
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
    private var isIgnoringWhiteSpace = false
    
    private var min : Int? = null
    private var max : Int? = null
    
    private fun ruleCollision() : Unit = throw IllegalStateException(
            "It's either multi rule or single rule, you cannot set them both!")
    
    open fun ignoreWhiteSpace(ignore : Boolean) = this.apply { isIgnoringWhiteSpace = ignore }
    
    open fun withRule(rule : Rule) = this.apply {
        isSingleGlobalRule = true
        singleRule = rule
    }
    
    open fun withRule(rule : Rule, vararg varRule : Rule) = this.apply {
        isMultipleGlobalRule = true
        singleRules.addAll(varRule)
    }
    
    open fun withRule(rules : List<Rule>) = this.apply {
        isMultipleGlobalRule = true
        singleRules.addAll(rules)
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
        minMaxParadox(rule)
        val sanitizedInput = if (isIgnoringWhiteSpace) string.replace(Regex(WHITE_SPACE), "") else string
        return when (rule) {
            Rules.PersianText -> isPersianText(sanitizedInput)
            Rules.ContainsPersianText -> containsPersianText(sanitizedInput)
    
            Rules.PersianNumber -> isPersianNumber(sanitizedInput)
            Rules.ContainsPersianNumber -> containsPersianNumber(sanitizedInput)
    
            Rules.ArabicNumber -> isArabicNumber(sanitizedInput)
            Rules.ContainsArabicNumber -> containsArabicNumber(sanitizedInput)
    
            Rules.Digit -> isDigit(sanitizedInput)
            Rules.ContainsDigit -> containsDigit(sanitizedInput)
    
            Rules.AlphaNumeric -> isAlphaNumeric(sanitizedInput)
            Rules.ContainsAlphaNumeric -> containsAlphaNumeric(sanitizedInput)
    
            Rules.Decimal -> isDecimal(sanitizedInput)
            Rules.ContainsDecimal -> containsDecimal(sanitizedInput)
    
            Rules.IranMobile -> isIranMobile(sanitizedInput)
            Rules.ContainsIranMobile -> containsIranMobile(sanitizedInput)
    
            Rules.IranNationalCode -> isIranNationalCode(sanitizedInput)
    
            is Rules.Length.Exact -> isExactLength(sanitizedInput, rule.length)
            is Rules.Length.Max -> isMaxLength(sanitizedInput, rule.max)
            is Rules.Length.Min -> isMinLength(sanitizedInput, rule.min)
    
            is RegexRule -> RegexValidity(sanitizedInput, rule.pattern).isValid()
    
            is CustomRule -> rule.logic()
            else -> false
        }
    }
    
    private fun minMaxParadox(rule : Rule) {
        if (min != null && max != null && min!! > max!!) {
            throw IllegalStateException("Minimum cannot be greater than Maximum!")
        } else {
            if (rule is Rules.Length.Min) min = rule.min
            if (rule is Rules.Length.Max) max = rule.max
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