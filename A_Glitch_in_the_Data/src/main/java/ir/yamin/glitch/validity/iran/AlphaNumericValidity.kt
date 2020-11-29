package ir.yamin.glitch.validity.iran

import ir.yamin.glitch.RegexPatterns
import ir.yamin.glitch.validity.RegexValidity

internal class AlphaNumericValidity(string : String) : RegexValidity(string, RegexPatterns.ALPHA_NUMERIC) {}