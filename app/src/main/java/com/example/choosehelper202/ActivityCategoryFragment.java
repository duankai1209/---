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

public class ActivityCategoryFragment extends Fragment {
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

        ArrayList<String> relaxData = new ArrayList<>(Arrays.asList("看电影", "听音乐", "喝咖啡", "散步", "看书", "泡温泉"));
        ArrayList<String> entertainmentData = new ArrayList<>(Arrays.asList("打游戏", "桌游", "密室逃脱", "剧本杀", "K歌", "电玩城"));
        ArrayList<String> foodData = new ArrayList<>(Arrays.asList("火锅", "烧烤", "日料", "自助餐", "甜品", "小吃街"));

        CategoryListFragment relaxFragment = CategoryListFragment.newInstance("relax", relaxData);
        CategoryListFragment entertainmentFragment = CategoryListFragment.newInstance("entertainment", entertainmentData);
        CategoryListFragment foodFragment = CategoryListFragment.newInstance("food", foodData);

        List<Fragment> fragments = Arrays.asList(relaxFragment, entertainmentFragment, foodFragment);
        List<String> titles = Arrays.asList("休闲", "娱乐", "美食");

        CategoryContainerAdapter adapter = new CategoryContainerAdapter(this, fragments);
        viewPager.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(titles.get(position))
        ).attach();
    }

    public CategoryListFragment getCurrentFragment() {
        int position = viewPager.getCurrentItem();
        return (CategoryListFragment) getChildFragmentManager().findFragmentByTag("f" + position);
    }
}