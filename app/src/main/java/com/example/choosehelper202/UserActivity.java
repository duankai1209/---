package com.example.choosehelper202;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        sp = getSharedPreferences("UserDB",MODE_PRIVATE);

        TextView tvName = findViewById(R.id.tv_user_name);
        TextView tvFood = findViewById(R.id.tv_food_collect);
        TextView tvCloth = findViewById(R.id.tv_cloth_collect);
        TextView tvDate = findViewById(R.id.tv_date_collect);
        Button btnBack = findViewById(R.id.btn_user_back);
        Button btnExit = findViewById(R.id.btn_exit);

        String nick = sp.getString("nickName","匿名用户");
        tvName.setText("当前用户："+nick);

        SharedPreferences eatSp = getSharedPreferences("EatData",MODE_PRIVATE);
        SharedPreferences clothSp = getSharedPreferences("ClothData",MODE_PRIVATE);
        SharedPreferences dateSp = getSharedPreferences("DateData",MODE_PRIVATE);

        tvFood.setText("美食收藏："+eatSp.getString("collect_food","暂无收藏"));
        tvCloth.setText("穿搭收藏："+clothSp.getString("collect_cloth","暂无收藏"));
        tvDate.setText("游玩收藏："+dateSp.getString("collect_date","暂无收藏"));

        btnBack.setOnClickListener(v -> finish());
        btnExit.setOnClickListener(v -> {
            sp.edit().remove("currentUser").apply();
            Intent intent = new Intent(UserActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}