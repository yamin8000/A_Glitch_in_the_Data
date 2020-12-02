package ir.yamin.glitch.rules

/**
 * list of rules
 *
 */
object Rules {
    
    class AlphaNumeric(override val error : String = "") : Rule() {
        
        override val name = "Is Alpha Numeric"
    }
    
    class ArabicNumber(override val error : String = "") : Rule() {
        
        override val name = "Is Arabic Number"
    }
    
    class ContainsAlphaNumeric(override val error : String = "") : Rule() {
        
        override val name = "Contains Alpha Numeric"
    }
    
    class ContainsArabicNumber(override val error : String = "") : Rule() {
        
        override val name = "Contains Arabic Number"
    }
    
    class ContainsDigit(override val error : String = "") : Rule() {
        
        override val name = "Contains Digit"
    }
    
    class ContainsPersianNumber(override val error : String = "") : Rule() {
        
        override val name = "Contains Persian Number"
    }
    
    class ContainsPersianText(override val error : String = "") : Rule() {
        
        override val name = "Contains Persian Text"
    }
    
    class Decimal(override val error : String = "") : Rule() {
        
        override val name = "Is Decimal"
    }
    
    class Digit(override val error : String = "") : Rule() {
        
        override val name = "Is Digit"
    }
    
    class PersianNumber(override val error : String = "") : Rule() {
        
        override val name = "Is Persian Number"
    }
    
    class PersianText(override val error : String = "") : Rule() {
        
        override val name = "Is Persian Text"
    }
    
    class ContainsDecimal(override val error : String = "") : Rule() {
        
        override val name = "Contains Decimal"
    }
    
    class IranNationalCode(override val error : String = "") : Rule() {
        
        override val name = "Is Iran National Code"
    }
    
    class IranMobile(override val error : String = "") : Rule() {
        
        override val name = "Is Iranian Mobile Number"
    }
    
    class ContainsIranMobile(override val error : String = "") : Rule() {
        
        override val name = "Contains Iranian Mobile Number"
    }
    
    class NotEmpty(override val error : String = "") : Rule() {
        
        override val name = "Is Not Empty"
    }
    
    /**
     * Custom rule class which a custom logic can be applied
     *
     * @property logic is function which takes nothing as argument
     * and returns Boolean as output
     */
    class Custom(
        ruleName : String = "Custom Rule", override val error : String = "",
        val logic : () -> Boolean,
                ) : Rule() {
        
        override val name = ruleName
    }
    
    /**
     * Regex rule class
     *
     * @property pattern regex pattern
     */
    class Regex(val pattern : String, override val error : String = "") : Rule() {
        
        override val name = "Regex Rule"
    }
    
    /**
     * Length rules which are consist of:
     * @see Exact is true when length of input string is exactly equal to the length provided for rule
     * @see Min is true when length of input string is greater or equal than the minimum length provided for rule
     * @see Max is true when length of input string is less or equal than the maximum length provided for rule
     *
     */
    object Length {
        
        class Exact(length : Int, override val error : String = "") : Rule() {
            
            internal var length = 0
            
            init {
                this.length = length
            }
            
            override val name = "Exact Length of $length Rule"
        }
        
        class Min(minLength : Int, override val error : String = "") : Rule() {
            
            internal var min = 0
            
            init {
                this.min = minLength
            }
            
            override val name = "Minimum Length of $minLength Rule"
        }
        
        class Max(maxLength : Int, override val error : String = "") : Rule() {
            
            internal var max = 0
            
            init {
                this.max = maxLength
            }
            
            override val name = "Maximum Length of $maxLength Rule"
        }
    }
}