package ir.yamin.glitch.rules

class LengthRule() : Rule() {
    
    var length = 0
    var min = 0
    var max = 0
    
    constructor(exactLength : Int) : this() {
        if (exactLength == 0) zeroLength()
        length = exactLength
    }
    
    fun setMin(minLength : Int) = this.apply {
        if (minLength == 0) zeroLength()
        min = minLength
    }
    
    fun setMax(maxLength : Int) = this.apply {
        if (maxLength == 0) zeroLength()
        max = maxLength
    }
    
    fun isValidLength(string : String?, exactLength : Int?, minLength : Int?, maxLength : Int?) : Boolean {
        if (exactLength == null || minLength == null || maxLength == null) {
            throw IllegalStateException("Length cannot be null!")
        }
        val stringSize = string.orEmpty().length
        when {
            minLength == 0 && maxLength == 0 && exactLength == 0 -> return true
            minLength == 0 && maxLength == 0 -> if (stringSize == exactLength) return true
            minLength == 0 -> return stringSize <= maxLength
            maxLength == 0 -> return stringSize >= minLength
            else -> return stringSize <= maxLength && stringSize >= minLength
        }
        return false
    }
    
    private fun zeroLength() : Unit = throw IllegalStateException("Length cannot be zero or null!")
}