package ir.yamin.glitch.validity.iran

import ir.yamin.glitch.util.RegexPatterns
import ir.yamin.glitch.validity.RegexValidity

internal class PersianTextValidity(string : String) : RegexValidity(string, RegexPatterns.PERSIAN_ALPHABET)