package ir.yamin.glitch.rules

abstract class Rule { object PersianText : Rule()
    object ContainsPersianText : Rule()
    
    object PersianNumber : Rule()
    object ContainsPersianNumber : Rule()
    
    object ArabicNumber : Rule()
    object ContainsArabicNumber : Rule()
    
    object Digit : Rule()
    object ContainsDigit : Rule()
    
    object AlphaNumeric : Rule()
    object ContainsAlphaNumeric : Rule()
    
    object Decimal : Rule()
    object ContainsDecimal : Rule()
    
    object IranNationalCode : Rule()
    
    object IranMobile : Rule()
    object ContainsIranMobile : Rule()
    
    object Email : Rule()
    object ContainsEmail : Rule()
    
    object Url : Rule()
    object ContainsUrl : Rule()
}