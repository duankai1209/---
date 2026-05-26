package com.example.choosehelper202;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private EditText etUsername, etPassword, etConfirm;
    private Button btnRegister;
    private ImageView bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        bg = findViewById(R.id.bg);
        BgUtil.setGlobalBg(this, bg);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        etConfirm = findViewById(R.id.et_confirm);
        btnRegister = findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(v -> {
            AnimUtil.clickAnim(v);
            String username = etUsername.getText().toString().trim();
            String pwd = etPassword.getText().toString().trim();
            String confirm = etConfirm.getText().toString().trim();
            if (username.isEmpty() || pwd.isEmpty()) {
                Toast.makeText(this, "用户名/密码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!pwd.equals(confirm)) {
                Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                return;
            }
            SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
            if (sp.contains(username)) {
                Toast.makeText(this, "用户名已存在", Toast.LENGTH_SHORT).show();
                return;
            }
            sp.edit().putString(username, pwd).apply();
            Toast.makeText(this, "注册成功，请登录", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}