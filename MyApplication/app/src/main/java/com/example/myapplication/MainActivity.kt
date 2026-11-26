package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etAge: EditText
    private lateinit var etSpecies: EditText
    private lateinit var btnCalculate: Button
    private lateinit var btnNext: Button
    private lateinit var tvResult: TextView
    private lateinit var rootView: View

    private var humanAge: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rootView = findViewById(android.R.id.content)

        etName = findViewById(R.id.etName)
        etAge = findViewById(R.id.etAge)
        etSpecies = findViewById(R.id.etSpecies)
        btnCalculate = findViewById(R.id.btnCalculate)
        btnNext = findViewById(R.id.btnNext)
        tvResult = findViewById(R.id.tvResult)

        // Проверка возраста при потере фокуса
        etAge.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                checkAge()
            }
        }

        // Кнопка расчёта
        btnCalculate.setOnClickListener {
            calculateAge()
        }

        // Переход на второй экран
        btnNext.setOnClickListener {
            if (calculateAge()) {
                val name = etName.text.toString().trim()
                val species = etSpecies.text.toString().trim()

                val intent = Intent(this, SecondActivity::class.java).apply {
                    putExtra("NAME", name)
                    putExtra("SPECIES", species)
                    putExtra("HUMAN_AGE", humanAge)
                }
                startActivity(intent)
            }
        }
    }

    // Проверяем возраст, возвращаем true/false
    private fun checkAge(): Boolean {
        val ageText = etAge.text.toString().trim()

        if (ageText.isEmpty()) {
            etAge.error = getString(R.string.error_empty_age)
            return false
        }

        val age = ageText.toIntOrNull()
        if (age == null) {
            etAge.error = getString(R.string.error_age_number)
            return false
        }

        if (age < 0 || age > 30) {
            etAge.error = getString(R.string.error_age_range)
            return false
        }

        etAge.error = null
        return true
    }

    // Расчёт человеческого возраста
    private fun calculateAge(): Boolean {
        val name = etName.text.toString().trim()
        val species = etSpecies.text.toString().trim()

        if (name.isEmpty()) {
            etName.error = getString(R.string.error_name)
            Snackbar.make(rootView, R.string.error_name, Snackbar.LENGTH_SHORT).show()
            return false
        }

        if (species.isEmpty()) {
            etSpecies.error = getString(R.string.error_species)
            Snackbar.make(rootView, R.string.error_species, Snackbar.LENGTH_SHORT).show()
            return false
        }

        // Проверка возраста с конкретным сообщением в Snackbar
        val ageText = etAge.text.toString().trim()

        if (ageText.isEmpty()) {
            etAge.error = getString(R.string.error_empty_age)
            Snackbar.make(rootView, R.string.error_empty_age, Snackbar.LENGTH_SHORT).show()
            return false
        }

        val age = ageText.toIntOrNull()
        if (age == null) {
            etAge.error = getString(R.string.error_age_number)
            Snackbar.make(rootView, R.string.error_age_number, Snackbar.LENGTH_SHORT).show()
            return false
        }

        if (age < 0 || age > 30) {
            etAge.error = getString(R.string.error_age_range)
            Snackbar.make(rootView, R.string.error_age_range, Snackbar.LENGTH_SHORT).show()
            return false
        }

        etAge.error = null

        humanAge = age * 7
        tvResult.text = getString(R.string.result_text, name, humanAge)
        return true
    }
}