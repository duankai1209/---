package com.example.choosehelper202;

public class StudyActivity extends BaseRandomActivity {
    @Override
    protected void initData() {
        pageTag = "study";
        dataList.add("背英语单词");
        dataList.add("做数学题");
        dataList.add("复习专业课");
        dataList.add("阅读论文");
        dataList.add("写代码");
        dataList.add("学习设计模式");
        dataList.add("观看教学视频");
        dataList.add("做笔记");
        dataList.add("刷LeetCode");
        dataList.add("练习口语");
    }
}