package ir.yamin.glitch.validity

internal open class RegexValidity(input : String, pattern : String) : Validity(input) {
    
    private val regex : Regex
    
    init {
        if (pattern.isBlank()) throw IllegalStateException("regex pattern can not be null")
        regex = Regex(pattern)
    }
    
    override fun isValid() = regex.matches(input)
    open fun contains() = regex.containsMatchIn(input)
    open fun find() = if (!contains()) "" else regex.find(input, 0)?.value.orEmpty()
    open fun findAll() : MutableList<String> {
        val result = mutableListOf<String>()
        if (contains()) regex.findAll(input, 0).mapTo(result) { match -> match.value }
        return result
    }
}