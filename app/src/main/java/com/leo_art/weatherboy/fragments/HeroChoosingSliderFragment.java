package com.leo_art.weatherboy.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.leo_art.weatherboy.R;
import com.leo_art.weatherboy.WeatherApplication;
import com.leo_art.weatherboy.model.Hero;
import com.leo_art.weatherboy.model.Settings;

import java.util.Set;

import io.realm.Realm;


public class HeroChoosingSliderFragment extends Fragment {

    private static final String ARG_HERO = "hero";

    private DataUpdateListener updateListener;

    private Hero hero;
    private ImageView ivHero;
    private TextView tvName;
    private TextView tvPrice;
    private TextView tvIsCurrent;
    private Button buttonSelect;

    public static HeroChoosingSliderFragment newInstance(Hero hero) {
        HeroChoosingSliderFragment fragment = new HeroChoosingSliderFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_HERO, hero);
        fragment.setArguments(args);
        return fragment;
    }

    public HeroChoosingSliderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            hero = (Hero)getArguments().getSerializable(ARG_HERO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_hero_choosing_slider, container, false);

        Settings settings = WeatherApplication.getInstance().getSettings(getActivity());

        tvName = (TextView)v.findViewById(R.id.tv_hero_name);
        ivHero = (ImageView)v.findViewById(R.id.iv_hero_image);
        tvPrice = (TextView)v.findViewById(R.id.tv_cost);
        tvIsCurrent = (TextView)v.findViewById(R.id.tv_is_current);
        buttonSelect = (Button)v.findViewById(R.id.select_hero_button);

        buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeCurrent();
            }
        });

        tvName.setText(hero.getName());
        ivHero.setImageResource(hero.getImageTypeNormal());
        tvPrice.setText(hero.isPaid() ? (hero.getPrice() + "") : "Free");
        tvIsCurrent.setText(hero.getId() == settings.getCurrentHeroId() ? "Current" : "");

        return v;
    }

    private void makeCurrent() {
        Realm realm = Realm.getInstance(getActivity());
        realm.beginTransaction();

        Settings settings = WeatherApplication.getInstance().getSettings(getActivity());
        settings.setCurrentHeroId(hero.getId());

        realm.commitTransaction();
        if(updateListener != null){
            updateListener.onUpdate();
        }
    }

    public void updateData() {
        Settings settings = WeatherApplication.getInstance().getSettings(getActivity());
        tvIsCurrent.setText(hero.getId() == settings.getCurrentHeroId() ? "Current" : "");
    }

    public void setOnUpdateListener(DataUpdateListener listener) {
        this.updateListener = listener;
    }

    public interface DataUpdateListener {
        void onUpdate();
    }
}
