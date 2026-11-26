package com.example.ppr6

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val z = View.OnClickListener {
            val x_ = findViewById<EditText>(R.id.theInput).text.toString().toDoubleOrNull();
            if (x_ == null || x_ < 0) {
                Toast.makeText(this, "Некорректный ввод!", Toast.LENGTH_SHORT).show()
                return@OnClickListener;
            }
            val km = x_ / 1000;
            val cm = x_ * 100;
            val mi = x_ * 0.000621317;
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("km", km);
            intent.putExtra("cm", cm);
            intent.putExtra("mi", mi);
            startActivity(intent);
        };
        val y = View.OnTouchListener { view, me ->
            if (me.actionMasked == MotionEvent.ACTION_DOWN)
                view.alpha = 0.6f;
            else if (me.actionMasked == MotionEvent.ACTION_UP)
                view.alpha = 1f;
            return@OnTouchListener false;
        }
        findViewById<Button>(R.id.btnNext).setOnClickListener(z);
        findViewById<Button>(R.id.btnCalc).setOnClickListener(z);
        findViewById<Button>(R.id.btnNext).setOnTouchListener(y);
        findViewById<Button>(R.id.btnCalc).setOnTouchListener(y);
    }
}