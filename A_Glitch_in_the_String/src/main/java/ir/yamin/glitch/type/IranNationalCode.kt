package ir.yamin.glitch.type

import ir.yamin.glitch.Patterns

class IranNationalCode(string : String?) : Text(string, Patterns.DIGIT) {
    
    override fun isValid() : Boolean {
        var validity = false
        if (input.isBlank() || input.length != 10 || !Regex(Patterns.DIGIT).matches(input)) {
            return false
        }
        val numberList = mutableListOf<Int>()
        input.toCharArray().mapTo(numberList) { char -> Character.getNumericValue(char) }
        val sum = (0..8).sumBy { index -> numberList[index] * (10 - index) }
        val sumModEleven = sum % 11
        val lastNumPredict : Int
        lastNumPredict = if (sumModEleven in 0..2) sumModEleven else 11 - (sum % 11)
        
        if (lastNumPredict == numberList.last()) validity = true
        
        return validity
    }
}