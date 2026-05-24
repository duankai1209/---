package com.example.choosehelper202;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {
    Button btnSelectBg, btnBack;
    private static final int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        btnSelectBg = findViewById(R.id.btn_select_bg);
        btnBack = findViewById(R.id.btn_back);

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
                getSharedPreferences("bgConfig", MODE_PRIVATE)
                        .edit().putString("uri", uri.toString()).apply();
            }
        }
    }
}