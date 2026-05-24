package com.example.choosehelper202;

public class SportActivity extends BaseRandomActivity {
    @Override
    protected void initData() {
        tvTitle.setText("运动随机");
        list.add("跑步");
        list.add("健身");
        list.add("篮球");
        list.add("游泳");
        list.add("跳绳");
    }
}