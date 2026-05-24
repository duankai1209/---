package com.example.choosehelper202;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ResetPasswordActivity extends AppCompatActivity {

    // 1. 先声明控件变量（你之前漏了这步）
    EditText etOldPwd, etNewPwd, etConfirmPwd;
    Button btnReset;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        // 2. 初始化控件（现在ID和布局文件完全对应）
        etOldPwd = findViewById(R.id.et_old_pwd);
        etNewPwd = findViewById(R.id.et_new_pwd);
        etConfirmPwd = findViewById(R.id.et_confirm_pwd);
        btnReset = findViewById(R.id.btn_reset);

        // 3. 初始化SharedPreferences（去掉了Kotlin语法）
        sp = getSharedPreferences("user_info", MODE_PRIVATE);

        // 4. 设置按钮逻辑
        btnReset.setOnClickListener(v -> {
            String oldPwd = etOldPwd.getText().toString().trim();
            String newPwd = etNewPwd.getText().toString().trim();
            String confirmPwd = etConfirmPwd.getText().toString().trim();

            // 验证旧密码
            String storedPwd = sp.getString("password", "123456");
            if (!oldPwd.equals(storedPwd)) {
                Toast.makeText(this, "旧密码错误", Toast.LENGTH_SHORT).show();
                return;
            }

            // 验证两次新密码一致
            if (!newPwd.equals(confirmPwd)) {
                Toast.makeText(this, "两次新密码不一致", Toast.LENGTH_SHORT).show();
                return;
            }

            // 更新密码
            sp.edit().putString("password", newPwd).apply();
            Toast.makeText(this, "密码重置成功", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}