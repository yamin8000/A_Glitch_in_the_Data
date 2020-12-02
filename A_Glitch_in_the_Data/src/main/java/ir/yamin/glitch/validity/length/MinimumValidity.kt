package ir.yamin.glitch.validity.length

import ir.yamin.glitch.util.CONSTANTS.ZERO_MIN
import ir.yamin.glitch.validity.BaseValidity

internal class MinimumValidity(input : String, private var min : Int) : BaseValidity(input) {
    
    override fun isValid() : Boolean {
        if (min < 0) throw IllegalArgumentException(ZERO_MIN)
        return input.length >= min
    }
}