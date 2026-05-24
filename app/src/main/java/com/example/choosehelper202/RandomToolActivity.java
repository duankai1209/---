package com.example.choosehelper202;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class RandomToolActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_tool);

        EditText etMin = findViewById(R.id.et_min);
        EditText etMax = findViewById(R.id.et_max);
        TextView tvNumRes = findViewById(R.id.tv_num_res);
        Button btnNum = findViewById(R.id.btn_num);

        EditText etLen = findViewById(R.id.et_len);
        TextView tvLetterRes = findViewById(R.id.tv_letter_res);
        Button btnLetter = findViewById(R.id.btn_letter);

        btnNum.setOnClickListener(v -> {
            try {
                int min = Integer.parseInt(etMin.getText().toString());
                int max = Integer.parseInt(etMax.getText().toString());
                int result = new Random().nextInt(max - min + 1) + min;
                tvNumRes.setText("随机数：" + result);
            } catch (Exception e) {
                tvNumRes.setText("输入错误");
            }
        });

        btnLetter.setOnClickListener(v -> {
            try {
                int len = Integer.parseInt(etLen.getText().toString());
                StringBuilder sb = new StringBuilder();
                Random random = new Random();
                for (int i = 0; i < len; i++) {
                    char c = (char) (random.nextInt(26) + 'A');
                    sb.append(c);
                }
                tvLetterRes.setText("随机字母：" + sb);
            } catch (Exception e) {
                tvLetterRes.setText("输入错误");
            }
        });

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }
}