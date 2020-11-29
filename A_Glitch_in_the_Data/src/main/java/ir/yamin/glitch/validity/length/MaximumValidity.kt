package ir.yamin.glitch.validity.length

import ir.yamin.glitch.validity.Validity

internal class MaximumValidity(input : String, private var max : Int) : Validity(input) {
    
    override fun isValid() : Boolean {
        if (max < 0) throw IllegalArgumentException("max cannot be less than zero!")
        return input.length <= max
    }
}