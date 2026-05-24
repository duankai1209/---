package com.example.choosehelper202;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Random;

public class EatActivity extends AppCompatActivity {
    private ArrayList<String> foodList;
    private ArrayAdapter<String> adapter;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eat);

        tvResult = findViewById(R.id.tv_eat_result);
        ListView listView = findViewById(R.id.lv_food_list);
        EditText etInput = findViewById(R.id.et_food_input);
        Button btnRandom = findViewById(R.id.btn_eat_random);
        Button btnAdd = findViewById(R.id.btn_food_add);
        Button btnBack = findViewById(R.id.btn_eat_back);

        foodList = new ArrayList<>();
        foodList.add("火锅");
        foodList.add("烤肉");
        foodList.add("米线");
        foodList.add("汉堡");
        foodList.add("炒饭");
        foodList.add("螺蛳粉");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foodList);
        listView.setAdapter(adapter);

        btnRandom.setOnClickListener(v -> {
            if (foodList.isEmpty()) {
                tvResult.setText("请添加选项");
                return;
            }
            tvResult.setText("推荐：" + foodList.get(new Random().nextInt(foodList.size())));
        });

        btnAdd.setOnClickListener(v -> {
            String s = etInput.getText().toString().trim();
            if (!s.isEmpty()) {
                foodList.add(s);
                adapter.notifyDataSetChanged();
                etInput.setText("");
            }
        });

        listView.setOnItemLongClickListener((parent, view, pos, id) -> {
            new AlertDialog.Builder(this)
                    .setTitle("操作")
                    .setItems(new String[]{"删除", "修改"}, (d, w) -> {
                        if (w == 0) {
                            foodList.remove(pos);
                        } else {
                            EditText edit = new EditText(this);
                            edit.setText(foodList.get(pos));
                            new AlertDialog.Builder(this).setView(edit)
                                    .setPositiveButton("确定", (di, wh) -> {
                                        String newStr = edit.getText().toString().trim();
                                        if (!newStr.isEmpty()) foodList.set(pos, newStr);
                                    }).show();
                        }
                        adapter.notifyDataSetChanged();
                    }).show();
            return true;
        });

        btnBack.setOnClickListener(v -> finish());
    }
}