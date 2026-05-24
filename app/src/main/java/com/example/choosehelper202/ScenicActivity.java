package com.example.choosehelper202;

public class ScenicActivity extends BaseRandomActivity {
    @Override
    protected void initData() {
        tvTitle.setText("景点随机");
        list.add("公园");
        list.add("商场");
        list.add("景区");
        list.add("海边");
        list.add("游乐园");
    }
}