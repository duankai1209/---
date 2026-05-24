package com.example.choosehelper202;

public class RandomEatActivity extends BaseRandomActivity {
    @Override
    protected void initData() {
        tvTitle.setText("美食方案");
        list.add("早餐");
        list.add("午餐");
        list.add("晚餐");
        list.add("夜宵");
    }
}