package com.example.feedbackform
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.google.android.material.snackbar.Snackbar

class SecondActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val name   = intent.getStringExtra("EXTRA_NAME") ?: "—"
        val length = intent.getIntExtra("EXTRA_LENGTH", 0)

        findViewById<TextView>(R.id.tvName).text   = "Имя: $name"
        findViewById<TextView>(R.id.tvLength).text = "Длина отзыва: $length символов"

        findViewById<Button>(R.id.btnShare).setOnClickListener {
            val shareText = "Пользователь $name оставил отзыв длиной $length символов."
            val send = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareText)
                putExtra(Intent.EXTRA_SUBJECT, "Мой отзыв")
            }
            startActivity(Intent.createChooser(send, "Поделиться через"))
        }
    }
}