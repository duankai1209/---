package com.example.choosehelper202;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Random;

public class MottoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        TextView title = findViewById(R.id.tv_title);
        TextView res = findViewById(R.id.tv_result);
        Button btn = findViewById(R.id.btn_random);
        title.setText("每日名言");

        ArrayList<String> list = new ArrayList<>();
        list.add("坚持就是胜利");
        list.add("努力终有回报");
        list.add("今天也要加油");

        btn.setOnClickListener(v->res.setText(list.get(new Random().nextInt(list.size()))));
        findViewById(R.id.btn_back).setOnClickListener(v->finish());
    }
}