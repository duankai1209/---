package com.example.choosehelper202;

public class GiftActivity extends BaseRandomActivity {
    @Override
    protected void initData() {
        tvTitle.setText("礼物随机");
        list.add("鲜花");
        list.add("巧克力");
        list.add("手表");
        list.add("香水");
        list.add("饰品");
    }
}