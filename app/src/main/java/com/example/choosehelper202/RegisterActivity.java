package com.example.choosehelper202;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    EditText etAccount,etPwd;
    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etAccount=findViewById(R.id.et_account);
        etPwd=findViewById(R.id.et_pwd);
        btnRegister=findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(v->{
            String user=etAccount.getText().toString().trim();
            String pwd=etPwd.getText().toString().trim();
            if(user.isEmpty()||pwd.isEmpty()){Toast.makeText(this,"请输入完整信息",Toast.LENGTH_SHORT).show();return;}
            getSharedPreferences("user_info",MODE_PRIVATE).edit().putString("account",user).putString("password",pwd).apply();
            Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}