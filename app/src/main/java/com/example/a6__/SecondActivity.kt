package com.example.a6__

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ORIGINAL = "extra_original"
        const val EXTRA_RESULT = "extra_result"
        const val EXTRA_IS_C_TO_F = "extra_is_c_to_f"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // Получаем данные с первого экрана
        val original = intent.getDoubleExtra(EXTRA_ORIGINAL, 0.0)
        val result = intent.getDoubleExtra(EXTRA_RESULT, 0.0)
        val isCtoF = intent.getBooleanExtra(EXTRA_IS_C_TO_F, true)

        // Определяем единицы измерения
        val originalUnit = if (isCtoF) getString(R.string.unit_c) else getString(R.string.unit_f)
        val resultUnit = if (isCtoF) getString(R.string.unit_f) else getString(R.string.unit_c)

        val tvOriginal = findViewById<TextView>(R.id.tvOriginal)
        val tvResultSecond = findViewById<TextView>(R.id.tvResultSecond)

        tvOriginal.text = getString(R.string.original_format, original, originalUnit)
        tvResultSecond.text = getString(R.string.result_second_format, result, resultUnit)
    }

    // Кнопка "Назад"
    fun onBackClick(view: View) {
        finish()
    }

    // Кнопка "Поделиться" (Неявный Intent)
    fun onShareClick(view: View) {
        val original = intent.getDoubleExtra(EXTRA_ORIGINAL, 0.0)
        val result = intent.getDoubleExtra(EXTRA_RESULT, 0.0)
        val isCtoF = intent.getBooleanExtra(EXTRA_IS_C_TO_F, true)

        val originalUnit = if (isCtoF) getString(R.string.unit_c) else getString(R.string.unit_f)
        val resultUnit = if (isCtoF) getString(R.string.unit_f) else getString(R.string.unit_c)

        val shareText = getString(R.string.share_text_format, result, resultUnit, original, originalUnit)

        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        startActivity(Intent.createChooser(sendIntent, getString(R.string.btn_share)))
    }
}