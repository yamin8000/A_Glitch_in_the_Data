package ir.yamin.glitch.validator

import ir.yamin.glitch.rules.Rule
import ir.yamin.glitch.rules.Rules
import ir.yamin.glitch.util.CONSTANTS.MINIMUM_MAXIMUM
import ir.yamin.glitch.util.CONSTANTS.RULE_COLLISION
import ir.yamin.glitch.util.RegexPatterns.WHITE_SPACE
import ir.yamin.glitch.validity.AlphaNumericValidity
import ir.yamin.glitch.validity.NotEmptyValidity
import ir.yamin.glitch.validity.RegexValidity
import ir.yamin.glitch.validity.iran.*
import ir.yamin.glitch.validity.length.LengthValidity
import ir.yamin.glitch.validity.length.MaximumValidity
import ir.yamin.glitch.validity.length.MinimumValidity
import ir.yamin.glitch.validity.numbers.DecimalValidity
import ir.yamin.glitch.validity.numbers.DigitValidity


/**
 * base class for Validators
 *
 */
abstract class DataValidator {
    
    protected var isMultiRule : Boolean = false
    private var isSingleGlobalRule : Boolean = false
    private var isMultipleGlobalRule : Boolean = false
    
    protected lateinit var singleRule : Rule
    
    protected var singleRules = mutableListOf<Rule>()
    
    private var isIgnoringWhiteSpace = false
    
    private var min : Int? = null
    private var max : Int? = null
    
    protected fun ruleCollision() {
        if (isSingleGlobalRule && (isMultiRule || isMultipleGlobalRule)) {
            throw IllegalStateException(RULE_COLLISION)
        }
    }
    
    /**
     * sets whether ignoring white space or not
     *
     * @param ignore true or false
     */
    open fun ignoreWhiteSpace(ignore : Boolean) = this.apply { isIgnoringWhiteSpace = ignore }
    
    /**
     * sets a single rule for all the elements
     *
     * @param rule single provided rule
     */
    open fun withRule(rule : Rule) = this.apply {
        isSingleGlobalRule = true
        singleRule = rule
    }
    
    /**
     * sets variable number of rules
     *
     * @param rule first rule
     * @param varRule variable number of rules
     */
    open fun withRule(rule : Rule, vararg varRule : Rule) = this.apply {
        isMultipleGlobalRule = true
        singleRules.addAll(varRule)
    }
    
    /**
     * sets list of rules
     *
     * @param rules
     */
    open fun withRule(rules : List<Rule>) = this.apply {
        isMultipleGlobalRule = true
        singleRules.addAll(rules)
    }
    
    /**
     * checks for validity of elements by provided rules
     *
     */
    open fun isValid() : Boolean {
        ruleCollision()
        
        when {
            isMultiRule -> checkMultiRule()
            isMultipleGlobalRule -> checkMultipleGlobalRule()
            isSingleGlobalRule -> checkSingleGlobalRule()
        }
        return false
    }
    
    /**
     * check a single rule for a single input string
     *
     * @param string input
     * @param rule rule to check
     * @return true if provided string match the provided rule
     */
    protected fun checkRule(string : String, rule : Rule) : Boolean {
        minMaxParadox(rule)
        val sanitizedInput = whiteSpaceSanitizer(string)
        return when (rule) {
            is Rules.PersianText -> isPersianText(sanitizedInput)
            is Rules.ContainsPersianText -> containsPersianText(sanitizedInput)
    
            is Rules.PersianNumber -> isPersianNumber(sanitizedInput)
            is Rules.ContainsPersianNumber -> containsPersianNumber(sanitizedInput)
    
            is Rules.ArabicNumber -> isArabicNumber(sanitizedInput)
            is Rules.ContainsArabicNumber -> containsArabicNumber(sanitizedInput)
    
            is Rules.Digit -> isDigit(sanitizedInput)
            is Rules.ContainsDigit -> containsDigit(sanitizedInput)
    
            is Rules.AlphaNumeric -> isAlphaNumeric(sanitizedInput)
            is Rules.ContainsAlphaNumeric -> containsAlphaNumeric(sanitizedInput)
    
            is Rules.Decimal -> isDecimal(sanitizedInput)
            is Rules.ContainsDecimal -> containsDecimal(sanitizedInput)
    
            is Rules.IranMobile -> isIranMobile(sanitizedInput)
            is Rules.ContainsIranMobile -> containsIranMobile(sanitizedInput)
    
            is Rules.IranNationalCode -> isIranNationalCode(sanitizedInput)
    
            is Rules.NotEmpty -> isNotEmpty(sanitizedInput)
    
            is Rules.Length.Exact -> isExactLength(sanitizedInput, rule.length)
            is Rules.Length.Max -> isMaxLength(sanitizedInput, rule.max)
            is Rules.Length.Min -> isMinLength(sanitizedInput, rule.min)
    
            is Rules.Regex -> RegexValidity(sanitizedInput, rule.pattern).isValid()
    
            is Rules.Custom -> rule.logic()
            
            else -> false
        }
    }
    
    /**
     * deletes white space
     *
     * @param string input string
     * @return a string with no white space
     */
    private fun whiteSpaceSanitizer(string : String) : String {
        return if (isIgnoringWhiteSpace) string.replace(Regex(WHITE_SPACE), "") else string
    }
    
    /**
     * checks whether provided min or max value are valid and logical
     * in other words min cannot be greater than max
     *
     * @param rule
     */
    private fun minMaxParadox(rule : Rule) {
        if (min != null && max != null && min!! > max!!) throw IllegalStateException(MINIMUM_MAXIMUM)
        else {
            if (rule is Rules.Length.Min) min = rule.min
            if (rule is Rules.Length.Max) max = rule.max
        }
    }
    
    /**
     * Check single global rule
     *
     */
    protected open fun checkSingleGlobalRule() {}
    
    /**
     * Check multiple global rule
     *
     */
    protected open fun checkMultipleGlobalRule() {}
    
    /**
     * Check multi rule
     *
     */
    protected open fun checkMultiRule() {}
    
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
        
        fun isNotEmpty(string : String) = NotEmptyValidity(string).isValid()
    }
}