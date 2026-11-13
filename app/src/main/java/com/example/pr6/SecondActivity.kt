package com.example.pr6

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : AppCompatActivity()  {

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_POSITION = "extra_position"
        const val EXTRA_PHONE = "extra_phone"
        const val EXTRA_EMAIL = "extra_email"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_page)

        val name = intent.getStringExtra(EXTRA_NAME) ?: ""
        val position = intent.getStringExtra(EXTRA_POSITION) ?: ""
        val phone = intent.getStringExtra(EXTRA_PHONE) ?: ""
        val email = intent.getStringExtra(EXTRA_EMAIL) ?: ""

        val tvName = findViewById<TextView>(R.id.tvName)
        val tvPosition = findViewById<TextView>(R.id.tvPosition)
        val tvPhone = findViewById<TextView>(R.id.tvPhone)
        val tvEmail = findViewById<TextView>(R.id.tvEmail)

        tvName.text = getString(R.string.label_name) + " " + name
        tvPosition.text = getString(R.string.label_position) + " " + position
        tvPhone.text = getString(R.string.label_phone) + " " + phone
        tvEmail.text = getString(R.string.label_email) + " " + email

        findViewById<Button>(R.id.btnShare).setOnClickListener {
            shareCard(name, position, phone, email)
        }

        findViewById<Button>(R.id.btnBack).setOnClickListener { finish() }
    }

    private fun shareCard(name: String, position: String, phone: String, email: String) {
        val text = getString(R.string.share_text, name, position, phone, email)
        val send = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        try {
            startActivity(Intent.createChooser(send, getString(R.string.share_chooser)))
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, R.string.no_app_for_share, Toast.LENGTH_SHORT).show()
        }
    }
}