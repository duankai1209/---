package com.example.choosehelper202;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ResetPasswordActivity extends AppCompatActivity {
    EditText etOld,etNew,etConfirm;
    Button btnReset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        etOld=findViewById(R.id.et_old_pwd);
        etNew=findViewById(R.id.et_new_pwd);
        etConfirm=findViewById(R.id.et_confirm_pwd);
        btnReset=findViewById(R.id.btn_reset);
        SharedPreferences sp=getSharedPreferences("user_info",MODE_PRIVATE);
        btnReset.setOnClickListener(v->{
            String old=etOld.getText().toString().trim();
            String n=etNew.getText().toString().trim();
            String c=etConfirm.getText().toString().trim();
            if(!old.equals(sp.getString("password","123456"))){Toast.makeText(this,"旧密码错误",Toast.LENGTH_SHORT).show();return;}
            if(!n.equals(c)){Toast.makeText(this,"两次密码不一致",Toast.LENGTH_SHORT).show();return;}
            sp.edit().putString("password",n).apply();
            Toast.makeText(this,"修改成功",Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}