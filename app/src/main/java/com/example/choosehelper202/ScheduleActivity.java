package com.example.choosehelper202;

public class ScheduleActivity extends BaseRandomActivity {
    @Override
    protected void initData() {
        tvTitle.setText("日程随机");
        list.add("学习");
        list.add("工作");
        list.add("休息");
        list.add("娱乐");
    }
}