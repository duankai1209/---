package com.example.choosehelper202;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btnLogin, btnRegister, btnReset;
    private ImageView bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bg = findViewById(R.id.bg);
        BgUtil.setGlobalBg(this, bg);   // 修正：删除 activity: 前缀

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
        btnReset = findViewById(R.id.btn_reset);

        btnLogin.setOnClickListener(v -> {
            AnimUtil.clickAnim(v);
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
            String savedPwd = sp.getString(username, "");
            if (savedPwd.equals(password) && !username.isEmpty()) {
                sp.edit().putString("currentUser", username).apply();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
            }
        });

        btnRegister.setOnClickListener(v -> {
            AnimUtil.clickAnim(v);
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

        btnReset.setOnClickListener(v -> {
            AnimUtil.clickAnim(v);
            startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
        });
    }
}