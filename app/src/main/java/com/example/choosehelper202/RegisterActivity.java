package com.example.choosehelper202;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        EditText etUser = findViewById(R.id.et_user);
        EditText etPass = findViewById(R.id.et_pass);
        Button btnReg = findViewById(R.id.btn_register);
        SharedPreferences.Editor ed = getSharedPreferences("user", MODE_PRIVATE).edit();

        btnReg.setOnClickListener(v -> {
            String u = etUser.getText().toString();
            String p = etPass.getText().toString();
            ed.putString(u,p).apply();
            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}