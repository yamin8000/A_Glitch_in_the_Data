package ir.yamin.glitch.validity.length

import ir.yamin.glitch.util.CONSTANTS.ZERO_LENGTH
import ir.yamin.glitch.validity.BaseValidity

internal class LengthValidity(input : String, private val exact : Int) : BaseValidity(input) {
    
    override fun isValid() : Boolean {
        if (exact < 0) throw IllegalArgumentException(ZERO_LENGTH)
        return input.length == exact
    }
}