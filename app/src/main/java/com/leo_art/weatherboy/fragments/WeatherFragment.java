package com.leo_art.weatherboy.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.leo_art.weatherboy.Constants;
import com.leo_art.weatherboy.R;
import com.leo_art.weatherboy.WeatherApplication;
import com.leo_art.weatherboy.activity.SettingsActivity;
import com.leo_art.weatherboy.location.LocationEngine;
import com.leo_art.weatherboy.model.Hero;
import com.leo_art.weatherboy.model.Main;
import com.leo_art.weatherboy.model.Settings;
import com.leo_art.weatherboy.model.Status;
import com.leo_art.weatherboy.networkUtils.RequestManager;
import com.leo_art.weatherboy.networkUtils.VolleyErrorHelper;
import com.leo_art.weatherboy.utils.Converter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class WeatherFragment extends Fragment {
    private static final String ARG_HERO = "hero";

    protected List<Status> statuses;
    protected Hero hero;

    protected LinearLayout llInput;
    protected ImageView ivSettings;
    protected ImageView ivWeatherIcon;
    protected TextView tvLocation;
    protected TextView tvStatus;
    protected TextView tvTemp;
    protected ImageView ivHero;

    private Settings settings;

    public WeatherFragment() {
    }

    public static WeatherFragment newInstance(Hero hero) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_HERO, hero);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            hero = (Hero)getArguments().getSerializable(ARG_HERO);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        animateInZoom(ivWeatherIcon);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        initUI(view);

        loadData();

        return view;
    }

    public void loadData() {
        settings = WeatherApplication.getInstance().getSettings(getActivity());
        String city = settings.getCity();

        if (city == null || city.equals("")) {
            city = LocationEngine.getInstance(getActivity()).getCityName();

            Realm realm = Realm.getInstance(getActivity());
            realm.beginTransaction();
            settings.setCity(city); // update city
            realm.commitTransaction();
        }

        if (!TextUtils.isEmpty(city)) {
            loadWeatherJSON(city);
            tvLocation.setText(city);
        }
    }

    private void loadWeatherJSON(String city) {
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&" + Constants.APPID + "&lang=ua";

        RequestManager.getInstance().doRequest().getWeatherJSON(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                    loadStatuses();
                    try {
                        String mainJson = response.getJSONObject("main").toString();
                        Gson gson = new GsonBuilder().create();
                        Main main = gson.fromJson(mainJson, Main.class);
                        if (main != null) {
                            Status status = findStatusByTemperature(Converter.kelvinToCel(main.getTemp()));
                            if (status != null) {
                                initWeather(status, main);
                            } else {
                                Toast.makeText(getActivity(), "Coudn't get status data", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Coudn't get main data", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR==" + VolleyErrorHelper.getMessage(error, getActivity()));
            }
        });
    }

    private void initUI(View view) {
        llInput = (LinearLayout) view.findViewById(R.id.ll_input);
        ivSettings = (ImageView) view.findViewById(R.id.iv_settings);
        ivWeatherIcon = (ImageView) view.findViewById(R.id.iv_weather_icon);
        tvLocation = (TextView) view.findViewById(R.id.tv_location);
        tvStatus = (TextView) view.findViewById(R.id.tv_status);
        tvTemp = (TextView) view.findViewById(R.id.tv_temp);
        ivHero = (ImageView) view.findViewById(R.id.iv_hero);
        animateInZoom(ivWeatherIcon);

        ivSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivityForResult(intent, Constants.REQUEST_SETTINGS);
            }
        });
    }

    private void animateInZoom(View view) {
        YoYo.with(Techniques.ZoomIn)
                .duration(1000)
                .playOn(view);
    }

    private void loadStatuses() {
        statuses = new ArrayList<>();
        statuses.add(new Status(-100, -15, Status.Name.VERY_COLD, ""));
        statuses.add(new Status(-14, 0, Status.Name.COLD, ""));
        statuses.add(new Status(1, 7, Status.Name.COOL, ""));
        statuses.add(new Status(7, 15, Status.Name.NORMAL, ""));
        statuses.add(new Status(16, 25, Status.Name.WARM, ""));
        statuses.add(new Status(26, 100, Status.Name.HOT, ""));
    }

    private Status findStatusByTemperature(double temperature) {
        int count = statuses.size();
        for (int i = 0; i < count; ++i) {
            if (statuses.get(i).getMinTemperature() <= temperature && statuses.get(i).getMaxTemperature() >= temperature) {
                return statuses.get(i);
            }
        }
        return null;
    }

    private void initWeather(Status status, Main main) {

        int number = 0;
        String[] array = getResources().getStringArray(R.array.metrics_array);
        for (int i = 0; i < array.length; i++) {
            if (settings.getMetric().endsWith(array[i])) {
                number = i;
            }
        }

        switch (number) {
            case 0: {
                tvTemp.setText(String.format("%.1f", Converter.kelvinToCel(main.getTemp())));
                break;
            }
            case 1: {
                tvTemp.setText(String.format("%.1f", main.getTemp()));
                break;
            }
            case 2: {
                tvTemp.setText(String.format("%.1f", Converter.kelvinToFar(main.getTemp())));
                break;
            }
            default: {
                break;
            }
        }

        tvStatus.setText(status.getName().name().toLowerCase());

        switch (status.getName()){
            case COLD:
                ivHero.setImageResource(hero.getImageTypeCold());
                break;
            case COOL:
                ivHero.setImageResource(hero.getImageTypeCool());
                break;
            case HOT:
                ivHero.setImageResource(hero.getImageTypeHot());
                break;
            case NORMAL:
                ivHero.setImageResource(hero.getImageTypeNormal());
                break;
            case VERY_COLD:
                ivHero.setImageResource(hero.getImageTypeVeryCold());
                break;
            case WARM:
                ivHero.setImageResource(hero.getImageTypeWarm());
                break;
            default:
                throw new IllegalStateException("Undefined status");
        }
    }
}
