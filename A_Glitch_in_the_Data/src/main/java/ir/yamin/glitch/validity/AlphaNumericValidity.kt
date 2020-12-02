package ir.yamin.glitch.validity

import ir.yamin.glitch.util.RegexPatterns

internal class AlphaNumericValidity(string : String) : RegexValidity(string, RegexPatterns.ALPHA_NUMERIC) {}