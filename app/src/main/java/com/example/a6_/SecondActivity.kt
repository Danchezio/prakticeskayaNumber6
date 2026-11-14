package com.example.a6_

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_QTY = "extra_qty"
        const val EXTRA_SUM = "extra_sum"
        const val EXTRA_CONFIRMED = "extra_confirmed"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val tvQty2 = findViewById<TextView>(R.id.tvQty2)
        val tvSum2 = findViewById<TextView>(R.id.tvSum2)
        val btnConfirm = findViewById<Button>(R.id.btnConfirm)
        val btnBack = findViewById<Button>(R.id.btnBack)

        val qty = intent.getIntExtra(EXTRA_QTY, 0)
        val sum = intent.getIntExtra(EXTRA_SUM, 0)

        tvQty2.text = getString(R.string.second_qty, qty)
        tvSum2.text = getString(R.string.second_sum, sum)

        btnConfirm.setOnClickListener {
            val data = Intent().apply {
                putExtra(EXTRA_CONFIRMED, true)
            }
            setResult(RESULT_OK, data)
            finish()
        }

        btnBack.setOnClickListener { finish() }
    }
}