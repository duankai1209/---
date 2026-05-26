package com.example.choosehelper202;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserActivity extends AppCompatActivity {
    EditText etNick;
    Button btnSave, btnLogout;
    LinearLayout container;  // 动态添加收藏和历史条目
    ImageView bg;

    // 定义所有页面标签及对应的中文名称和跳转类
    private static class PageInfo {
        String tag;
        String title;
        Class<?> activityClass;
        PageInfo(String tag, String title, Class<?> activityClass) {
            this.tag = tag;
            this.title = title;
            this.activityClass = activityClass;
        }
    }

    private final List<PageInfo> pages = Arrays.asList(
            new PageInfo("eat", "美食", EatActivity.class),
            new PageInfo("date", "约会", DateActivity.class),
            new PageInfo("cloth", "穿搭", ClothActivity.class),
            new PageInfo("gift", "礼物", GiftActivity.class),
            new PageInfo("lottery", "抽奖", LotteryActivity.class),
            new PageInfo("motto", "格言", MottoActivity.class),
            new PageInfo("scenic", "景点", ScenicActivity.class),
            new PageInfo("schedule", "日程", ScheduleActivity.class),
            new PageInfo("sport", "运动", SportActivity.class),
            new PageInfo("study", "学习", StudyActivity.class)
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        bg = findViewById(R.id.bg);
        BgUtil.setGlobalBg(this, bg);

        etNick = findViewById(R.id.et_nick);
        btnSave = findViewById(R.id.btn_save);
        btnLogout = findViewById(R.id.btn_logout);
        container = findViewById(R.id.container);  // 在 activity_user.xml 中需要添加一个 LinearLayout 作为容器

        SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);
        etNick.setText(sp.getString("nickname", "用户"));

        btnSave.setOnClickListener(v -> {
            AnimUtil.clickAnim(v);
            sp.edit().putString("nickname", etNick.getText().toString().trim()).apply();
            Toast.makeText(this, "昵称已保存", Toast.LENGTH_SHORT).show();
        });

        btnLogout.setOnClickListener(v -> {
            AnimUtil.clickAnim(v);
            // 清除登录状态
            getSharedPreferences("user", MODE_PRIVATE).edit().remove("currentUser").apply();
            startActivity(new Intent(this, LoginActivity.class));
            finishAffinity();
        });

        loadAllData();
    }

    private void loadAllData() {
        Gson gson = new Gson();
        for (PageInfo page : pages) {
            // 加载收藏
            SharedPreferences favSp = getSharedPreferences(page.tag + "_fav", MODE_PRIVATE);
            String favJson = favSp.getString("fav", "[]");
            ArrayList<String> favList = gson.fromJson(favJson, new TypeToken<ArrayList<String>>(){}.getType());
            String favText = (favList == null || favList.isEmpty()) ? "暂无收藏" : favList.toString();

            // 加载历史
            SharedPreferences hisSp = getSharedPreferences(page.tag + "_history", MODE_PRIVATE);
            String hisJson = hisSp.getString("his", "[]");
            ArrayList<String> hisList = gson.fromJson(hisJson, new TypeToken<ArrayList<String>>(){}.getType());
            String hisText = (hisList == null || hisList.isEmpty()) ? "暂无历史" : hisList.toString();

            // 创建显示收藏的TextView
            TextView tvFav = new TextView(this);
            tvFav.setText("【" + page.title + "收藏】\n" + favText);
            tvFav.setPadding(16, 16, 16, 16);
            tvFav.setBackgroundResource(R.drawable.et_bg);  // 复用输入框背景样式
            tvFav.setTextSize(14f);
            tvFav.setClickable(true);
            tvFav.setOnClickListener(v -> {
                AnimUtil.clickAnim(v);
                startActivity(new Intent(this, page.activityClass));
            });

            // 创建显示历史的TextView
            TextView tvHis = new TextView(this);
            tvHis.setText("【" + page.title + "历史】\n" + hisText);
            tvHis.setPadding(16, 16, 16, 16);
            tvHis.setBackgroundResource(R.drawable.et_bg);
            tvHis.setTextSize(14f);
            tvHis.setClickable(true);
            tvHis.setOnClickListener(v -> {
                AnimUtil.clickAnim(v);
                startActivity(new Intent(this, page.activityClass));
            });

            container.addView(tvFav);
            container.addView(tvHis);
        }
    }
}