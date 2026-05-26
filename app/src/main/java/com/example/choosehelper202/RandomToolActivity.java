package com.example.choosehelper202;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class RandomToolActivity extends AppCompatActivity {
    private TextView tvResult;
    private Button btnDice, btnCoin;
    // 随机数字
    private EditText etMin, etMax;
    private Button btnNumber;
    // 随机字母
    private Button btnLetter;
    private CheckBox cbUpperCase;
    private ImageView bg;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_tool);

        bg = findViewById(R.id.bg);
        BgUtil.setGlobalBg(this, bg);

        tvResult = findViewById(R.id.tv_result);
        btnDice = findViewById(R.id.btn_dice);
        btnCoin = findViewById(R.id.btn_coin);
        etMin = findViewById(R.id.et_min);
        etMax = findViewById(R.id.et_max);
        btnNumber = findViewById(R.id.btn_number);
        btnLetter = findViewById(R.id.btn_letter);
        cbUpperCase = findViewById(R.id.cb_uppercase);

        btnDice.setOnClickListener(v -> {
            AnimUtil.clickAnim(v);
            int num = random.nextInt(6) + 1;
            tvResult.setText("🎲 骰子点数：" + num);
        });

        btnCoin.setOnClickListener(v -> {
            AnimUtil.clickAnim(v);
            boolean isHeads = random.nextBoolean();
            tvResult.setText("🪙 硬币结果：" + (isHeads ? "正面" : "反面"));
        });

        btnNumber.setOnClickListener(v -> {
            AnimUtil.clickAnim(v);
            String minStr = etMin.getText().toString().trim();
            String maxStr = etMax.getText().toString().trim();
            if (minStr.isEmpty() || maxStr.isEmpty()) {
                Toast.makeText(this, "请输入最小值和最大值", Toast.LENGTH_SHORT).show();
                return;
            }
            int min = Integer.parseInt(minStr);
            int max = Integer.parseInt(maxStr);
            if (min > max) {
                Toast.makeText(this, "最小值不能大于最大值", Toast.LENGTH_SHORT).show();
                return;
            }
            int num = random.nextInt(max - min + 1) + min;
            tvResult.setText("🔢 随机数字 (" + min + "~" + max + ")：" + num);
        });

        btnLetter.setOnClickListener(v -> {
            AnimUtil.clickAnim(v);
            char base = cbUpperCase.isChecked() ? 'A' : 'a';
            char letter = (char) (base + random.nextInt(26));
            tvResult.setText("🔤 随机字母：" + letter);
        });
    }
}