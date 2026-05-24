package com.example.choosehelper202;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import java.util.ArrayList;

public class DrinkActivity extends BaseRandomActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("饮品随机");
        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        lvList.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        list.add("奶茶");
        list.add("咖啡");
        list.add("果汁");
        list.add("可乐");
        list.add("矿泉水");
    }
}