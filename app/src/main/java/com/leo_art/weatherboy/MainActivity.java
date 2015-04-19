package com.leo_art.weatherboy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.leo_art.weatherboy.adapters.FragmentPagerAdapter;
import com.leo_art.weatherboy.networkUtils.RequestManager;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {
    public static final String APPID = "APPID=99a24e041993c3cba90f16159c38f2ca";
    private ViewPager viewPager;

    private WeatherFragment currentWeatherFragment;
    private WeatherFragment nextDayWeatherFragment;
    private WeatherWeekFragment weatherWeekFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.fragmentPager);

        currentWeatherFragment = WeatherFragment.newInstance();
        nextDayWeatherFragment = WeatherFragment.newInstance();
        weatherWeekFragment = WeatherWeekFragment.newInstance();

        ArrayList<Fragment> fragmentData = new ArrayList<>();
        fragmentData.add(currentWeatherFragment);
        fragmentData.add(nextDayWeatherFragment);
        fragmentData.add(weatherWeekFragment);

        viewPager.setAdapter(new FragmentPagerAdapter(fragmentData, getSupportFragmentManager()));

        loadWeatherJSON();
    }

    private void loadWeatherJSON() {
//        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + "lviv" + "&" + APPID + "&lang=ua";
        String url = "http://api.openweathermap.org/data/2.5/weather?q=lviv";
        RequestManager.getInstance().doRequest().getWeatherJSON(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("RESP==" + response.toString());
                Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
