package com.example.choosehelper202;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Random;

public class DrinkActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        TextView title = findViewById(R.id.tv_title);
        TextView res = findViewById(R.id.tv_result);
        Button btn = findViewById(R.id.btn_random);
        title.setText("今天喝什么");

        ArrayList<String> list = new ArrayList<>();
        list.add("奶茶"); list.add("咖啡"); list.add("果汁"); list.add("可乐"); list.add("矿泉水");

        btn.setOnClickListener(v->res.setText("推荐："+list.get(new Random().nextInt(list.size()))));
        findViewById(R.id.btn_back).setOnClickListener(v->finish());
    }
}