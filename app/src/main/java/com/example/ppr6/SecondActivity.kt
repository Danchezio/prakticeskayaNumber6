package com.example.ppr6

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
        val km = intent.getDoubleExtra("km", 0.0);
        findViewById<TextView>(R.id.retKm).text = km.toString();
        findViewById<TextView>(R.id.retCm).text = intent.getDoubleExtra("cm", 0.0).toString();
        findViewById<TextView>(R.id.retMi).text = intent.getDoubleExtra("mi", 0.0).toString();
        findViewById<Button>(R.id.btnShare).setOnClickListener {
            val send = Intent(Intent.ACTION_SEND)                                    // Действие «отправить»
            send.type = "text/plain"                                                 // Тип данных — простой текст
            send.putExtra(Intent.EXTRA_TEXT, "Моя длина: $km км")                  // Сам текст для отправки
            startActivity(Intent.createChooser(send, "Поделиться через"))            // Показываем системное меню выбора приложения
        }
    }
}