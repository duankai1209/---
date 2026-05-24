package com.example.choosehelper202;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {
    EditText etNick;
    Button btnSave, btnLogout;
    TextView tvEatFav, tvDateFav, tvEatHistory, tvDateHistory, tvClothHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        etNick = findViewById(R.id.et_nick);
        btnSave = findViewById(R.id.btn_save);
        btnLogout = findViewById(R.id.btn_logout);
        tvEatFav = findViewById(R.id.tv_eat_fav);
        tvDateFav = findViewById(R.id.tv_date_fav);
        tvEatHistory = findViewById(R.id.tv_eat_history);
        tvDateHistory = findViewById(R.id.tv_date_history);
        tvClothHistory = findViewById(R.id.tv_cloth_history);

        // 加载昵称
        SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);
        etNick.setText(sp.getString("nickname", "用户"));

        // 保存昵称
        btnSave.setOnClickListener(v -> {
            sp.edit().putString("nickname", etNick.getText().toString().trim()).apply();
        });

        // 退出登录
        btnLogout.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finishAffinity();
        });

        // 加载所有收藏 + 历史记录
        loadAllData();
    }

    // 加载收藏 + 历史记录
    private void loadAllData() {
        Gson gson = new Gson();

        // 美食收藏
        SharedPreferences eatSp = getSharedPreferences("eat_fav", MODE_PRIVATE);
        String eatFavJson = eatSp.getString("fav", "[]");
        ArrayList<String> eatFav = gson.fromJson(eatFavJson, new TypeToken<ArrayList<String>>() {});
        tvEatFav.setText("美食收藏：" + eatFav);

        // 约会收藏
        SharedPreferences dateSp = getSharedPreferences("date_fav", MODE_PRIVATE);
        String dateFavJson = dateSp.getString("fav", "[]");
        ArrayList<String> dateFav = gson.fromJson(dateFavJson, new TypeToken<ArrayList<String>>() {});
        tvDateFav.setText("约会收藏：" + dateFav);

        // 美食历史
        SharedPreferences eatHisSp = getSharedPreferences("eat_history", MODE_PRIVATE);
        String eatHis = eatHisSp.getString("his", "[]");
        tvEatHistory.setText("美食随机历史：" + gson.fromJson(eatHis, new TypeToken<ArrayList<String>>() {}));

        // 约会历史
        SharedPreferences dateHisSp = getSharedPreferences("date_history", MODE_PRIVATE);
        String dateHis = dateHisSp.getString("his", "[]");
        tvDateHistory.setText("约会随机历史：" + gson.fromJson(dateHis, new TypeToken<ArrayList<String>>() {}));

        // 穿搭历史
        SharedPreferences clothHisSp = getSharedPreferences("cloth_history", MODE_PRIVATE);
        String clothHis = clothHisSp.getString("his", "[]");
        tvClothHistory.setText("穿搭搭配历史：" + gson.fromJson(clothHis, new TypeToken<ArrayList<String>>() {}));
    }
}