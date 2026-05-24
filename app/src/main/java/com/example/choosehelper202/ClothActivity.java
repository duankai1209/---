package com.example.choosehelper202;
public class ClothActivity extends BaseRandomActivity {
    @Override protected void initData() {
        tvTitle.setText("穿搭随机");
        list.add("T恤");list.add("衬衫");list.add("卫衣");list.add("裤子");list.add("鞋子");
    }
}