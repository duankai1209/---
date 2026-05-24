package com.example.choosehelper202;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class SettingActivity extends AppCompatActivity {
    Button btnSelectBg, btnBack;
    ImageView ivBgPreview;
    private static final int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        btnSelectBg = findViewById(R.id.btn_select_bg);
        btnBack = findViewById(R.id.btn_back);
        ivBgPreview = findViewById(R.id.iv_bg_preview);

        // 加载已保存的背景预览
        SharedPreferences sp = getSharedPreferences("bgConfig", MODE_PRIVATE);
        String uriStr = sp.getString("uri", "");
        if (!uriStr.isEmpty()) {
            Glide.with(this).load(Uri.parse(uriStr)).centerCrop().into(ivBgPreview);
        }

        btnSelectBg.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_CODE);
        });

        btnBack.setOnClickListener(v -> finish());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                // 保存背景URI
                getSharedPreferences("bgConfig", MODE_PRIVATE).edit().putString("uri", uri.toString()).apply();
                // 显示预览（centerCrop适配拉伸问题）
                Glide.with(this).load(uri).centerCrop().into(ivBgPreview);
            }
        }
    }
}