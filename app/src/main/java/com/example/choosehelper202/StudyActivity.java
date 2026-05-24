package com.example.choosehelper202;

public class StudyActivity extends BaseRandomActivity {
    @Override
    protected void initData() {
        tvTitle.setText("学习随机");
        list.add("看书");
        list.add("做题");
        list.add("复习");
        list.add("预习");
    }
}