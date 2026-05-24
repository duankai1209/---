package com.example.choosehelper202;

public class RandomClothesActivity extends BaseRandomActivity {
    @Override
    protected void initData() {
        tvTitle.setText("穿搭方案");
        list.add("休闲风");
        list.add("运动风");
        list.add("商务风");
        list.add("可爱风");
    }
}