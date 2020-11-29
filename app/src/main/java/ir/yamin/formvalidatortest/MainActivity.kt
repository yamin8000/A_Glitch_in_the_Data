package ir.yamin.formvalidatortest

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import ir.yamin.glitch.form.FormElement
import ir.yamin.glitch.rules.Rules
import ir.yamin.glitch.validator.FormValidator

class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        Logger.addLogAdapter(AndroidLogAdapter(PrettyFormatStrategy.newBuilder().tag("<==>").build()))
    
        val edittext1 = findViewById<EditText>(R.id.edittext1)
        val edittext2 = findViewById<EditText>(R.id.edittext2)
        val edittext3 = findViewById<EditText>(R.id.edittext3)
        val input = findViewById<TextInputLayout>(R.id.inputLayout)
    
        //            val formValidity = FormValidator().addField(FormElement(edittext1, "text1").error("digit only"),
        //                                                        FormElement(edittext2, "text2").error("digit only"),
        //                                                        FormElement(edittext3, "text3").error("digit only"),
        //                                                        FormElement(input, "input").error("digit only"))
        //                .withRule(Rule.IsDigit, LengthRule().setMin(5).setMax(15))
        val form = FormValidator().ignoreWhiteSpace(true)
    
        //        form.addWithRule(FormElement(edittext1, "name").error("alpha numeric min 3 max 10"),
        //                         Rules.AlphaNumeric, Rules.Length.Min(3), Rules.Length.Max(10))
        //
        //        form.addWithRule(FormElement(edittext2, "password").error("digit only"), Rules.Digit)
        //
        //        form.addWithRule(FormElement(edittext3, "code").error("کد ملی نیست"), Rules.IranNationalCode)
        //
        //        form.addWithRule(FormElement(input, "mobile").error("not mobile"), Rules.IranMobile)
    
        form.add(FormElement(edittext1).error("error1")).add(FormElement(edittext2).error("error2"))
            .add(FormElement(edittext3).error("error3")).add(FormElement(input).error("error4"))
            .withRule(Rules.Length.Min(5), Rules.PersianText, Rules.Length.Max(10))
    
        form.activeCheck()
    
    
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            Logger.d(form.isValid())
            Logger.d(form.giveMeSomething())
        }
    }
    
}