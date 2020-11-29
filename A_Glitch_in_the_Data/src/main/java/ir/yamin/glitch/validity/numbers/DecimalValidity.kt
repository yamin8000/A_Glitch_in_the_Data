package ir.yamin.glitch.validity.numbers

import ir.yamin.glitch.RegexPatterns
import ir.yamin.glitch.validity.RegexValidity

internal class DecimalValidity(string : String) : RegexValidity(string, RegexPatterns.DECIMAL)