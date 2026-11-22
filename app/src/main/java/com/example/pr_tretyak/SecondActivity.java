package com.example.pr_tretyak;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    // Ключи для данных Intent (одинаковые константы на обоих экранах)
    public static final String EXTRA_NAME = "extra_name";
    public static final String EXTRA_PRIORITY = "extra_priority";
    public static final String EXTRA_URGENT = "extra_urgent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second); // Подключаем макет второго экрана

        // Читаем данные, пришедшие с первого экрана
        String name = getIntent().getStringExtra(EXTRA_NAME);
        if (name == null) {
            name = ""; // Если данных нет, подставляем пустую строку
        }
        int priority = getIntent().getIntExtra(EXTRA_PRIORITY, 0);
        boolean urgent = getIntent().getBooleanExtra(EXTRA_URGENT, false);

        // Находим текстовые поля
        TextView tvName = findViewById(R.id.tvName);
        TextView tvPriority = findViewById(R.id.tvPriority);
        TextView tvUrgent = findViewById(R.id.tvUrgent);

        // Показываем данные
        tvName.setText(getString(R.string.second_name, name));
        tvPriority.setText(getString(R.string.second_priority, priority));
        tvUrgent.setText(getString(R.string.second_urgent,
                getString(urgent ? R.string.urgent_yes : R.string.urgent_no)));
    }

    // Кнопка «Назад» (задана в XML через android:onClick): закрываем экран
    public void onBackClick(View view) {
        finish();
    }
}