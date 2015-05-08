package com.leo_art.weatherboy.fragments;

import android.app.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leo_art.weatherboy.R;


public class HeroChoosingSliderFragment extends Fragment {

    private static final String ARG_INDEX = "index";


    // TODO: Rename and change types of parameters
    private String index;


    public static HeroChoosingSliderFragment newInstance(String index) {
        HeroChoosingSliderFragment fragment = new HeroChoosingSliderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_INDEX, index);
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
            index = getArguments().getString(ARG_INDEX);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hero_choosing_slider, container, false);
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
