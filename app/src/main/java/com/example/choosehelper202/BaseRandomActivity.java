package com.example.choosehelper202;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class BaseRandomActivity extends AppCompatActivity {

    TextView tvTitle;
    ListView lvList;
    EditText etInput;
    Button btnAdd, btnRandom, btnBack;
    ArrayAdapter<String> adapter;
    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_random);

        tvTitle = findViewById(R.id.tv_title);
        lvList = findViewById(R.id.lv_list);
        etInput = findViewById(R.id.et_input);
        btnAdd = findViewById(R.id.btn_add);
        btnRandom = findViewById(R.id.btn_random);
        btnBack = findViewById(R.id.btn_back);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        lvList.setAdapter(adapter);

        initData();

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

        btnBack.setOnClickListener(v -> finish());
    }

    protected void initData() {
        // 子类重写
    }
}