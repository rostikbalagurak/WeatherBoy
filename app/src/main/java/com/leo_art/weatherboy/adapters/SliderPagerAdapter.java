package com.leo_art.weatherboy.adapters;

import java.util.List;

import com.leo_art.weatherboy.fragments.HeroChoosingSliderFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class SliderPagerAdapter extends FragmentStatePagerAdapter{

    private List<HeroChoosingSliderFragment> fragments;
    
    public SliderPagerAdapter(FragmentManager fm, List<HeroChoosingSliderFragment> heroesFragments) {
        super(fm);
        this.fragments = heroesFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

}
