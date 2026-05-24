package com.example.choosehelper202;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private EditText etAccount, etPwd, etPwd2, etName;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etAccount = findViewById(R.id.et_account);
        etPwd = findViewById(R.id.et_pwd);
        etPwd2 = findViewById(R.id.et_pwd2);
        etName = findViewById(R.id.et_name);
        Button btnRegister = findViewById(R.id.btn_register);
        TextView tvBack = findViewById(R.id.tv_back);
        sp = getSharedPreferences("UserDB", MODE_PRIVATE);

        tvBack.setOnClickListener(v -> finish());

        btnRegister.setOnClickListener(v -> {
            String account = etAccount.getText().toString();
            String pwd = etPwd.getText().toString();
            String pwd2 = etPwd2.getText().toString();
            String name = etName.getText().toString();

            if (account.isEmpty() || pwd.isEmpty() || name.isEmpty()) {
                Toast.makeText(this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Pattern.matches("^1[3-9]\\d{9}$", account)) {
                Toast.makeText(this, "手机号格式错误", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!pwd.equals(pwd2)) {
                Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                return;
            }

            sp.edit().putString(account, pwd).apply();
            sp.edit().putString("currentUser", account).apply();
            sp.edit().putString("nickName", name).apply();
            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            finish();
        });
    }
}