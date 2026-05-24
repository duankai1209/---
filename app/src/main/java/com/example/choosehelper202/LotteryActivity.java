package com.example.choosehelper202;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class LotteryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        TextView title = findViewById(R.id.tv_title);
        TextView res = findViewById(R.id.tv_result);
        Button btn = findViewById(R.id.btn_random);
        title.setText("今日运势");
        String[] arr = {"大吉","吉","中平","小凶","平"};
        btn.setOnClickListener(v->res.setText("今日运势："+arr[new Random().nextInt(arr.length)]));
        findViewById(R.id.btn_back).setOnClickListener(v->finish());
    }
}