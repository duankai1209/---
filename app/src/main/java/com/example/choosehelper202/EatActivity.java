package com.example.choosehelper202;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Random;

public class EatActivity extends AppCompatActivity {
    ArrayList<String> foodList;
    ArrayAdapter<String> adapter;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 统一使用通用布局文件
        setContentView(R.layout.activity_base_random);

        TextView tvTitle = findViewById(R.id.tv_title);
        tvResult = findViewById(R.id.tv_result);
        ListView listView = findViewById(R.id.lv_list);
        EditText etInput = findViewById(R.id.et_input);
        Button btnRandom = findViewById(R.id.btn_random);
        Button btnAdd = findViewById(R.id.btn_add);
        Button btnBack = findViewById(R.id.btn_back);

        // 设置标题
        tvTitle.setText("美食选择");

        // 初始化食物列表
        foodList = new ArrayList<>();
        foodList.add("火锅");
        foodList.add("烧烤");
        foodList.add("奶茶");
        foodList.add("麻辣烫");
        foodList.add("盖浇饭");

        // 绑定列表适配器
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foodList);
        listView.setAdapter(adapter);

        // 随机按钮逻辑
        btnRandom.setOnClickListener(v -> {
            if (foodList.isEmpty()) {
                tvResult.setText("请先添加食物选项");
            } else {
                int randomIndex = new Random().nextInt(foodList.size());
                tvResult.setText("推荐：" + foodList.get(randomIndex));
            }
        });

        // 添加按钮逻辑
        btnAdd.setOnClickListener(v -> {
            String newFood = etInput.getText().toString().trim();
            if (!newFood.isEmpty()) {
                foodList.add(newFood);
                adapter.notifyDataSetChanged();
                etInput.setText("");
            }
        });

        // 长按列表项可删除/修改
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            new AlertDialog.Builder(EatActivity.this)
                    .setTitle("操作")
                    .setItems(new String[]{"删除", "修改"}, (dialog, which) -> {
                        if (which == 0) {
                            // 删除
                            foodList.remove(position);
                            adapter.notifyDataSetChanged();
                        } else {
                            // 修改
                            EditText editText = new EditText(EatActivity.this);
                            editText.setText(foodList.get(position));
                            new AlertDialog.Builder(EatActivity.this)
                                    .setTitle("修改内容")
                                    .setView(editText)
                                    .setPositiveButton("确定", (d, w) -> {
                                        String updatedFood = editText.getText().toString().trim();
                                        if (!updatedFood.isEmpty()) {
                                            foodList.set(position, updatedFood);
                                            adapter.notifyDataSetChanged();
                                        }
                                    })
                                    .setNegativeButton("取消", null)
                                    .show();
                        }
                    })
                    .show();
            return true;
        });

        // 返回按钮
        btnBack.setOnClickListener(v -> finish());
    }
}