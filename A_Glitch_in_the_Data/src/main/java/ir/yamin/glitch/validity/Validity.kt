package ir.yamin.glitch.validity

internal abstract class Validity(input : String) {
    
    protected var input = ""
    
    init {
        this.input = input.trim()
    }
    
    abstract fun isValid() : Boolean
}