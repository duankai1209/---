package com.example.choosehelper202;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ClothActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloth);

        TextView tvTip = findViewById(R.id.tv_cloth_tip);
        Button btnBack = findViewById(R.id.btn_cloth_back);

        String temp = getIntent().getStringExtra("weather");
        if(temp!=null){
            int t = Integer.parseInt(temp);
            if(t<10) tvTip.setText("天气寒冷\n建议：羽绒服+毛衣+保暖裤");
            else if(t<20) tvTip.setText("天气凉爽\n建议：卫衣+长裤+外套");
            else if(t<28) tvTip.setText("天气温暖\n建议：T恤+长裤");
            else tvTip.setText("天气炎热\n建议：短袖+短裤/裙子");
        }

        btnBack.setOnClickListener(v -> finish());
    }
}