package com.example.choosehelper202;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import java.util.ArrayList;

public class MottoActivity extends BaseRandomActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 用基类里定义的 tvTitle 来设置标题，而不是直接用不存在的 title 变量
        tvTitle.setText("名言随机");
        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        lvList.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        list.add("坚持就是胜利");
        list.add("努力终有回报");
        list.add("今天也要加油");
    }
}