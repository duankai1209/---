package com.example.choosehelper202;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Random;

public class DateActivity extends AppCompatActivity {
    ArrayList<String> dateList;
    ArrayAdapter<String> adapter;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 这里改成正确的布局文件名
        setContentView(R.layout.activity_base_random);

        TextView tvTitle = findViewById(R.id.tv_title);
        tvResult = findViewById(R.id.tv_result);
        ListView listView = findViewById(R.id.lv_list);
        EditText etInput = findViewById(R.id.et_input);
        Button btnRandom = findViewById(R.id.btn_random);
        Button btnAdd = findViewById(R.id.btn_add);
        Button btnBack = findViewById(R.id.btn_back);

        tvTitle.setText("约会游玩");

        dateList = new ArrayList<>();
        dateList.add("看电影");
        dateList.add("逛公园");
        dateList.add("喝奶茶");
        dateList.add("桌游");
        dateList.add("逛街");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dateList);
        listView.setAdapter(adapter);

        btnRandom.setOnClickListener(v -> {
            if (dateList.isEmpty()) {
                tvResult.setText("请先添加约会项目");
            } else {
                int randomIndex = new Random().nextInt(dateList.size());
                tvResult.setText("推荐：" + dateList.get(randomIndex));
            }
        });

        btnAdd.setOnClickListener(v -> {
            String newItem = etInput.getText().toString().trim();
            if (!newItem.isEmpty()) {
                dateList.add(newItem);
                adapter.notifyDataSetChanged();
                etInput.setText("");
            }
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            new AlertDialog.Builder(DateActivity.this)
                    .setTitle("操作")
                    .setItems(new String[]{"删除", "修改"}, (dialog, which) -> {
                        if (which == 0) {
                            // 删除
                            dateList.remove(position);
                            adapter.notifyDataSetChanged();
                        } else {
                            // 修改
                            EditText editText = new EditText(DateActivity.this);
                            editText.setText(dateList.get(position));
                            new AlertDialog.Builder(DateActivity.this)
                                    .setTitle("修改内容")
                                    .setView(editText)
                                    .setPositiveButton("确定", (d, w) -> {
                                        String updatedText = editText.getText().toString().trim();
                                        if (!updatedText.isEmpty()) {
                                            dateList.set(position, updatedText);
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

        btnBack.setOnClickListener(v -> finish());
    }
}