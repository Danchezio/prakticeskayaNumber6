package com.example.a6_

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var quantity: Int = 0
    private var pricePerItem: Int = 0

    private lateinit var tvQty: TextView
    private lateinit var tvPrice: TextView
    private lateinit var tvResult: TextView

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val confirmed = result.data?.getBooleanExtra(SecondActivity.EXTRA_CONFIRMED, false) == true
            if (confirmed) {
                tvResult.text = getString(R.string.second_status)
                Toast.makeText(this, R.string.second_status, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvQty = findViewById(R.id.tvQty)
        tvPrice = findViewById(R.id.tvPrice)
        tvResult = findViewById(R.id.tvResult)
        val btnPlus = findViewById<Button>(R.id.btnPlus)
        val btnMinus = findViewById<Button>(R.id.btnMinus)
        val btnCalc = findViewById<Button>(R.id.btnCalc)
        val btnNext = findViewById<Button>(R.id.btnNext)

        pricePerItem = resources.getInteger(R.integer.price_per_item)
        quantity = resources.getInteger(R.integer.qty_default)
        tvPrice.text = getString(R.string.price_value, pricePerItem)
        updateQuantityText()

        val qtyClickListener = View.OnClickListener { view ->
            val min = resources.getInteger(R.integer.qty_min)
            val max = resources.getInteger(R.integer.qty_max)
            when (view.id) {
                R.id.btnPlus -> {
                    if (quantity >= max) {
                        Toast.makeText(this, R.string.toast_limit_max, Toast.LENGTH_SHORT).show()
                    } else {
                        quantity++
                    }
                }
                R.id.btnMinus -> {
                    if (quantity <= min) {
                        Toast.makeText(this, R.string.toast_limit_min, Toast.LENGTH_SHORT).show()
                    } else {
                        quantity--
                    }
                }
            }
            updateQuantityText()
        }
        btnPlus.setOnClickListener(qtyClickListener)
        btnMinus.setOnClickListener(qtyClickListener)

        btnCalc.setOnClickListener {
            val sum = calculateSum()
            tvResult.text = getString(R.string.result_sum, sum)
            Toast.makeText(this, R.string.toast_calc_done, Toast.LENGTH_SHORT).show()
        }

        btnCalc.setOnLongClickListener {
            quantity = resources.getInteger(R.integer.qty_default)
            updateQuantityText()
            tvResult.text = ""
            Toast.makeText(this, R.string.toast_long_press, Toast.LENGTH_SHORT).show()
            true
        }

        btnNext.setOnClickListener {
            val sum = calculateSum()
            val intent = Intent(this, SecondActivity::class.java).apply {
                putExtra(SecondActivity.EXTRA_QTY, quantity)
                putExtra(SecondActivity.EXTRA_SUM, sum)
            }
            launcher.launch(intent)
        }
    }

    private fun calculateSum(): Int = quantity * pricePerItem

    private fun updateQuantityText() {
        tvQty.text = getString(R.string.qty_value, quantity)
    }
}