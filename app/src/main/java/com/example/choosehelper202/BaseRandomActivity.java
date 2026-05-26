package com.example.choosehelper202;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Random;

public abstract class BaseRandomActivity extends AppCompatActivity {

    protected ListView lvList;
    protected EditText etInput;
    protected Button btnAdd, btnRandom, btnFav, btnFavRandom, btnClearFav, btnBack;
    protected ArrayList<String> dataList = new ArrayList<>();
    protected ArrayList<String> favList = new ArrayList<>();
    protected String pageTag = "";   // 子类赋值，如 "eat", "date"
    protected Gson gson = new Gson();
    protected SharedPreferences spFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_random);

        ImageView bg = findViewById(R.id.bg);
        BgUtil.setGlobalBg(this, bg);

        lvList = findViewById(R.id.lvList);
        etInput = findViewById(R.id.etInput);
        btnAdd = findViewById(R.id.btnAdd);
        btnRandom = findViewById(R.id.btnRandom);
        btnFav = findViewById(R.id.btnFav);
        btnFavRandom = findViewById(R.id.btnFavRandom);
        btnClearFav = findViewById(R.id.btnClearFav);
        btnBack = findViewById(R.id.btnBack);

        initData();  // 子类设置 pageTag 和初始 dataList

        // 使用 pageTag + "_fav" 作为收藏文件名
        spFav = getSharedPreferences(pageTag + "_fav", MODE_PRIVATE);
        loadFav();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        lvList.setAdapter(adapter);

        btnAdd.setOnClickListener(v -> {
            AnimUtil.clickAnim(v);
            String s = etInput.getText().toString().trim();
            if (!s.isEmpty()) {
                dataList.add(s);
                adapter.notifyDataSetChanged();
                etInput.setText("");
                Toast.makeText(this, "已添加: " + s, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
            }
        });

        btnRandom.setOnClickListener(v -> {
            AnimUtil.clickAnim(v);
            if (dataList.isEmpty()) {
                Toast.makeText(this, "列表为空，请先添加", Toast.LENGTH_SHORT).show();
                return;
            }
            String res = dataList.get(new Random().nextInt(dataList.size()));
            Toast.makeText(this, "🎲 随机结果： " + res, Toast.LENGTH_LONG).show();
            // 保存历史（使用 pageTag + "_history" 文件，key 为 "his"，格式为 JSON 数组）
            saveHistory(res);
        });

        btnFav.setOnClickListener(v -> {
            AnimUtil.clickAnim(v);
            if (dataList.isEmpty()) {
                Toast.makeText(this, "列表为空", Toast.LENGTH_SHORT).show();
                return;
            }
            String res = dataList.get(new Random().nextInt(dataList.size()));
            if (!favList.contains(res)) {
                favList.add(res);
                saveFav();
                Toast.makeText(this, "❤️ 收藏成功： " + res, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "已收藏过该选项", Toast.LENGTH_SHORT).show();
            }
        });

        btnFavRandom.setOnClickListener(v -> {
            AnimUtil.clickAnim(v);
            if (favList.isEmpty()) {
                Toast.makeText(this, "收藏夹为空，请先收藏", Toast.LENGTH_SHORT).show();
                return;
            }
            String res = favList.get(new Random().nextInt(favList.size()));
            Toast.makeText(this, "⭐ 收藏随机： " + res, Toast.LENGTH_LONG).show();
        });

        btnClearFav.setOnClickListener(v -> {
            AnimUtil.clickAnim(v);
            favList.clear();
            saveFav();
            Toast.makeText(this, "已清空收藏", Toast.LENGTH_SHORT).show();
        });

        btnBack.setOnClickListener(v -> {
            AnimUtil.clickAnim(v);
            finish();
        });
    }

    protected abstract void initData();

    private void loadFav() {
        String json = spFav.getString("fav", "[]");
        favList = gson.fromJson(json, new TypeToken<ArrayList<String>>(){}.getType());
        if (favList == null) favList = new ArrayList<>();
    }

    private void saveFav() {
        String json = gson.toJson(favList);
        spFav.edit().putString("fav", json).apply();
    }

    // 保存历史：使用 pageTag + "_history" 文件，key 为 "his"，存储 JSON 数组（最多保留20条）
    private void saveHistory(String result) {
        SharedPreferences sp = getSharedPreferences(pageTag + "_history", MODE_PRIVATE);
        String json = sp.getString("his", "[]");
        ArrayList<String> history = gson.fromJson(json, new TypeToken<ArrayList<String>>(){}.getType());
        if (history == null) history = new ArrayList<>();
        history.add(0, result);  // 最新在前
        if (history.size() > 20) history = new ArrayList<>(history.subList(0, 20));
        sp.edit().putString("his", gson.toJson(history)).apply();
    }
}