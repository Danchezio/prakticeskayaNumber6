package com.example.feedbackform
import android.content.Intent
import android.view.View
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.google.android.material.snackbar.Snackbar
class MainActivity : ComponentActivity() {
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etReview: EditText
    private lateinit var tvCharCount: TextView

    private val MIN_LENGTH = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etName   = findViewById(R.id.etName)
        etEmail  = findViewById(R.id.etEmail)
        etReview = findViewById(R.id.etReview)
        tvCharCount = findViewById(R.id.tvCharCount)

    }


    fun onHintClick(view: View) {
        Snackbar.make(
            view,
            "Заполните имя, e-mail с символом @ и отзыв не короче $MIN_LENGTH символов",
            Snackbar.LENGTH_LONG
        ).show()
    }

    fun onCalculateClick(view: View) {
        val len = etReview.text.length
        val emailOk = etEmail.text.contains("@")
        val msg = buildString {
            append("Символов в отзыве: $len")
            append(" • E-mail ")
            append(if (emailOk) "корректен" else "не содержит @")
            append(" • До минимума: ")
            append((MIN_LENGTH - len).coerceAtLeast(0))
        }
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show()
    }

    fun onNextClick(view: View) {
        val name = etName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val review = etReview.text.toString()

        if (!email.contains("@")) {
            Snackbar.make(view, "E-mail должен содержать символ @", Snackbar.LENGTH_LONG).show()
            return
        }
        if (review.length < MIN_LENGTH) {
            Snackbar.make(
                view,
                "Текст отзыва — минимум $MIN_LENGTH символов",
                Snackbar.LENGTH_LONG
            ).show()
            return
        }

        val intent = Intent(this, SecondActivity::class.java).apply {
            putExtra("EXTRA_NAME", name)
            putExtra("EXTRA_LENGTH", review.length)
        }
        startActivity(intent)
    }
}