package com.leo_art.weatherboy.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ProgressBar;

import com.andraskindler.parallaxviewpager.ParallaxViewPager;
import com.leo_art.weatherboy.R;
import com.leo_art.weatherboy.WeatherApplication;
import com.leo_art.weatherboy.adapters.FragmentPagerAdapter;
import com.leo_art.weatherboy.fragments.WeatherFragment;
import com.leo_art.weatherboy.fragments.WeatherWeekFragment;
import com.leo_art.weatherboy.model.Hero;
import com.leo_art.weatherboy.model.Settings;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;

public class MainActivity extends BaseActivity {
    protected Settings settings;
    protected Hero hero;
    protected ParallaxViewPager parallaxViewPager;
    protected ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.settings = WeatherApplication.getInstance().getSettings(this);
        loadHero();
        setContentView(R.layout.activity_main);
        getWindow().setBackgroundDrawable(null);

        progressBar = (ProgressBar) findViewById(R.id.preloader);
        parallaxViewPager = (ParallaxViewPager) findViewById(R.id.fragmentPager);
        ArrayList<Fragment> fragmentData = new ArrayList<>();
        fragmentData.add(WeatherFragment.newInstance(hero));
        fragmentData.add(WeatherFragment.newInstance(hero));
        fragmentData.add(WeatherWeekFragment.newInstance());
        parallaxViewPager.setOverlapPercentage(0.55f).setAdapter(new FragmentPagerAdapter(fragmentData, getSupportFragmentManager()));

        progressBar.setVisibility(View.GONE);
    }

    private void loadHero() {
        Realm realm = Realm.getInstance(this);
        RealmQuery<Hero> query = realm.where(Hero.class);
        query.equalTo("id", settings.getCurrentHeroId());

        hero = query.findFirst();
    }
}
