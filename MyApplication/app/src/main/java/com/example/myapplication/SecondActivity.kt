package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    private lateinit var tvPetName: TextView
    private lateinit var tvHumanAge: TextView
    private lateinit var btnShare: Button
    private lateinit var btnBack: Button

    private var petName: String = ""
    private var humanAge: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        tvPetName = findViewById(R.id.tvPetName)
        tvHumanAge = findViewById(R.id.tvHumanAge)
        btnShare = findViewById(R.id.btnShare)
        btnBack = findViewById(R.id.btnBack)

        // Получаем данные с первого экрана
        petName = intent.getStringExtra("NAME") ?: ""
        humanAge = intent.getIntExtra("HUMAN_AGE", 0)

        // Имя питомца
        tvPetName.text = getString(R.string.pet_name_text, petName)

        // Склонение слова "год"
        val ageText = resources.getQuantityString(
            R.plurals.human_years,
            humanAge,
            humanAge
        )
        tvHumanAge.text = getString(R.string.human_age_text, ageText)

        // Поделиться результатом (неявный Intent)
        btnShare.setOnClickListener {
            val message = getString(R.string.share_message, petName, ageText)

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, message)
            }

            startActivity(
                Intent.createChooser(shareIntent, getString(R.string.share_chooser))
            )
        }

        // Возврат на первый экран
        btnBack.setOnClickListener {
            finish()
        }
    }
}