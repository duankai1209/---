package com.example.choosehelper202;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ResetPasswordActivity extends AppCompatActivity {
    private EditText etUsername, etNewPwd, etConfirm;
    private Button btnReset;
    private ImageView bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        bg = findViewById(R.id.bg);
        BgUtil.setGlobalBg(this, bg);

        etUsername = findViewById(R.id.et_username);
        etNewPwd = findViewById(R.id.et_new_pwd);
        etConfirm = findViewById(R.id.et_confirm);
        btnReset = findViewById(R.id.btn_reset);

        btnReset.setOnClickListener(v -> {
            AnimUtil.clickAnim(v);
            String username = etUsername.getText().toString().trim();
            String newPwd = etNewPwd.getText().toString().trim();
            String confirm = etConfirm.getText().toString().trim();
            if (username.isEmpty() || newPwd.isEmpty()) {
                Toast.makeText(this, "请填写完整", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!newPwd.equals(confirm)) {
                Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                return;
            }
            SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
            if (!sp.contains(username)) {
                Toast.makeText(this, "用户名不存在", Toast.LENGTH_SHORT).show();
                return;
            }
            sp.edit().putString(username, newPwd).apply();
            Toast.makeText(this, "密码重置成功，请登录", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}