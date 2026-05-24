package com.example.choosehelper202;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Random;

public class DateActivity extends AppCompatActivity {
    private ArrayList<String> dateList;
    private ArrayAdapter<String> adapter;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        tvResult = findViewById(R.id.tv_date_result);
        ListView listView = findViewById(R.id.lv_date_list);
        EditText etInput = findViewById(R.id.et_date_input);
        Button btnRandom = findViewById(R.id.btn_date_random);
        Button btnAdd = findViewById(R.id.btn_date_add);
        Button btnBack = findViewById(R.id.btn_date_back);

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
                tvResult.setText("请添加选项");
                return;
            }
            tvResult.setText("推荐：" + dateList.get(new Random().nextInt(dateList.size())));
        });

        btnAdd.setOnClickListener(v -> {
            String s = etInput.getText().toString().trim();
            if (!s.isEmpty()) {
                dateList.add(s);
                adapter.notifyDataSetChanged();
                etInput.setText("");
            }
        });

        listView.setOnItemLongClickListener((parent, view, pos, id) -> {
            new AlertDialog.Builder(this)
                    .setTitle("操作")
                    .setItems(new String[]{"删除", "修改"}, (d, w) -> {
                        if (w == 0) {
                            dateList.remove(pos);
                        } else {
                            EditText edit = new EditText(this);
                            edit.setText(dateList.get(pos));
                            new AlertDialog.Builder(this).setView(edit)
                                    .setPositiveButton("确定", (di, wh) -> {
                                        String newStr = edit.getText().toString().trim();
                                        if (!newStr.isEmpty()) dateList.set(pos, newStr);
                                    }).show();
                        }
                        adapter.notifyDataSetChanged();
                    }).show();
            return true;
        });

        btnBack.setOnClickListener(v -> finish());
    }
}