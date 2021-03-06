package com.example.tiptime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.*
import kotlin.math.ceil

//------------viewBinding---------------//

/** INFORMATION: View Binding makes it much easier and faster to call methods on the views in your UI.
 * The binding object automatically defines references for every View in your app that has an ID.
 * Using view binding is so much more concise that often you won't even need to create a variable to hold the reference for a View,
 * just use it directly from the binding object.

 //   Below are lines to be added in Gradle Script > build.gradle
 * android {
        ...

        buildFeatures {
            viewBinding true
        }
        ...
    }

# IMPORTANT NOTE: The reference for each view is generated by removing underscores and converting the view name to camel case. */

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding   //  This declares a top-level variable in the class for the binding object.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)    // Initializes the binding object which you'll use to access Views in the activity_main.xml layout
        setContentView(binding.root)    //  Instead of passing the resource ID of the layout, specifies the root of the hierarchy of views in your app, binding.root.

        binding.tipCalculateButton.setOnClickListener { calculateTip() }
    }

    private fun calculateTip() {    //    If defined as 'private fun calculateTip()', its members won't be able to access other implicit functions such as 'toDouble()'

        var costOfService = binding.costOfService.text.toString().toDoubleOrNull()    //  Here, 'binding.costOfService.text' returns data of type 'EDITABLE', therefore, to convert this returned data into 'DOUBLE', we converted it first into 'STRING'

/** #EDGE CASE  */
        if (costOfService == null)  return  //  this step will protect the app from getting crashed,
    /** CONSIDER, IF WE USE 'TODOUBLE()' INSTEAD IN THE ABOVE STATEMENT, THEN COSTOFSERVICE WILL RECEIVE AN EMPTY STRING WHICH WILL THROW AN ERROR.
        HENCE, WE USED 'TODOUBLEORNULL()', WHICH WILL RETURN NULL IF THERE'S NOT A STRING, THEN LATER WE CHECKED IF THE STRING IS NULL.
        IF SO, THEN WE STRICTLY TERMINATING FROM THE PROCESS, BECAUSE IF WE PROCEED WITH THE NULL VALUE, THEN IN FURTHER DOUBLE TYPE CALCULATION,
        THE PROGRAM WILL GET CRASHED AS IT'LL AGAIN GET TERMINATED ENCOUNTERING AN EXCEPTION    */

        var tipPercentage =
            when (binding.tipOptions.checkedRadioButtonId) {    //  passing an ID of Checked Radio Button as parameter of "'WHEN' (Switch Case)"
                R.id.twenty_percent_option -> 0.20
                R.id.fifteen_percent_option -> 0.15
                else -> 0.10
            }
        var tipValue = costOfService * tipPercentage    //  Calculating tip value

        if (binding.roundUpSwitch.isChecked) {  //  Rounding up tip value if roundUpSwitch is Checked
            tipValue = ceil(costOfService * tipPercentage)
        }

        binding.tipResult.text = getString(R.string.tip_amount, NumberFormat.getCurrencyInstance(Locale("en", "in")).format(tipValue))
        /** "NumberFormat.getCurrencyInstance().format(tipValue)" will generate the Currency String (501 -> $501)
            "getString()" will place the value of its second parameter to the Format Specifier in the String formatting done in the string definition written in 'strings.xml' file and returns the combined value   */
    }
}

/** -----------------*DEFAULT*--findViewById()-----------------
//The Android framework provides a method, findViewById(),
//that does exactly what you need???given the ID of a View, return a reference to it.
//This approach works, but as you add more views to your app and the UI becomes more complex,
//using findViewById() can become cumbersome.

class MainActivity : AppCompatActivity() {
override fun onCreate(savedInstanceState: Bundle?) {
super.onCreate(savedInstanceState)
setContentView(R.layout.activity_main)
}
}
 */
