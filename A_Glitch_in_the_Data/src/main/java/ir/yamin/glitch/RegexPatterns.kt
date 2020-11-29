package ir.yamin.glitch

object RegexPatterns {
    
    const val WHITE_SPACE = """\s+"""
    const val PERSIAN_NUMBERS = """[۰-۹]+"""
    const val ARABIC_NUMBERS = """[٠-٩]+"""
    const val PERSIAN_ALPHABET = """([\u0621-\u0659]+|[\u0670-\u06cc]+)+"""
    const val DIGIT = """\d+"""
    const val IRAN_MOBILE_NUMBERS = """([+]98|0098|0)?-?(9\d{2})-?(\d{3})-?(\d{4})"""
    const val ALPHA_NUMERIC = """([A-Za-z0-9])+"""
    const val DECIMAL = """\d*\.\d+"""
    const val EMAIL = """ """
    const val URL = """ """
}