package ir.yamin.glitch.validity.length

import ir.yamin.glitch.validity.Validity

internal class MinimumValidity(input : String, private var min : Int) : Validity(input) {
    
    override fun isValid() : Boolean {
        if (min < 0) throw IllegalArgumentException("minimum cannot be less than zero!")
        return input.length >= min
    }
}