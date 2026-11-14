package com.example.a6__

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etTemperature: EditText
    private lateinit var cbDirection: CheckBox
    private lateinit var tvResult: TextView
    private lateinit var tvHint: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Находим элементы по id
        etTemperature = findViewById(R.id.etTemperature)
        cbDirection = findViewById(R.id.cbDirection)
        tvResult = findViewById(R.id.tvResult)
        tvHint = findViewById(R.id.tvHint)

        // Дополнительное событие: при смене CheckBox меняем подсказку
        cbDirection.setOnCheckedChangeListener { _, isChecked ->
            tvHint.text = if (isChecked) {
                getString(R.string.hint_c)
            } else {
                getString(R.string.hint_f)
            }
        }
    }

    // Логика расчета
    private fun calculateResult(value: Double, isCtoF: Boolean): Double {
        return if (isCtoF) {
            value * 9 / 5 + 32
        } else {
            (value - 32) * 5 / 9
        }
    }

    // Проверка ввода (обработка ошибок)
    private fun getValidatedInput(): Double? {
        val input = etTemperature.text.toString().trim()
        if (input.isEmpty()) {
            Toast.makeText(this, R.string.error_empty, Toast.LENGTH_SHORT).show()
            return null
        }

        val value = input.toDoubleOrNull()
        if (value == null) {
            Toast.makeText(this, R.string.error_invalid, Toast.LENGTH_SHORT).show()
            return null
        }

        val isCtoF = cbDirection.isChecked
        if (isCtoF && value < -273.15) {
            Toast.makeText(this, R.string.error_below_absolute_zero_c, Toast.LENGTH_SHORT).show()
            return null
        }
        if (!isCtoF && value < -459.67) {
            Toast.makeText(this, R.string.error_below_absolute_zero_f, Toast.LENGTH_SHORT).show()
            return null
        }

        return value
    }

    // Обработчик для кнопки "Рассчитать" (вызывается из XML)
    fun onCalculateClick(view: View) {
        val value = getValidatedInput() ?: return
        val isCtoF = cbDirection.isChecked
        val result = calculateResult(value, isCtoF)
        val unit = if (isCtoF) getString(R.string.unit_f) else getString(R.string.unit_c)

        tvResult.text = getString(R.string.result_format, result, unit)
    }

    // Обработчик для кнопки "Далее" (вызывается из XML)
    fun onNextClick(view: View) {
        val value = getValidatedInput() ?: return
        val isCtoF = cbDirection.isChecked
        val result = calculateResult(value, isCtoF)

        // Передаем данные на второй экран
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra(SecondActivity.EXTRA_ORIGINAL, value)
        intent.putExtra(SecondActivity.EXTRA_RESULT, result)
        intent.putExtra(SecondActivity.EXTRA_IS_C_TO_F, isCtoF)
        startActivity(intent)
    }
}