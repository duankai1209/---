package com.example.choosehelper202;

public class LotteryActivity extends BaseRandomActivity {
    @Override
    protected void initData() {
        pageTag = "lottery";
        dataList.add("一等奖：三亚双飞游");
        dataList.add("二等奖：高端手机");
        dataList.add("三等奖：智能手表");
        dataList.add("幸运奖：定制U盘");
        dataList.add("参与奖：优惠券");
        dataList.add("谢谢参与");
        dataList.add("现金红包10元");
        dataList.add("现金红包50元");
        dataList.add("电影票两张");
        dataList.add("健身房年卡");
    }
}