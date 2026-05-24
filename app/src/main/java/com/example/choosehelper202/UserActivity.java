package com.example.choosehelper202;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {
    EditText etNick;
    Button btnSave,btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        etNick=findViewById(R.id.et_nick);
        btnSave=findViewById(R.id.btn_save);
        btnLogout=findViewById(R.id.btn_logout);
        SharedPreferences sp=getSharedPreferences("user_info",MODE_PRIVATE);
        etNick.setText(sp.getString("nickname","用户"));
        btnSave.setOnClickListener(v->{
            sp.edit().putString("nickname",etNick.getText().toString().trim()).apply();
            Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();
        });
        btnLogout.setOnClickListener(v->{
            startActivity(new Intent(this,LoginActivity.class));
            finishAffinity();
        });
    }
}