package com.example.choosehelper202;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText etUser = findViewById(R.id.et_user);
        EditText etPass = findViewById(R.id.et_pass);
        Button btnLogin = findViewById(R.id.btn_login);
        TextView tvReg = findViewById(R.id.tv_register);
        TextView tvReset = findViewById(R.id.tv_reset);
        SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);

        btnLogin.setOnClickListener(v -> {
            String u = etUser.getText().toString();
            String p = etPass.getText().toString();
            if(sp.getString(u, "").equals(p)){
                startActivity(new Intent(this, MainActivity.class));
            }else Toast.makeText(this, "账号或密码错误", Toast.LENGTH_SHORT).show();
        });
        tvReg.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
        tvReset.setOnClickListener(v -> startActivity(new Intent(this, ResetPasswordActivity.class)));
    }
}