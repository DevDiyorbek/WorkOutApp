package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.workoutapp.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {
    private var binding : ActivityBmiBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBmiActivity)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Calculate BMI"
        }
        binding?.toolbarBmiActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        makeVisibleMetricUnitsView()

        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId: Int ->
            if (checkedId == R.id.rbMetricUnits) {
                makeVisibleMetricUnitsView()
            } else {
                makeVisibleUsUnitsView()
            }
        }

        binding?.btnCalculateUnits?.setOnClickListener {
            if (validateMetricUnits()){
                val heightValue : Float = binding?.etMetricUnitHeight?.text.toString().toFloat()/100
                val weightValue : Float = binding?.etMetricUnitWeight?.text.toString().toFloat()
                val bmi = weightValue/(heightValue*heightValue)
                displayBMIResult(bmi)
            } else if (validateUSUnits()){
                val inchValue : Float = binding?.etUSUnitFeet?.text.toString().toFloat()*12 +
                        binding?.etUSUnitInch?.text.toString().toFloat()
                val poundValue :Float = binding?.etUSUnitWeight?.text.toString().toFloat()
                val bmi = (poundValue/(inchValue*inchValue))*703
                displayBMIResult(bmi)
            } else {

                Toast.makeText(this@BMIActivity,
                    "Please, enter valid values",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun makeVisibleUsUnitsView() {

        binding?.USUnitsInputFields?.visibility = View.VISIBLE
        binding?.metricUnitsInputFields?.visibility = View.INVISIBLE
        binding?.etUSUnitWeight?.text!!.clear()
        binding?.etUSUnitFeet?.text!!.clear()
        binding?.etUSUnitInch?.text!!.clear()
        binding?.llDisplayBMIResult?.visibility = View.INVISIBLE
    }

    private fun makeVisibleMetricUnitsView() {
        binding?.metricUnitsInputFields?.visibility = View.VISIBLE
        binding?.USUnitsInputFields?.visibility = View.INVISIBLE
        binding?.etMetricUnitWeight?.text!!.clear()
        binding?.etMetricUnitHeight?.text!!.clear()
        binding?.llDisplayBMIResult?.visibility = View.INVISIBLE

    }


    private fun displayBMIResult(bmi:Float){

        val bmiLabel : String
        val bmiDescription : String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more"
        }else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <=0 ){
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more"
        }else if (bmi.compareTo(18.5f) >0 && bmi.compareTo(25f) <= 0){
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape"
        }else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! YOu really need to take care of yourself! Workout more"
        }else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <=0 ){
            bmiLabel = "Obese Class I"
            bmiDescription = "OMG! You are in a dangerous condition! Act now!"
        }else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <=0 ){
            bmiLabel = "Obese Class II"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }else {
            bmiLabel = "Obese Class III"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }
        val bmiValue = BigDecimal(bmi.toDouble())
            .setScale(2, RoundingMode.HALF_EVEN).toString()

        binding?.llDisplayBMIResult?.visibility = View.VISIBLE
        binding?.tvBMIValue?.text = bmiValue
        binding?.tvBMIType?.text = bmiLabel
        binding?.tvBMIDescription?.text = bmiDescription
    }

    private fun validateMetricUnits(): Boolean{
        var isValid = true
        if(binding?.etMetricUnitWeight?.text.toString().isEmpty()){
            isValid = false
        } else if(binding?.etMetricUnitHeight?.text.toString().isEmpty()){
            isValid = false
        }
        return isValid
    }

    private fun validateUSUnits(): Boolean{
        var isValid = true
        if(binding?.etUSUnitWeight?.text.toString().isEmpty()){
            isValid = false
        } else if(binding?.etUSUnitFeet?.text.toString().isEmpty()){
            isValid = false
        } else if (binding?.etUSUnitInch?.text.toString().isEmpty()){
            binding?.etUSUnitInch?.setText("0")
        }
        return isValid
    }
}
