package com.example.choosehelper202;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Random;

public class GiftActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        TextView title = findViewById(R.id.tv_title);
        TextView res = findViewById(R.id.tv_result);
        Button btn = findViewById(R.id.btn_random);
        title.setText("礼物推荐");

        ArrayList<String> list = new ArrayList<>();
        list.add("鲜花"); list.add("巧克力"); list.add("手表"); list.add("香水");

        btn.setOnClickListener(v->res.setText("推荐："+list.get(new Random().nextInt(list.size()))));
        findViewById(R.id.btn_back).setOnClickListener(v->finish());
    }
}