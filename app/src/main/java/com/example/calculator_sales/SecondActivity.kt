package com.example.calculator_sales

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PRICE = "extra_price"
        const val EXTRA_DISCOUNT = "extra_discount"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val price = intent.getDoubleExtra(EXTRA_PRICE, 0.0)
        val discount = intent.getDoubleExtra(EXTRA_DISCOUNT, 0.0)
        val newPrice = price * (1 - discount / 100)

        val tvOriginal = findViewById<TextView>(R.id.tvOriginal)
        val tvDiscount = findViewById<TextView>(R.id.tvDiscount)
        val tvNewPrice = findViewById<TextView>(R.id.tvNewPrice)
        val btnBack = findViewById<Button>(R.id.btnBack)

        tvOriginal.text = getString(R.string.original_format, price)
        tvDiscount.text = getString(R.string.discount_format, discount)
        tvNewPrice.text = getString(R.string.new_price_format, newPrice)

        btnBack.setOnClickListener {
            finish()
        }
    }
}