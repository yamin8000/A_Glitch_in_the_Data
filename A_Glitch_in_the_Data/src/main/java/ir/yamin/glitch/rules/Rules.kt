package ir.yamin.glitch.rules

object Rules { object AlphaNumeric : Rule()
    object ArabicNumber : Rule()
    object ContainsAlphaNumeric : Rule()
    object ContainsArabicNumber : Rule()
    object ContainsDigit : Rule()
    object ContainsPersianNumber : Rule()
    object ContainsPersianText : Rule()
    object Decimal : Rule()
    object Digit : Rule()
    object PersianNumber : Rule()
    object PersianText : Rule()
    object ContainsDecimal : Rule()
    object IranNationalCode : Rule()
    object IranMobile : Rule()
    object ContainsIranMobile : Rule()
    object Email : Rule()
    object ContainsEmail : Rule()
    object Url : Rule()
    object ContainsUrl : Rule()
    
    object Length {
        
        class Exact(length : Int) : Rule() {
            
            internal var length = 0
            
            init {
                this.length = length
            }
        }
        
        class Min(minLength : Int) : Rule() {
            
            internal var min = 0
            
            init {
                this.min = minLength
            }
        }
        
        class Max(maxLength : Int) : Rule() {
            
            internal var max = 0
            
            init {
                this.max = maxLength
            }
        }
    }
}