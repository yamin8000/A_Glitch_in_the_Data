package ir.yamin.glitch.validity.iran

import ir.yamin.glitch.util.RegexPatterns
import ir.yamin.glitch.validity.RegexValidity

internal class ArabicNumberValidity(string : String) : RegexValidity(string, RegexPatterns.ARABIC_NUMBERS) {}