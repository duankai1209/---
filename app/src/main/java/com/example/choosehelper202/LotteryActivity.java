package com.example.choosehelper202;

public class LotteryActivity extends BaseRandomActivity {
    @Override
    protected void initData() {
        tvTitle.setText("抽签运势");
        list.add("大吉");
        list.add("吉");
        list.add("中平");
        list.add("平");
        list.add("小凶");
    }
}