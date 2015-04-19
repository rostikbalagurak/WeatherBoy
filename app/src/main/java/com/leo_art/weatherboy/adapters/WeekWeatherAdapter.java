package com.leo_art.weatherboy.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.leo_art.weatherboy.R;

import java.util.ArrayList;

public class WeekWeatherAdapter extends BaseAdapter {
    private ArrayList<String> data;
    private Activity activity;

    public WeekWeatherAdapter(ArrayList<String> data, Activity activity){
        super();
        this.data = data;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = activity.getLayoutInflater().inflate(R.layout.week_weather_item, parent, false);
        }
        return convertView;
    }
}
