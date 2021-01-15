package ir.yamin.glitch

import ir.yamin.glitch.rules.Rules
import ir.yamin.glitch.validator.StringValidator

fun main() {
    val stringValidator = StringValidator().addWithRule("111", Rules.Digit())
        .addWithRule("a1aa", Rules.Decimal())
    println(stringValidator.isValid())
}