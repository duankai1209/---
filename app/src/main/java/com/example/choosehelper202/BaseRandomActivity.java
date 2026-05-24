package com.example.choosehelper202;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public abstract class BaseRandomActivity extends AppCompatActivity {

    protected ArrayList<String> list;
    protected ArrayAdapter<String> adapter;
    protected EditText etInput;
    protected ListView lvList;
    protected Button btnAdd, btnRandom, btnBack;
    protected TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_random);

        initView();
        initData();
        setupListeners();
    }

    private void initView() {
        tvTitle = findViewById(R.id.tv_title);
        lvList = findViewById(R.id.lv_list);
        etInput = findViewById(R.id.et_input);
        btnAdd = findViewById(R.id.btn_add);
        btnRandom = findViewById(R.id.btn_random);
        btnBack = findViewById(R.id.btn_back);
    }

    protected abstract void initData();

    private void setupListeners() {
        btnAdd.setOnClickListener(v -> {
            String text = etInput.getText().toString().trim();
            if (!text.isEmpty()) {
                list.add(text);
                adapter.notifyDataSetChanged();
                etInput.setText("");
            }
        });

        btnRandom.setOnClickListener(v -> {
            if (!list.isEmpty()) {
                int index = (int) (Math.random() * list.size());
                tvTitle.setText("结果：" + list.get(index));
            }
        });

        lvList.setOnItemLongClickListener((parent, view, position, id) -> {
            new AlertDialog.Builder(this)
                    .setItems(new String[]{"删除", "修改"}, (dialog, which) -> {
                        if (which == 0) {
                            list.remove(position);
                        } else {
                            EditText editText = new EditText(this);
                            editText.setText(list.get(position));
                            new AlertDialog.Builder(this)
                                    .setView(editText)
                                    .setPositiveButton("确定", (d, w) -> {
                                        String newText = editText.getText().toString().trim();
                                        if (!newText.isEmpty()) {
                                            list.set(position, newText);
                                        }
                                    })
                                    .show();
                        }
                        adapter.notifyDataSetChanged();
                    })
                    .show();
            return true;
        });

        btnBack.setOnClickListener(v -> finish());
    }
}