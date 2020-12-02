package ir.yamin.glitch.validity

internal class NotEmptyValidity(input : String) : BaseValidity(input) {
    
    override fun isValid() : Boolean = input.isNotEmpty()
}