package ir.yamin.glitch.type

abstract class Text(string : String?, pattern : String?) {
    
    protected var input = ""
    private val regex : Regex
    
    init {
        if (pattern == null || string == null) throw NullPointerException()
        if (pattern.isBlank()) throw IllegalStateException("regex pattern can not be null")
        regex = Regex(pattern)
        input = string.trim()
    }
    
    open fun isValid() = regex.matches(input)
    open fun contains() = regex.containsMatchIn(input)
    open fun find() = if (!contains()) "" else regex.find(input, 0)?.value.orEmpty()
    open fun findAll() : MutableList<String> {
        val result = mutableListOf<String>()
        if (contains()) regex.findAll(input, 0).mapTo(result) { match -> match.value }
        return result
    }
}