package com.example.choosehelper202;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Random;

public class StudyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        TextView title = findViewById(R.id.tv_title);
        TextView res = findViewById(R.id.tv_result);
        Button btn = findViewById(R.id.btn_random);
        title.setText("学习计划");

        ArrayList<String> list = new ArrayList<>();
        list.add("阅读30分钟"); list.add("背单词"); list.add("写笔记");

        btn.setOnClickListener(v->res.setText("今日任务："+list.get(new Random().nextInt(list.size()))));
        findViewById(R.id.btn_back).setOnClickListener(v->finish());
    }
}