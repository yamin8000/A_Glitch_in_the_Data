package ir.yamin.formvalidatortest

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import ir.yamin.glitch.FormElement
import ir.yamin.glitch.FormValidator
import ir.yamin.glitch.rules.LengthRule
import ir.yamin.glitch.rules.Rule

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
        val form = FormValidator()
        
        form.addFieldWithRule(FormElement(edittext1, "name").error("alpha numeric min 3 max 10"),
                              Rule.AlphaNumeric, LengthRule().setMin(3).setMax(10))
        
        form.addFieldWithRule(FormElement(edittext2, "password").error("digit only"), Rule.Digit)
        
        form.addFieldWithRule(FormElement(edittext3, "code").error("کد ملی نیست"), Rule.IranNationalCode)
        
        form.addFieldWithRule(FormElement(input, "mobile").error("not mobile"), Rule.IranMobile)
        
        form.activeCheck()
        
        
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            Logger.d(form.isValid())
            Logger.d(form.giveMeSomething())
        }
    }
}