package com.example.choosehelper202;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EatActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eat);

        ImageView bg = findViewById(R.id.bg);
        BgUtil.setGlobalBg(this, bg);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        List<String> tabTitles = Arrays.asList("早餐", "午餐", "晚餐", "奶茶", "咖啡", "果汁", "茶饮");
        List<RandomListFragment> fragments = new ArrayList<>();

        fragments.add(RandomListFragment.newInstance("breakfast", new ArrayList<>(Arrays.asList("牛奶+麦片", "豆浆+油条", "粥+包子", "三明治", "煎饼果子"))));
        fragments.add(RandomListFragment.newInstance("lunch", new ArrayList<>(Arrays.asList("米饭+炒菜", "面条", "盖浇饭", "沙拉", "披萨"))));
        fragments.add(RandomListFragment.newInstance("dinner", new ArrayList<>(Arrays.asList("火锅", "烧烤", "日料", "家常菜", "轻食"))));
        fragments.add(RandomListFragment.newInstance("milk_tea", new ArrayList<>(Arrays.asList("珍珠奶茶", "椰奶茶", "芋泥波波", "四季春茶"))));
        fragments.add(RandomListFragment.newInstance("coffee", new ArrayList<>(Arrays.asList("美式", "拿铁", "卡布奇诺", "摩卡"))));
        fragments.add(RandomListFragment.newInstance("juice", new ArrayList<>(Arrays.asList("橙汁", "苹果汁", "西瓜汁", "芒果汁"))));
        fragments.add(RandomListFragment.newInstance("tea", new ArrayList<>(Arrays.asList("红茶", "绿茶", "乌龙茶", "花茶"))));

        EatPagerAdapter adapter = new EatPagerAdapter(this, fragments, tabTitles);
        viewPager.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(tabTitles.get(position))).attach();
    }
}