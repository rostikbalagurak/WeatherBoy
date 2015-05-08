package com.leo_art.weatherboy.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.andraskindler.parallaxviewpager.ParallaxViewPager;
import com.leo_art.weatherboy.R;
import com.leo_art.weatherboy.adapters.FragmentPagerAdapter;
import com.leo_art.weatherboy.fragments.WeatherFragment;
import com.leo_art.weatherboy.fragments.WeatherWeekFragment;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    protected ParallaxViewPager parallaxViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setBackgroundDrawable(null);

        parallaxViewPager = (ParallaxViewPager) findViewById(R.id.fragmentPager);

        ArrayList<Fragment> fragmentData = new ArrayList<>();
        fragmentData.add(WeatherFragment.newInstance());
        fragmentData.add(WeatherFragment.newInstance());
        fragmentData.add(WeatherWeekFragment.newInstance());

        parallaxViewPager.setOverlapPercentage(0.55f).setAdapter(new FragmentPagerAdapter(fragmentData, getSupportFragmentManager()));

    }
}
