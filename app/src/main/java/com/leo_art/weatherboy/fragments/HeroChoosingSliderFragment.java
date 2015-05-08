package com.leo_art.weatherboy.fragments;

import android.app.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leo_art.weatherboy.R;
import com.leo_art.weatherboy.model.Hero;


public class HeroChoosingSliderFragment extends Fragment {

    private static final String ARG_HERO = "hero";

    private Hero hero;

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
        TextView heroName = (TextView)v.findViewById(R.id.tv_hero_name);
        heroName.setText(hero.getName());
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

}
