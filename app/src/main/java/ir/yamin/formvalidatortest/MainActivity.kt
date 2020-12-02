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
    
        val form = FormValidator().ignoreWhiteSpace(true)
    
        form.add(FormElement(edittext1).error("error1")).add(FormElement(edittext2).error("error2"))
            .add(FormElement(edittext3).error("error3")).add(FormElement(input).error("error4"))
            .withRule(Rules.Length.Min(5), Rules.PersianText("متن فارسی باید باشد"),
                      Rules.Length.Max(10, "حداکثر ده حرفی باید باشد"), Rules.Custom {
                return@Custom true
            })
        form.addSubmitButton(findViewById(R.id.submitButton))
            .addWithRule(FormElement(edittext2), Rules.NotEmpty())
    
        form.activeCheck()
    
        val button = findViewById<Button>(R.id.submitButton)
        button.setOnClickListener {
            Logger.d(form.isValid())
            Logger.d(form.getFormState())
        }
    }
    
}