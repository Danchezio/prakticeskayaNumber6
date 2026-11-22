package com.example.pr_tretyak;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    // Границы приоритета
    private static final int MIN_PRIORITY = 1;
    private static final int MAX_PRIORITY = 3;

    // Элементы экрана
    private View clRoot;
    private EditText etTaskName;
    private EditText etPriority;
    private CheckBox cbUrgent;
    private TextView tvResult;

    // Проверенные данные, которые заполняет readInput()
    private String taskName = "";
    private int priority = 0;
    private boolean urgent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Подключаем XML-макет

        // Находим элементы по id
        clRoot = findViewById(R.id.clRoot);
        etTaskName = findViewById(R.id.etTaskName);
        etPriority = findViewById(R.id.etPriority);
        cbUrgent = findViewById(R.id.cbUrgent);
        tvResult = findViewById(R.id.tvResult);
        ImageView ivTask = findViewById(R.id.ivTask);

        // Дополнительное событие: клик по иллюстрации показывает подсказку
        ivTask.setOnClickListener(v -> showMessage(R.string.hint_fill));
    }

    // Обработчик кнопки «Рассчитать» (задан в XML через android:onClick)
    public void onCalcClick(View view) {
        if (!readInput()) {
            return; // Ввод неверный, ошибка уже показана
        }
        String urgentText = getString(urgent ? R.string.urgent_yes : R.string.urgent_no);
        // Форматируем результат через getString с аргументами
        tvResult.setText(getString(R.string.result_format, taskName, priority, urgentText));
        // Цвет надписи зависит от приоритета
        tvResult.setTextColor(getPriorityColor(priority));
    }

    // Обработчик кнопки «Далее» (задан в XML через android:onClick)
    public void onNextClick(View view) {
        if (!readInput()) {
            return;
        }
        // Явный Intent: откуда — MainActivity.this, куда — SecondActivity
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtra(SecondActivity.EXTRA_NAME, taskName);
        intent.putExtra(SecondActivity.EXTRA_PRIORITY, priority);
        intent.putExtra(SecondActivity.EXTRA_URGENT, urgent);
        startActivity(intent);
    }

    // Читает и проверяет ввод. Возвращает true, если всё корректно
    private boolean readInput() {
        String name = etTaskName.getText().toString().trim();
        String priorityText = etPriority.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            showMessage(R.string.error_empty_name);
            return false;
        }
        if (TextUtils.isEmpty(priorityText)) {
            showMessage(R.string.error_empty_priority);
            return false;
        }

        int value;
        try {
            value = Integer.parseInt(priorityText);
        } catch (NumberFormatException e) {
            showMessage(R.string.error_not_number);
            return false;
        }
        if (value < MIN_PRIORITY || value > MAX_PRIORITY) {
            showMessage(R.string.error_range);
            return false;
        }

        // Ввод корректный, запоминаем данные
        taskName = name;
        priority = value;
        urgent = cbUrgent.isChecked();
        return true;
    }

    // Цвет по приоритету: 1 — зелёный, 2 — оранжевый, 3 — красный
    private int getPriorityColor(int value) {
        int colorRes;
        if (value == 1) {
            colorRes = R.color.priority_low;
        } else if (value == 2) {
            colorRes = R.color.priority_medium;
        } else {
            colorRes = R.color.priority_high;
        }
        return ContextCompat.getColor(this, colorRes);
    }

    // Единое место показа сообщений (Snackbar из библиотеки Material)
    private void showMessage(int messageRes) {
        Snackbar.make(clRoot, messageRes, Snackbar.LENGTH_SHORT).show();
    }
}