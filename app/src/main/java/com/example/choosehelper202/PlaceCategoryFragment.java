package com.example.choosehelper202;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlaceCategoryFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category_container, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);

        // 准备室内数据
        ArrayList<String> indoorData = new ArrayList<>(Arrays.asList(
                "咖啡馆", "书店", "博物馆", "电影院", "商场", "健身房", "KTV", "密室逃脱"));
        // 准备室外数据
        ArrayList<String> outdoorData = new ArrayList<>(Arrays.asList(
                "公园", "海滩", "爬山", "骑行道", "游乐场", "植物园", "动物园", "野餐营地"));

        CategoryListFragment indoorFragment = CategoryListFragment.newInstance("indoor", indoorData);
        CategoryListFragment outdoorFragment = CategoryListFragment.newInstance("outdoor", outdoorData);

        List<Fragment> fragments = Arrays.asList(indoorFragment, outdoorFragment);
        List<String> titles = Arrays.asList("室内", "室外");

        // 适配器
        CategoryContainerAdapter adapter = new CategoryContainerAdapter(this, fragments);
        viewPager.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(titles.get(position))
        ).attach();
    }

    // 获取当前选中的分类（室内或室外）的 Fragment，以便获取随机地点
    public CategoryListFragment getCurrentFragment() {
        int position = viewPager.getCurrentItem();
        return (CategoryListFragment) getChildFragmentManager().findFragmentByTag("f" + position);
    }
}