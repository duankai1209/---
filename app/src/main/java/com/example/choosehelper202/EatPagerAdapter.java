package com.example.choosehelper202;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import java.util.List;

public class EatPagerAdapter extends FragmentStateAdapter {
    private List<RandomListFragment> fragments;
    private List<String> titles;

    public EatPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<RandomListFragment> fragments, List<String> titles) {
        super(fragmentActivity);
        this.fragments = fragments;
        this.titles = titles;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}