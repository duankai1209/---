package com.example.choosehelper202;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class RandomToolActivity extends AppCompatActivity {
    EditText etMin, etMax;
    TextView tvResult;
    Button btnNum, btnLetter, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_tool);

        etMin = findViewById(R.id.et_min);
        etMax = findViewById(R.id.et_max);
        tvResult = findViewById(R.id.tv_result);
        btnNum = findViewById(R.id.btn_random_num);
        btnLetter = findViewById(R.id.btn_random_letter);
        btnBack = findViewById(R.id.btn_back);

        // 随机数字
        btnNum.setOnClickListener(v -> {
            try {
                int min = Integer.parseInt(etMin.getText().toString().trim());
                int max = Integer.parseInt(etMax.getText().toString().trim());
                int random = new Random().nextInt(max - min + 1) + min;
                tvResult.setText("随机数字：" + random);
            } catch (Exception e) {
                tvResult.setText("请输入有效数字");
            }
        });

        // 随机字母
        btnLetter.setOnClickListener(v -> {
            char c = (char) (new Random().nextInt(26) + 'A');
            tvResult.setText("随机字母：" + c);
        });

        btnBack.setOnClickListener(v -> finish());
    }
}