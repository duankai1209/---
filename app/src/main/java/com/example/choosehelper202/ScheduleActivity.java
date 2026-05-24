package com.example.choosehelper202;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ScheduleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        TextView title = findViewById(R.id.tv_title);
        TextView res = findViewById(R.id.tv_result);
        Button btn = findViewById(R.id.btn_random);
        title.setText("健康作息计划");
        res.setText("7:00 起床\n12:00 午餐\n18:00 晚餐\n23:00 睡觉");
        btn.setText("查看计划");
        btn.setOnClickListener(v->res.setText("7:00 起床\n12:00 午餐\n18:00 晚餐\n23:00 睡觉"));
        findViewById(R.id.btn_back).setOnClickListener(v->finish());
    }
}