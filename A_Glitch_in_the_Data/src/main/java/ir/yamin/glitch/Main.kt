package ir.yamin.glitch

import ir.yamin.glitch.rules.CustomRule
import ir.yamin.glitch.rules.RegexRule
import ir.yamin.glitch.validator.StringValidator

fun main() {
    //    println("is 'null' a persian number? ${StringValidator().isPersianNumber(null)}")
    //    println("is '۸۸۸۹۹۹۹۹۹' a persian number? ${StringValidator().isPersianNumber("۸۸۸۹۹۹۹۹۹")}")
    //    println("is '۸۸۸۹۹sa۹۹۹۹' a persian number? ${StringValidator().isPersianNumber("۸۸۸۹۹sa۹۹۹۹")}")
    //    println("is '۸۸۸۹۹sa۹۹۹۹' contains a persian number? ${
    //        StringValidator().containsPersianNumber("۸۸۸۹۹sa۹۹۹۹")
    //    }")
    //    println("is 'sss' contains a persian number? ${StringValidator().containsPersianNumber("sss")}")
    //
    //
    //    println("added strings are: ${
    //        StringValidator().addString(null, null).giveMeMyStrings()
    //    }")
    //    println("added strings are: ${StringValidator().addString(null).giveMeMyStrings()}")
    //    println("added strings are: ${StringValidator().addString(mutableListOf("sads", "")).giveMeMyStrings()}")
    //
    //
    //    println("is 'سلامas' contains a persian text? ${StringValidator().containsPersianText("سلامas")}")
    //
    //    val valid1 = StringValidator().addString("سلا3م").addString("خداحافظ").withRule(Rule.IsPersianText)
    //        .isValid()
    //    println(valid1)
    //
    //    println(StringValidator().isNationalCode("2110610352"))
    //    println(StringValidator().isNationalCode("2122618280"))
    //    println(StringValidator().isNationalCode("2122618180"))
    //    println(StringValidator().isNationalCode("aaaaaaaaaa"))
    //
    //    println(StringValidator().findAllDigit("asdasdasdasdasdasdasdasd123assd21312as"))
    //    println(StringValidator().findAllDigit("asdasdas"))
    //
    //    val valid = StringValidator().addString("1ی1").addString("سلاااااام").addString("salسسسam")
    //        .withRule(Rule.ContainsPersianText).isValid()
    //    println(valid)
    //
    //    val multi = StringValidator().addStringWithRule("11", Rule.IsDigit)
    //        .addStringWithRule("سلام", Rule.IsPersianText).isValid()
    //    println(multi)
    //
    //    val test = StringValidator().addStringWithRule("yamin", Length(1000).setMax(10).setMin(1))
    //    println(test.isValid())
    //
    //    val multi2 = StringValidator().addStringWithRule("12345", listOf(Rule.IsDigit, Length(5)))
    //        .addStringWithRule("م۹ین1",
    //                           listOf(Rule.ContainsPersianText, Rule.ContainsDigit, Rule.ContainsPersianNumber))
    //        .addStringWithRule("111", listOf(Length().setMin(2).setMax(10), Rule.ContainsDigit))
    //
    //    val single = StringValidator().addString("tasd1as", "a1sdas", "a2s1")
    //        .withRule(Length().setMin(4), Rule.ContainsDigit)
    //
    //    println(multi2.isValid())
    //    println(single.isValid())
    //
    //    println(Digit("asdasdas111asdasdasd2531").find())
    //
    //    println(StringValidator().addString("hello", "hello 123").withRule(Rule.IsAlphaNumeric).isValid())
    //
    //    println(IranNationalCode("2110610351").contains())
    
    //println(StringValidator().isIranMobile("09338880330"))
    //    val testTest = StringValidator().addStringWithRule("yamin2", Rule.AlphaNumeric, LengthRule().setMin(4))
    //        .addStringWithRule("siah", Rule.AlphaNumeric).addStringWithRule("212312", Rule.Digit)
    //        .addStringWithRule("2110610352", Rule.IranNationalCode)
    //    println(testTest.isValid())
    //    println(testTest.giveMeMyStringValidity())
    
    val test : String? = null
    val test2 : String? = null
    val ss = StringValidator().addStringWithRule("abab13123123", RegexRule("(ab)+.+"))
    val sd = StringValidator().addStringWithRule("abab13123123", CustomRule {
    
        return@CustomRule false
    })
    println(sd.isValid())
}