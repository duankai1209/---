package com.example.choosehelper202;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        applyBg();

        Button btnEat = findViewById(R.id.btn_random_eat);
        Button btnDate = findViewById(R.id.btn_random_date);
        Button btnCloth = findViewById(R.id.btn_random_clothes);
        Button btnWeather = findViewById(R.id.btn_weather);
        Button btnDrink = findViewById(R.id.btn_drink);
        Button btnSport = findViewById(R.id.btn_sport);
        Button btnScenic = findViewById(R.id.btn_scenic);
        Button btnLottery = findViewById(R.id.btn_lottery);
        Button btnMotto = findViewById(R.id.btn_motto);
        Button btnGift = findViewById(R.id.btn_gift);
        Button btnTool = findViewById(R.id.btn_tool);
        Button btnSetting = findViewById(R.id.btn_setting);

        btnEat.setOnClickListener(v -> startActivity(new Intent(this, EatActivity.class)));
        btnDate.setOnClickListener(v -> startActivity(new Intent(this, DateActivity.class)));
        btnCloth.setOnClickListener(v -> startActivity(new Intent(this, ClothActivity.class)));
        btnWeather.setOnClickListener(v -> startActivity(new Intent(this, WeatherActivity.class)));
        btnDrink.setOnClickListener(v -> startActivity(new Intent(this, DrinkActivity.class)));
        btnSport.setOnClickListener(v -> startActivity(new Intent(this, SportActivity.class)));
        btnScenic.setOnClickListener(v -> startActivity(new Intent(this, ScenicActivity.class)));
        btnLottery.setOnClickListener(v -> startActivity(new Intent(this, LotteryActivity.class)));
        btnMotto.setOnClickListener(v -> startActivity(new Intent(this, MottoActivity.class)));
        btnGift.setOnClickListener(v -> startActivity(new Intent(this, GiftActivity.class)));
        btnTool.setOnClickListener(v -> startActivity(new Intent(this, RandomToolActivity.class)));
        btnSetting.setOnClickListener(v -> startActivity(new Intent(this, SettingActivity.class)));
    }

    private void applyBg() {
        SharedPreferences sp = getSharedPreferences("bgConfig", MODE_PRIVATE);
        String uriStr = sp.getString("uri", "");

        if (!uriStr.isEmpty()) {
            Uri uri = Uri.parse(uriStr);
            Glide.with(this)
                    .load(uri)
                    .into((android.widget.ImageView) findViewById(android.R.id.content));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        applyBg();
    }
}