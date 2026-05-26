package com.example.choosehelper202;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import java.util.Arrays;
import java.util.List;
import androidx.fragment.app.Fragment;
import java.util.Arrays;
import java.util.List;

public class DateActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private Button btnRandomCombine;
    private PlaceCategoryFragment placeFragment;
    private ActivityCategoryFragment activityFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        ImageView bg = findViewById(R.id.bg);
        BgUtil.setGlobalBg(this, bg);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        btnRandomCombine = findViewById(R.id.btn_random_combine);

        // 创建两个主 Fragment
        placeFragment = new PlaceCategoryFragment();
        activityFragment = new ActivityCategoryFragment();

        List<Fragment> fragments = Arrays.asList(placeFragment, activityFragment);
        List<String> titles = Arrays.asList("游玩地点", "游玩项目");

        DatePagerAdapter adapter = new DatePagerAdapter(this, fragments, titles);
        viewPager.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(titles.get(position))
        ).attach();

        btnRandomCombine.setOnClickListener(v -> {
            AnimUtil.clickAnim(v);
            // 获取当前选中的地点分类下的当前子分类的随机一项
            CategoryListFragment currentPlaceFrag = placeFragment.getCurrentFragment();
            CategoryListFragment currentActivityFrag = activityFragment.getCurrentFragment();
            if (currentPlaceFrag == null || currentActivityFrag == null) {
                Toast.makeText(this, "加载失败", Toast.LENGTH_SHORT).show();
                return;
            }
            String place = currentPlaceFrag.getRandomItem();
            String activity = currentActivityFrag.getRandomItem();
            if (place == null || activity == null) {
                Toast.makeText(this, "请确保列表不为空", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, "🎉 推荐：去 " + place + " 做 " + activity, Toast.LENGTH_LONG).show();
        });
    }
}