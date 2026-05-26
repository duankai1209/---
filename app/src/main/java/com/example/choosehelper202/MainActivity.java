package com.example.choosehelper202;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnEat, btnDate, btnCloth, btnWeather, btnRandomTool, btnUser, btnSetting;
    private Button btnGift, btnLottery, btnMotto, btnScenic, btnSchedule, btnSport, btnStudy;
    private ImageView bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bg = findViewById(R.id.bg);
        BgUtil.setGlobalBg(this, bg);

        // 绑定按钮
        btnEat = findViewById(R.id.btn_eat);
        btnDate = findViewById(R.id.btn_date);
        btnCloth = findViewById(R.id.btn_cloth);
        btnWeather = findViewById(R.id.btn_weather);
        btnRandomTool = findViewById(R.id.btn_random_tool);
        btnUser = findViewById(R.id.btn_user);
        btnSetting = findViewById(R.id.btn_setting);
        btnGift = findViewById(R.id.btn_gift);
        btnLottery = findViewById(R.id.btn_lottery);
        btnMotto = findViewById(R.id.btn_motto);
        btnScenic = findViewById(R.id.btn_scenic);
        btnSchedule = findViewById(R.id.btn_schedule);
        btnSport = findViewById(R.id.btn_sport);
        btnStudy = findViewById(R.id.btn_study);

        // 设置点击事件
        btnEat.setOnClickListener(v -> { AnimUtil.clickAnim(v); startActivity(new Intent(this, EatActivity.class)); });
        btnDate.setOnClickListener(v -> { AnimUtil.clickAnim(v); startActivity(new Intent(this, DateActivity.class)); });
        btnCloth.setOnClickListener(v -> { AnimUtil.clickAnim(v); startActivity(new Intent(this, ClothActivity.class)); });
        btnWeather.setOnClickListener(v -> { AnimUtil.clickAnim(v); startActivity(new Intent(this, WeatherActivity.class)); });
        btnRandomTool.setOnClickListener(v -> { AnimUtil.clickAnim(v); startActivity(new Intent(this, RandomToolActivity.class)); });
        btnUser.setOnClickListener(v -> { AnimUtil.clickAnim(v); startActivity(new Intent(this, UserActivity.class)); });
        btnSetting.setOnClickListener(v -> { AnimUtil.clickAnim(v); startActivity(new Intent(this, SettingActivity.class)); });
        btnGift.setOnClickListener(v -> { AnimUtil.clickAnim(v); startActivity(new Intent(this, GiftActivity.class)); });
        btnLottery.setOnClickListener(v -> { AnimUtil.clickAnim(v); startActivity(new Intent(this, LotteryActivity.class)); });
        btnMotto.setOnClickListener(v -> { AnimUtil.clickAnim(v); startActivity(new Intent(this, MottoActivity.class)); });
        btnScenic.setOnClickListener(v -> { AnimUtil.clickAnim(v); startActivity(new Intent(this, ScenicActivity.class)); });
        btnSchedule.setOnClickListener(v -> { AnimUtil.clickAnim(v); startActivity(new Intent(this, ScheduleActivity.class)); });
        btnSport.setOnClickListener(v -> { AnimUtil.clickAnim(v); startActivity(new Intent(this, SportActivity.class)); });
        btnStudy.setOnClickListener(v -> { AnimUtil.clickAnim(v); startActivity(new Intent(this, StudyActivity.class)); });
    }
}