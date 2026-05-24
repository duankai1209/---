package com.example.choosehelper202;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import java.util.ArrayList;

public class LotteryActivity extends BaseRandomActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 用基类里定义的 tvTitle 来设置标题，而不是直接用 title
        tvTitle.setText("抽签运势");
        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        lvList.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        list.add("大吉");
        list.add("吉");
        list.add("中平");
        list.add("平");
        list.add("小凶");
    }
}