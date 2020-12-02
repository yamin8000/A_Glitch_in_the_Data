package ir.yamin.glitch.validity.numbers

import ir.yamin.glitch.util.RegexPatterns
import ir.yamin.glitch.validity.RegexValidity

internal class DigitValidity(string : String) : RegexValidity(string, RegexPatterns.DIGIT)