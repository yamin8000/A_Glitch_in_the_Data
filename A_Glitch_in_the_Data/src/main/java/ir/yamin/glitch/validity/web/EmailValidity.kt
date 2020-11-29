package ir.yamin.glitch.validity.web

import ir.yamin.glitch.RegexPatterns
import ir.yamin.glitch.validity.RegexValidity

internal class EmailValidity(string : String) : RegexValidity(string, RegexPatterns.EMAIL)