package com.example.choosehelper202;
public class DrinkActivity extends BaseRandomActivity {
    @Override protected void initData() {
        tvTitle.setText("饮料随机");
        list.add("奶茶");list.add("咖啡");list.add("果汁");list.add("茶饮");
    }
}