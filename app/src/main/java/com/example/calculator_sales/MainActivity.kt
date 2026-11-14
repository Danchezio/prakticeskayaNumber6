package com.example.calculator_sales

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var etPrice: EditText
    private lateinit var etDiscount: EditText
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etPrice = findViewById(R.id.etPrice)
        etDiscount = findViewById(R.id.etDiscount)
        tvResult = findViewById(R.id.tvResult)
        val btnCalculate = findViewById<Button>(R.id.btnCalculate)
        val btnNext = findViewById<Button>(R.id.btnNext)

        etPrice.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) validatePrice(false)
        }
        etDiscount.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) validateDiscount(false)
        }

        btnCalculate.setOnClickListener {
            if (validatePrice(true) && validateDiscount(true)) {
                val price = etPrice.text.toString().toDouble()
                val discount = etDiscount.text.toString().toDouble()
                showResult(price, discount)
            }
        }

        btnNext.setOnClickListener {
            if (validatePrice(true) && validateDiscount(true)) {
                val price = etPrice.text.toString().toDouble()
                val discount = etDiscount.text.toString().toDouble()

                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra(SecondActivity.EXTRA_PRICE, price)
                intent.putExtra(SecondActivity.EXTRA_DISCOUNT, discount)
                startActivity(intent)
            }
        }
    }

    private fun validatePrice(showSnackbar: Boolean): Boolean {
        val str = etPrice.text.toString().trim()
        if (str.isEmpty()) {
            if (showSnackbar) showError(getString(R.string.error_empty_price))
            return false
        }
        val value = str.toDoubleOrNull()
        if (value == null) {
            if (showSnackbar) showError(getString(R.string.error_invalid_price))
            return false
        }
        if (value <= 0) {
            if (showSnackbar) showError(getString(R.string.error_price_range))
            return false
        }
        return true
    }

    private fun validateDiscount(showSnackbar: Boolean): Boolean {
        val str = etDiscount.text.toString().trim()
        if (str.isEmpty()) {
            if (showSnackbar) showError(getString(R.string.error_empty_discount))
            return false
        }
        val value = str.toDoubleOrNull()
        if (value == null) {
            if (showSnackbar) showError(getString(R.string.error_invalid_discount))
            return false
        }
        if (value < 0 || value > 100) {
            if (showSnackbar) showError(getString(R.string.error_discount_range))
            return false
        }
        return true
    }

    private fun showError(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }
    private fun showResult(price: Double, discount: Double) {
        val newPrice = price * (1 - discount / 100)
        val saved = price - newPrice

        tvResult.text = getString(R.string.result_format, newPrice, saved)
    }
}