package com.example.choosehelper202;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import java.util.ArrayList;

public class GiftActivity extends BaseRandomActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("礼物随机");
        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        lvList.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        list.add("鲜花");
        list.add("巧克力");
        list.add("手表");
        list.add("香水");
    }
}