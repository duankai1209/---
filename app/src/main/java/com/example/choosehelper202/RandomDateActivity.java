package com.example.choosehelper202;

public class RandomDateActivity extends BaseRandomActivity {
    @Override
    protected void initData() {
        tvTitle.setText("约会方案");
        list.add("室内约会");
        list.add("室外约会");
        list.add("美食约会");
        list.add("娱乐约会");
    }
}