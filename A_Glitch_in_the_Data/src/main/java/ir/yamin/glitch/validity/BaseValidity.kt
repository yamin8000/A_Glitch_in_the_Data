package ir.yamin.glitch.validity

internal abstract class BaseValidity(input : String) {
    
    protected var input = ""
    
    init {
        this.input = input.trim()
    }
    
    abstract fun isValid() : Boolean
}