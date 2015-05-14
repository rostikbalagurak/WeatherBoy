package com.leo_art.weatherboy.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.leo_art.weatherboy.R;
import com.leo_art.weatherboy.adapters.SliderPagerAdapter;
import com.leo_art.weatherboy.dialogs.MetricChooserDialogFragment;
import com.leo_art.weatherboy.fragments.HeroChoosingSliderFragment;
import com.leo_art.weatherboy.model.Hero;
import com.leo_art.weatherboy.model.Settings;
import com.leo_art.weatherboy.utils.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class SettingsActivity extends AppCompatActivity implements MetricChooserDialogFragment.OnMetricDialogChooseListener{

    protected ViewPager vpHero;
    protected List<HeroChoosingSliderFragment> heroesFragments;
    private String[] metricsStrings;

    protected TextView tvMetric;
    protected TextView tvMetricValue;

    protected Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        metricsStrings = getResources().getStringArray(R.array.metrics_array);

        loadSettings();
        initUI();

        initData();
    }

    private void loadSettings() {
        Realm realm = Realm.getInstance(this);
        this.settings = realm.allObjects(Settings.class).first();
    }

    private void initUI(){
        vpHero = (ViewPager) findViewById(R.id.vp_hero);
        vpHero.setPageTransformer(true, new ZoomOutPageTransformer());

        initPagerAdapter();

        tvMetric = (TextView) findViewById(R.id.tv_metric);
        tvMetric.setOnClickListener(metricClickListener);

        tvMetricValue = (TextView) findViewById(R.id.tv_metric_value);
        tvMetricValue.setOnClickListener(metricClickListener);
    }

    private void initPagerAdapter(){
        Realm realm = Realm.getInstance(this);
        RealmResults<Hero> heroes = realm.allObjects(Hero.class);

        heroesFragments = new ArrayList<>();
        HeroChoosingSliderFragment fragment;
        for(Hero hero : heroes ){
            fragment = HeroChoosingSliderFragment.newInstance(hero);
            fragment.setOnUpdateListener(new HeroChoosingSliderFragment.DataUpdateListener() {
                @Override
                public void onUpdate() {
                    for(HeroChoosingSliderFragment fragment : heroesFragments){
                        fragment.updateData();
                    }
                }
            });
            heroesFragments.add(fragment);
        }
        vpHero.setAdapter(new SliderPagerAdapter(getSupportFragmentManager(), heroesFragments));


    }

    private void initData(){
        if(!TextUtils.isEmpty(settings.getMetric())){
            tvMetricValue.setText(settings.getMetric());
        }
    }

    View.OnClickListener metricClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MetricChooserDialogFragment dialogFragment = new MetricChooserDialogFragment();
            dialogFragment.show(getSupportFragmentManager(),  "");
        }
    };

    @Override
    public void onMetricDialogItemChosen(int which) {
        if(which>=0 && which<metricsStrings.length) {
            tvMetricValue.setText(metricsStrings[which]);
        }
    }
}
