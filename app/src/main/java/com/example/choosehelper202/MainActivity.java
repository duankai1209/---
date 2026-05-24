package com.example.choosehelper202;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        applyBackground();

        Button btn_random_eat = findViewById(R.id.btn_random_eat);
        Button btn_random_date = findViewById(R.id.btn_random_date);
        Button btn_random_clothes = findViewById(R.id.btn_random_clothes);
        Button btn_weather = findViewById(R.id.btn_weather);
        Button btn_setting = findViewById(R.id.btn_setting);
        Button btn_drink = findViewById(R.id.btn_drink);
        Button btn_sport = findViewById(R.id.btn_sport);
        Button btn_scenic = findViewById(R.id.btn_scenic);
        Button btn_lottery = findViewById(R.id.btn_lottery);
        Button btn_schedule = findViewById(R.id.btn_schedule);
        Button btn_motto = findViewById(R.id.btn_motto);
        Button btn_gift = findViewById(R.id.btn_gift);
        Button btn_study = findViewById(R.id.btn_study);

        btn_random_eat.setOnClickListener(v -> startActivity(new Intent(this, EatActivity.class)));
        btn_random_date.setOnClickListener(v -> startActivity(new Intent(this, DateActivity.class)));
        btn_random_clothes.setOnClickListener(v -> startActivity(new Intent(this, ClothActivity.class)));
        btn_weather.setOnClickListener(v -> startActivity(new Intent(this, WeatherActivity.class)));
        btn_setting.setOnClickListener(v -> startActivity(new Intent(this, SettingActivity.class)));
        btn_drink.setOnClickListener(v -> startActivity(new Intent(this, DrinkActivity.class)));
        btn_sport.setOnClickListener(v -> startActivity(new Intent(this, SportActivity.class)));
        btn_scenic.setOnClickListener(v -> startActivity(new Intent(this, ScenicActivity.class)));
        btn_lottery.setOnClickListener(v -> startActivity(new Intent(this, LotteryActivity.class)));
        btn_schedule.setOnClickListener(v -> startActivity(new Intent(this, ScheduleActivity.class)));
        btn_motto.setOnClickListener(v -> startActivity(new Intent(this, MottoActivity.class)));
        btn_gift.setOnClickListener(v -> startActivity(new Intent(this, GiftActivity.class)));
        btn_study.setOnClickListener(v -> startActivity(new Intent(this, StudyActivity.class)));
    }

    private void applyBackground() {
        View root = findViewById(android.R.id.content);
        SharedPreferences sp = getSharedPreferences("bgConfig", MODE_PRIVATE);
        int type = sp.getInt("bg_type", 1);
        if (type == 2) {
            String uriStr = sp.getString("custom_bg_uri", "");
            if (!uriStr.isEmpty()) {
                try {
                    Drawable drawable = Drawable.createFromStream(getContentResolver().openInputStream(Uri.parse(uriStr)), null);
                    root.setBackground(drawable);
                } catch (FileNotFoundException e) {
                    root.setBackgroundColor(0xFFF5F7FA);
                }
                return;
            }
        }
        root.setBackgroundColor(0xFFF5F7FA);
    }

    @Override
    protected void onResume() {
        super.onResume();
        applyBackground();
    }
}