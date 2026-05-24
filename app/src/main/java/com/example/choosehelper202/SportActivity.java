package com.example.choosehelper202;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Random;

public class SportActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        TextView title = findViewById(R.id.tv_title);
        TextView res = findViewById(R.id.tv_result);
        Button btn = findViewById(R.id.btn_random);
        title.setText("今天做什么运动");

        ArrayList<String> list = new ArrayList<>();
        list.add("跑步"); list.add("跳绳"); list.add("健身"); list.add("篮球"); list.add("游泳");

        btn.setOnClickListener(v->res.setText("推荐："+list.get(new Random().nextInt(list.size()))));
        findViewById(R.id.btn_back).setOnClickListener(v->finish());
    }
}