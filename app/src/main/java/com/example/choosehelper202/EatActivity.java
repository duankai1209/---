package com.example.choosehelper202;
public class EatActivity extends BaseRandomActivity {
    @Override protected void initData() {
        tvTitle.setText("三餐随机");
        list.add("包子");list.add("面条");list.add("米饭");list.add("饺子");list.add("粥");
    }
}