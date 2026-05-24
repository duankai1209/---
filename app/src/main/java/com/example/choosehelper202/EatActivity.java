package com.example.choosehelper202;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;

public class EatActivity extends AppCompatActivity {

    RadioGroup rgTabs;
    Button btnAdd, btnRandom, btnAddFav, btnRandomFav, btnBack;
    android.widget.ListView lvList;
    android.widget.TextView tvTitle;
    android.widget.EditText etInput;
    android.widget.ArrayAdapter<String> adapter;
    ArrayList<String> list = new ArrayList<>();

    SharedPreferences sp;
    ArrayList<String> favList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eat);

        tvTitle = findViewById(R.id.tv_title);
        lvList = findViewById(R.id.lv_list);
        etInput = findViewById(R.id.et_input);
        btnAdd = findViewById(R.id.btn_add);
        btnRandom = findViewById(R.id.btn_random);
        btnAddFav = findViewById(R.id.btn_add_fav);
        btnRandomFav = findViewById(R.id.btn_random_fav);
        btnBack = findViewById(R.id.btn_back);
        rgTabs = findViewById(R.id.rg_tabs);

        adapter = new android.widget.ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        lvList.setAdapter(adapter);

        sp = getSharedPreferences("eat_fav", MODE_PRIVATE);
        loadFav();

        initBreakfast();

        rgTabs.setOnCheckedChangeListener((group, id) -> {
            if (id == R.id.rb_breakfast) initBreakfast();
            else if (id == R.id.rb_lunch) initLunch();
            else if (id == R.id.rb_dinner) initDinner();
            else if (id == R.id.rb_drink) initDrink();
        });

        btnAdd.setOnClickListener(v -> {
            String s = etInput.getText().toString().trim();
            if (!s.isEmpty()) {
                list.add(s);
                adapter.notifyDataSetChanged();
                etInput.setText("");
            }
        });

        btnRandom.setOnClickListener(v -> {
            if (!list.isEmpty()) {
                int i = (int) (Math.random() * list.size());
                tvTitle.setText("结果：" + list.get(i));
            }
        });

        btnAddFav.setOnClickListener(v -> {
            String res = tvTitle.getText().toString().replace("结果：", "").trim();
            if (res.isEmpty()) {
                Toast.makeText(this, "请先随机食物", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!favList.contains(res)) {
                favList.add(res);
                saveFav();
                Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
            }
        });

        btnRandomFav.setOnClickListener(v -> {
            if (favList.isEmpty()) {
                Toast.makeText(this, "收藏为空", Toast.LENGTH_SHORT).show();
                return;
            }
            int i = (int) (Math.random() * favList.size());
            tvTitle.setText("收藏随机：" + favList.get(i));
        });

        btnBack.setOnClickListener(v -> finish());
    }

    private void initBreakfast() {
        list.clear();
        list.add("包子"); list.add("豆浆"); list.add("油条"); list.add("粥");
        adapter.notifyDataSetChanged();
    }

    private void initLunch() {
        list.clear();
        list.add("米饭"); list.add("面条"); list.add("盖浇饭"); list.add("快餐");
        adapter.notifyDataSetChanged();
    }

    private void initDinner() {
        list.clear();
        list.add("火锅"); list.add("烧烤"); list.add("炒菜"); list.add("轻食");
        adapter.notifyDataSetChanged();
    }

    private void initDrink() {
        list.clear();
        list.add("奶茶"); list.add("咖啡"); list.add("果汁"); list.add("茶饮");
        adapter.notifyDataSetChanged();
    }

    private void loadFav() {
        String json = sp.getString("fav", "[]");
        favList = new Gson().fromJson(json, new TypeToken<ArrayList<String>>() {}.getType());
    }

    private void saveFav() {
        sp.edit().putString("fav", new Gson().toJson(favList)).apply();
    }
}