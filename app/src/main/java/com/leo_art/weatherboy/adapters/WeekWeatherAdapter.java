package com.leo_art.weatherboy.adapters;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.leo_art.weatherboy.R;
import com.leo_art.weatherboy.model.WeekForecast;
import com.leo_art.weatherboy.utils.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeekWeatherAdapter extends BaseAdapter {
    private List<WeekForecast> data;
    private LayoutInflater inflater;
    private SimpleDateFormat myFormat;

    public WeekWeatherAdapter(Activity activity, List<WeekForecast> data) {
        super();
        this.data = data;
        this.inflater = activity.getLayoutInflater();
        myFormat = new SimpleDateFormat("dd MMMM");
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.week_weather_item, parent, false);
            holder = new ViewHolder();
            holder.tvWeekDay = (TextView) convertView.findViewById(R.id.tv_week_day);
            holder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
            holder.ivWeekDayIcon = (ImageView) convertView.findViewById(R.id.iv_week_day_icon);
            holder.tvDayTemperature = (TextView) convertView.findViewById(R.id.tv_day_temperature);
            holder.tvNightTemperature = (TextView) convertView.findViewById(R.id.tv_night_temperature);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        WeekForecast forecast = data.get(position);

        //TODO add options for setting to set temp

        if (!TextUtils.isEmpty(forecast.getDt())) {
            //todo refactor
            Date date = new Date(Long.valueOf(forecast.getDt()));
            String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);
            holder.tvWeekDay.setText(dayOfWeek);

        }

        holder.tvDayTemperature.setText(String.format("%.1f", Converter.kelvinToCel(forecast.getTemp().getDay())) + " C");

        holder.tvNightTemperature.setText(String.format("%.1f", Converter.kelvinToCel(forecast.getTemp().getNight())) + " C");


        return convertView;
    }

    static class ViewHolder {
        TextView tvWeekDay;
        TextView tvDate;
        ImageView ivWeekDayIcon;
        TextView tvDayTemperature;
        TextView tvNightTemperature;
    }
}
