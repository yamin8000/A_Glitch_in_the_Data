package ir.yamin.glitch.validity.iran

import ir.yamin.glitch.util.RegexPatterns
import ir.yamin.glitch.validity.RegexValidity

internal class IranMobileValidity(string : String) :
    RegexValidity(string, RegexPatterns.IRAN_MOBILE_NUMBERS) {}