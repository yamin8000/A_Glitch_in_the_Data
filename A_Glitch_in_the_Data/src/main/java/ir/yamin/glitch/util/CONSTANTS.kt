package ir.yamin.glitch.util

object CONSTANTS {
    
    const val RULE_COLLISION = "It's either multi rule or single rule, you cannot set them both!"
    const val MINIMUM_MAXIMUM = "Minimum cannot be greater than Maximum!"
    private const val LENGTH = "Length"
    private const val MIN = "Minimum"
    private const val MAX = "Maximum"
    private const val NOT_ZERO = " cannot be less than zero!"
    const val ZERO_LENGTH = "${LENGTH}${NOT_ZERO}"
    const val ZERO_MIN = "${MIN}${NOT_ZERO}"
    const val ZERO_MAX = "${MAX}${NOT_ZERO}"
    const val REGEX_PATTERN = "Regex pattern can not be null!"
}