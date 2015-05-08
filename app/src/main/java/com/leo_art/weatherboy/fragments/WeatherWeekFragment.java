package com.leo_art.weatherboy.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.leo_art.weatherboy.Constants;
import com.leo_art.weatherboy.R;
import com.leo_art.weatherboy.adapters.WeekWeatherAdapter;
import com.leo_art.weatherboy.model.WeekForecast;
import com.leo_art.weatherboy.networkUtils.RequestManager;
import com.leo_art.weatherboy.networkUtils.VolleyErrorHelper;
import com.leo_art.weatherboy.utils.WeatherUtils;
import com.sefford.circularprogressdrawable.CircularProgressDrawable;
import com.twotoasters.jazzylistview.JazzyListView;
import com.twotoasters.jazzylistview.effects.CardsEffect;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class WeatherWeekFragment extends Fragment {

    private CircularProgressDrawable drawable;
    private JazzyListView weekWeatherList;

    private ImageView ivDrawable;
    private Animator currentAnimation;

    private boolean dataWasLoaded = false;
    private int animCounter = 0;

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
        weekWeatherList = (JazzyListView) v.findViewById(R.id.weekWeatherListView);
        weekWeatherList.setTransitionEffect(new CardsEffect());
        weekWeatherList.addHeaderView(inflater.inflate(R.layout.week_weather_header, null, false));

        ivDrawable = (ImageView) v.findViewById(R.id.iv_drawable);
        drawable = new CircularProgressDrawable.Builder()
                .setRingWidth(getResources().getDimensionPixelSize(R.dimen.drawable_ring_size))
                .setOutlineColor(getResources().getColor(android.R.color.darker_gray))
                .setRingColor(getResources().getColor(android.R.color.holo_green_light))
                .setCenterColor(getResources().getColor(android.R.color.holo_blue_dark))
                .create();
        ivDrawable.setImageDrawable(drawable);

        currentAnimation = prepareStyle3Animation();
        currentAnimation.start();

        currentAnimation.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd (Animator animation){

                if (dataWasLoaded == true) {
                    ivDrawable.setVisibility(View.GONE);
                } else {
                    currentAnimation.start();
                }
            }

        });
        loadWeatherJSON();
        return v;
    }



    /**
     * This animation was intended to keep a pressed state of the Drawable
     *
     * @return Animation
     */
    private Animator preparePressedAnimation() {
        Animator animation = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.CIRCLE_SCALE_PROPERTY,
                drawable.getCircleScale(), 0.65f);
        animation.setDuration(120);
        return animation;
    }

    /**
     * This animation will make a pulse effect to the inner circle
     *
     * @return Animation
     */
    private Animator preparePulseAnimation() {
        AnimatorSet animation = new AnimatorSet();

        Animator firstBounce = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.CIRCLE_SCALE_PROPERTY,
                drawable.getCircleScale(), 0.88f);
        firstBounce.setDuration(300);
        firstBounce.setInterpolator(new CycleInterpolator(1));
        Animator secondBounce = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.CIRCLE_SCALE_PROPERTY,
                0.75f, 0.83f);
        secondBounce.setDuration(300);
        secondBounce.setInterpolator(new CycleInterpolator(1));
        Animator thirdBounce = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.CIRCLE_SCALE_PROPERTY,
                0.75f, 0.80f);
        thirdBounce.setDuration(300);
        thirdBounce.setInterpolator(new CycleInterpolator(1));

        animation.playSequentially(firstBounce, secondBounce, thirdBounce);
        return animation;
    }

    private void loadWeatherJSON() {

        String forecatsURL = WeatherUtils.formForectast("Lviv", 7).concat("&").concat(Constants.APPID);

        RequestManager.getInstance().doRequest().getWeatherJSON(forecatsURL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                    try {
                        Gson gson = new GsonBuilder().create();
                        Type collectionType = new TypeToken<List<WeekForecast>>() {
                        }.getType();
                        String jsonString = response.getJSONArray("list").toString();
                        List<WeekForecast> forecastList = gson.fromJson(jsonString, collectionType);
                        if (forecastList != null) {
                            setWeekWeatherList(forecastList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                dataWasLoaded = true;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dataWasLoaded = true;
                System.out.println("ERROR==" + VolleyErrorHelper.getMessage(error, getActivity()));
            }
        });
    }

    private void setWeekWeatherList(List<WeekForecast> forecasts) {
        weekWeatherList.setAdapter(new WeekWeatherAdapter(getActivity(), forecasts));
    }


    /**
     * Style 1 animation will simulate a indeterminate loading while taking advantage of the inner
     * circle to provide a progress sense
     *
     * @return Animation
     */
    private Animator prepareStyle1Animation() {
        AnimatorSet animation = new AnimatorSet();

        final Animator indeterminateAnimation = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.PROGRESS_PROPERTY, 0, 3600);
        indeterminateAnimation.setDuration(3600);

        Animator innerCircleAnimation = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.CIRCLE_SCALE_PROPERTY, 0f, 0.75f);
        innerCircleAnimation.setDuration(3600);
        innerCircleAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                drawable.setIndeterminate(true);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                indeterminateAnimation.end();
                drawable.setIndeterminate(false);
                drawable.setProgress(0);
            }
        });

        animation.playTogether(innerCircleAnimation, indeterminateAnimation);
        return animation;
    }

    /**
     * Style 2 animation will fill the outer ring while applying a color effect from red to green
     *
     * @return Animation
     */
    private Animator prepareStyle2Animation() {
        AnimatorSet animation = new AnimatorSet();

        ObjectAnimator progressAnimation = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.PROGRESS_PROPERTY,
                0f, 1f);
        progressAnimation.setDuration(3600);
        progressAnimation.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator colorAnimator = ObjectAnimator.ofInt(drawable, CircularProgressDrawable.RING_COLOR_PROPERTY,
                getResources().getColor(android.R.color.holo_red_dark),
                getResources().getColor(android.R.color.holo_green_light));
        colorAnimator.setEvaluator(new ArgbEvaluator());
        colorAnimator.setDuration(3600);

        animation.playTogether(progressAnimation, colorAnimator);
        return animation;
    }

    /**
     * Style 3 animation will turn a 3/4 animation with Anticipate/Overshoot interpolation to a
     * blank waiting - like state, wait for 2 seconds then return to the original state
     *
     * @return Animation
     */
    private Animator prepareStyle3Animation() {
        AnimatorSet animation = new AnimatorSet();

        ObjectAnimator progressAnimation = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.PROGRESS_PROPERTY, 0.75f, 0f);

        progressAnimation.setDuration(1200);
        progressAnimation.setInterpolator(new AnticipateInterpolator());

        Animator innerCircleAnimation = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.CIRCLE_SCALE_PROPERTY, 0.75f, 0f);
        innerCircleAnimation.setDuration(1200);
        innerCircleAnimation.setInterpolator(new AnticipateInterpolator());

        ObjectAnimator invertedProgress = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.PROGRESS_PROPERTY, 0f, 0.75f);
        invertedProgress.setDuration(1200);
        invertedProgress.setStartDelay(3200);

        invertedProgress.setInterpolator(new OvershootInterpolator());

        Animator invertedCircle = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.CIRCLE_SCALE_PROPERTY, 0f, 0.75f);
        invertedCircle.setDuration(1200);
        invertedCircle.setStartDelay(3200);
        invertedCircle.setInterpolator(new OvershootInterpolator());

        animation.playTogether(progressAnimation, innerCircleAnimation, invertedProgress, invertedCircle);
        return animation;
    }


}
