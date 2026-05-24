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

public class DateActivity extends AppCompatActivity {

    RadioGroup rg_date_tabs;
    Button btn_add, btn_random, btn_date_add_fav, btn_date_random_fav, btn_date_random_all, btn_back;
    android.widget.ListView lv_list;
    android.widget.TextView tv_title;
    android.widget.EditText et_input;
    android.widget.ArrayAdapter<String> adapter;
    ArrayList<String> list = new ArrayList<>();

    SharedPreferences sp;
    ArrayList<String> favList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        tv_title = findViewById(R.id.tv_title);
        lv_list = findViewById(R.id.lv_list);
        et_input = findViewById(R.id.et_input);
        btn_add = findViewById(R.id.btn_add);
        btn_random = findViewById(R.id.btn_random);
        btn_date_add_fav = findViewById(R.id.btn_date_add_fav);
        btn_date_random_fav = findViewById(R.id.btn_date_random_fav);
        btn_date_random_all = findViewById(R.id.btn_date_random_all);
        btn_back = findViewById(R.id.btn_back);
        rg_date_tabs = findViewById(R.id.rg_date_tabs);

        adapter = new android.widget.ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        lv_list.setAdapter(adapter);

        sp = getSharedPreferences("date_fav", MODE_PRIVATE);
        loadFav();

        initIndoorList();

        rg_date_tabs.setOnCheckedChangeListener((group, id) -> {
            if (id == R.id.rb_indoor) initIndoorList();
            else if (id == R.id.rb_outdoor) initOutdoorList();
        });

        btn_add.setOnClickListener(v -> {
            String s = et_input.getText().toString().trim();
            if (!s.isEmpty()) {
                list.add(s);
                adapter.notifyDataSetChanged();
                et_input.setText("");
            }
        });

        btn_random.setOnClickListener(v -> {
            if (!list.isEmpty()) {
                int i = (int) (Math.random() * list.size());
                tv_title.setText("结果：" + list.get(i));
            }
        });

        btn_date_add_fav.setOnClickListener(v -> {
            String res = tv_title.getText().toString().replace("结果：", "").trim();
            if (res.isEmpty()) {
                Toast.makeText(this, "请先随机", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!favList.contains(res)) {
                favList.add(res);
                saveFav();
                Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
            }
        });

        btn_date_random_fav.setOnClickListener(v -> {
            if (favList.isEmpty()) {
                Toast.makeText(this, "收藏为空", Toast.LENGTH_SHORT).show();
                return;
            }
            int i = (int) (Math.random() * favList.size());
            tv_title.setText("收藏随机：" + favList.get(i));
        });

        btn_date_random_all.setOnClickListener(v -> {
            ArrayList<String> all = new ArrayList<>();
            all.add("看电影+奶茶");
            all.add("公园散步+野餐");
            all.add("桌游+晚餐");
            all.add("海边+烧烤");
            int i = (int) (Math.random() * all.size());
            tv_title.setText("纯随机：" + all.get(i));
        });

        btn_back.setOnClickListener(v -> finish());
    }

    private void initIndoorList() {
        list.clear();
        list.add("看电影"); list.add("桌游"); list.add("逛商场"); list.add("咖啡馆");
        adapter.notifyDataSetChanged();
    }

    private void initOutdoorList() {
        list.clear();
        list.add("公园"); list.add("爬山"); list.add("海边"); list.add("游乐园");
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