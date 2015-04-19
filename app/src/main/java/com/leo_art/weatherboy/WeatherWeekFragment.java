package com.leo_art.weatherboy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.leo_art.weatherboy.adapters.WeekWeatherAdapter;

import java.util.ArrayList;

public class WeatherWeekFragment extends Fragment {
    private ListView weekWeatherList;

    public static WeatherWeekFragment newInstance() {
        WeatherWeekFragment fragment = new WeatherWeekFragment();
        return fragment;
    }

    public WeatherWeekFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_weather_week, container, false);
        weekWeatherList = (ListView)v.findViewById(R.id.weekWeatherListView);
        weekWeatherList.addHeaderView(inflater.inflate(R.layout.week_weather_header, null, false));
        ArrayList<String> l = new ArrayList<>();
        l.add("dwd");
        l.add("dwd1");
        l.add("dwd2");
        l.add("dwd3");
        l.add("dwd4");
        l.add("dwd5");
        l.add("dwd6");
        weekWeatherList.setAdapter(new WeekWeatherAdapter(l, getActivity()));
        return v;
    }
}
