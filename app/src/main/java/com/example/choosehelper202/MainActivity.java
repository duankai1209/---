package com.example.choosehelper202;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {
    Button btnEat, btnDate, btnCloth, btnWeather, btnUser, btnTool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 绑定控件
        btnEat = findViewById(R.id.btn_eat);
        btnDate = findViewById(R.id.btn_date);
        btnCloth = findViewById(R.id.btn_cloth);
        btnWeather = findViewById(R.id.btn_weather);
        btnUser = findViewById(R.id.btn_user);
        btnTool = findViewById(R.id.btn_tool);

        // 设置点击事件
        btnEat.setOnClickListener(v -> startActivity(new Intent(this, EatActivity.class)));
        btnDate.setOnClickListener(v -> startActivity(new Intent(this, DateActivity.class)));
        btnCloth.setOnClickListener(v -> startActivity(new Intent(this, ClothActivity.class)));
        btnWeather.setOnClickListener(v -> startActivity(new Intent(this, WeatherActivity.class)));
        btnUser.setOnClickListener(v -> startActivity(new Intent(this, UserActivity.class)));
        btnTool.setOnClickListener(v -> startActivity(new Intent(this, RandomToolActivity.class)));

        // 应用背景（修复所有报错）
        applyBackground();
    }

    private void applyBackground() {
        try {
            // 修复 Kotlin 语法，使用纯 Java 写法
            SharedPreferences sp = getSharedPreferences("bgConfig", MODE_PRIVATE);
            String uriStr = sp.getString("uri", "");

            if (!uriStr.isEmpty()) {
                Uri uri = Uri.parse(uriStr);
                // 修复 Glide 调用方式，明确指定类型消除方法匹配冲突
                ImageView backgroundView = findViewById(android.R.id.content);
                Glide.with(this)
                        .load(uri)
                        .into(backgroundView);
            }
        } catch (Exception e) {
            // 捕获异常，防止设置背景失败导致闪退
            e.printStackTrace();
        }
    }
}