package ir.yamin.glitch.validity.length

import ir.yamin.glitch.util.CONSTANTS.ZERO_MAX
import ir.yamin.glitch.validity.BaseValidity

internal class MaximumValidity(input : String, private var max : Int) : BaseValidity(input) {
    
    override fun isValid() : Boolean {
        if (max < 0) throw IllegalArgumentException(ZERO_MAX)
        return input.length <= max
    }
}