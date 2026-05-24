package com.example.choosehelper202;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import java.util.ArrayList;

public class ClothActivity extends BaseRandomActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("穿搭随机");
        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        lvList.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        list.add("T恤");
        list.add("衬衫");
        list.add("卫衣");
        list.add("外套");
    }
}