package ir.yamin.glitch.validity.length

import ir.yamin.glitch.validity.Validity

internal class LengthValidity(input : String, private val exact : Int) : Validity(input) {
    
    override fun isValid() : Boolean {
        if (exact < 0) throw IllegalArgumentException("length cannot be less than zero!")
        return input.length == exact
    }
}