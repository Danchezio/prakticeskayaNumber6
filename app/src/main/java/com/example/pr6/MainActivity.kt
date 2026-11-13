package com.example.pr6

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

class MainActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etPosition: EditText
    private lateinit var etPhone: EditText
    private lateinit var etEmail: EditText
    private lateinit var tvResult: TextView
    private lateinit var btnCalc: Button
    private lateinit var btnNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etName = findViewById(R.id.etName)
        etPosition = findViewById(R.id.etPosition)
        etPhone = findViewById(R.id.etPhone)
        etEmail = findViewById(R.id.etEmail)
        tvResult = findViewById(R.id.tvResult)
        btnCalc = findViewById(R.id.btnCalc)
        btnNext = findViewById(R.id.btnNext)

        btnCalc.setOnClickListener { calculateAndShow() }

        btnCalc.setOnLongClickListener {
            clearFields()
            Toast.makeText(this, R.string.msg_cleared, Toast.LENGTH_SHORT).show()
            true
        }

        btnNext.setOnClickListener { goToSecondScreen() }
    }

    private fun clearFields() {
        etName.text.clear()
        etPosition.text.clear()
        etPhone.text.clear()
        etEmail.text.clear()
        tvResult.text = ""
    }

    private fun validateInput(): Boolean {
        val name = etName.text.toString().trim()
        val position = etPosition.text.toString().trim()
        val phone = etPhone.text.toString().trim()
        val email = etEmail.text.toString().trim()

        if (name.isEmpty() || position.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, R.string.err_empty_fields, Toast.LENGTH_SHORT).show()
            return false
        }

        val phonePattern = Regex("^\\+?[0-9]+$")
        if (!phonePattern.matches(phone)) {
            Toast.makeText(this, R.string.err_phone, Toast.LENGTH_SHORT).show()
            etPhone.error = getString(R.string.err_phone)
            return false
        }

        val atIndex = email.indexOf('@')
        val dotAfterAt = if (atIndex >= 0) email.indexOf('.', atIndex) else -1
        if (atIndex <= 0 || dotAfterAt <= atIndex + 1 || dotAfterAt == email.length - 1) {
            Toast.makeText(this, R.string.err_email, Toast.LENGTH_SHORT).show()
            etEmail.error = getString(R.string.err_email)
            return false
        }

        return true
    }

    private fun calculateAndShow() {
        if (!validateInput()) return

        val name = etName.text.toString().trim()
        val position = etPosition.text.toString().trim()

        tvResult.text = getString(R.string.msg_result, name, position)
        Toast.makeText(this, R.string.msg_ok, Toast.LENGTH_SHORT).show()
    }

    private fun goToSecondScreen() {
        if (!validateInput()) return

        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra(SecondActivity.EXTRA_NAME, etName.text.toString().trim())
        intent.putExtra(SecondActivity.EXTRA_POSITION, etPosition.text.toString().trim())
        intent.putExtra(SecondActivity.EXTRA_PHONE, etPhone.text.toString().trim())
        intent.putExtra(SecondActivity.EXTRA_EMAIL, etEmail.text.toString().trim())
        startActivity(intent)
    }
}