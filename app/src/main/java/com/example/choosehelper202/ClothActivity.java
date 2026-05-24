package com.example.choosehelper202;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;

public class ClothActivity extends AppCompatActivity {
    TextView tvWeatherSuggest, tvResult;
    Button btnRandomSet, btnSaveSet, btnRandomSavedSet;
    private SharedPreferences sp;
    private ArrayList<String> savedSets = new ArrayList<>();
    private String[] tops = {"T恤", "衬衫", "卫衣", "毛衣"};
    private String[] bottoms = {"牛仔裤", "休闲裤", "裙子", "短裤"};
    private String[] shoes = {"运动鞋", "皮鞋", "帆布鞋", "凉鞋"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloth);
        tvWeatherSuggest = findViewById(R.id.tv_weather_suggest);
        tvResult = findViewById(R.id.tv_result);
        btnRandomSet = findViewById(R.id.btn_random_set);
        btnSaveSet = findViewById(R.id.btn_save_set);
        btnRandomSavedSet = findViewById(R.id.btn_random_saved_set);
        sp = getSharedPreferences("cloth_sets", MODE_PRIVATE);
        loadSavedSets();

        // 天气联动（从天气页获取建议，这里简化为示例，实际可通过Intent传递）
        tvWeatherSuggest.setText("根据天气推荐：建议穿薄款衬衫 + 牛仔裤");

        // 随机套装
        btnRandomSet.setOnClickListener(v -> {
            String top = tops[(int) (Math.random() * tops.length)];
            String bottom = bottoms[(int) (Math.random() * bottoms.length)];
            String shoe = shoes[(int) (Math.random() * shoes.length)];
            String set = top + " + " + bottom + " + " + shoe;
            tvResult.setText("随机套装：" + set);
        });

        // 保存套装
        btnSaveSet.setOnClickListener(v -> {
            String set = tvResult.getText().toString().replace("随机套装：", "");
            if (!savedSets.contains(set)) {
                savedSets.add(set);
                saveSavedSets();
                Toast.makeText(this, "套装已保存", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "套装已存在", Toast.LENGTH_SHORT).show();
            }
        });

        // 随机已保存套装
        btnRandomSavedSet.setOnClickListener(v -> {
            if (!savedSets.isEmpty()) {
                int index = (int) (Math.random() * savedSets.size());
                tvResult.setText("已保存套装：" + savedSets.get(index));
            } else {
                Toast.makeText(this, "没有保存的套装", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadSavedSets() {
        String json = sp.getString("saved_sets", "");
        if (!json.isEmpty()) {
            savedSets = new Gson().fromJson(json, new TypeToken<ArrayList<String>>(){}.getType());
        }
    }

    private void saveSavedSets() {
        sp.edit().putString("saved_sets", new Gson().toJson(savedSets)).apply();
    }
}