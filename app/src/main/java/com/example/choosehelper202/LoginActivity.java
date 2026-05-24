package com.example.choosehelper202;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    EditText etAccount,etPwd;
    Button btnLogin,btnGoRegister,btnGoReset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etAccount=findViewById(R.id.et_account);
        etPwd=findViewById(R.id.et_pwd);
        btnLogin=findViewById(R.id.btn_login);
        btnGoRegister=findViewById(R.id.btn_go_register);
        btnGoReset=findViewById(R.id.btn_go_reset);
        SharedPreferences sp=getSharedPreferences("user_info",MODE_PRIVATE);
        btnLogin.setOnClickListener(v->{
            String user=etAccount.getText().toString().trim();
            String pwd=etPwd.getText().toString().trim();
            String realUser=sp.getString("account","admin");
            String realPwd=sp.getString("password","123456");
            if(user.equals(realUser)&&pwd.equals(realPwd)){
                startActivity(new Intent(this,MainActivity.class));
                finish();
            }else Toast.makeText(this,"账号或密码错误",Toast.LENGTH_SHORT).show();
        });
        btnGoRegister.setOnClickListener(v->startActivity(new Intent(this,RegisterActivity.class)));
        btnGoReset.setOnClickListener(v->startActivity(new Intent(this,ResetPasswordActivity.class)));
    }
}