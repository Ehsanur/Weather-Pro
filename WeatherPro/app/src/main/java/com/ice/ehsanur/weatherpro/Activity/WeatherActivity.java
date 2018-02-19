package com.ice.ehsanur.weatherpro.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.view.Window;

import com.ice.ehsanur.weatherpro.R;
import com.ice.ehsanur.weatherpro.WeatherMainSources.Forecast;
import com.ice.ehsanur.weatherpro.WeatherMainSources.Results;
import com.ice.ehsanur.weatherpro.WeatherMainSources.WeatherApi;
import com.ice.ehsanur.weatherpro.WeatherMainSources.WeatherMain;
import com.ice.ehsanur.weatherpro.adapter.FragmentPageAdapter;
import com.ice.ehsanur.weatherpro.fragment.CurrentWeather;
import com.ice.ehsanur.weatherpro.fragment.ForecastWeather;
import com.ice.ehsanur.weatherpro.fragment.WeatherDetails;
import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WeatherActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    WeatherApi weatherapi;
    ViewPager viewPager;
    FragmentPageAdapter mFragmentPageAdapter;
    Toolbar toolbar;
    WeatherMain weatherMain;
    String mLocation = "";
    ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        sharedPreferences = getSharedPreferences("weather",MODE_PRIVATE); // Create SharedPreference For Save Data

// Set Location
        String getintent = getIntent().getStringExtra("location");
        try{
            getintent.isEmpty();
            mLocation = getintent;
        }catch (NullPointerException e){
            try{
                String json = sharedPreferences.getString("results","");
                Gson gson = new Gson();
                Results results = gson.fromJson(json,Results.class);
                mLocation = results.getChannel().getLocation().getCity()+results.getChannel().getLocation().getCountry();

            }catch (NullPointerException ex){mLocation = "dhaka,bd";}
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setViewPager(viewPager);
        networkInitializer();   // Initialize Api NetWork
        getWeatherData();   // Get Data Form Api NetWork

// Set Menu On Top For Some Device Which Have Manual Menu Key
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField != null) {menuKeyField.setAccessible(true);menuKeyField.setBoolean(config, false);}
        } catch (Exception ignored) {}

// set Action bar Tex
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ab=getSupportActionBar();
    }

    // Option Menu Settings
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    //set Page View WIth Fragments
    private void setViewPager(ViewPager viewPager){
        mFragmentPageAdapter = new FragmentPageAdapter(getSupportFragmentManager());
        mFragmentPageAdapter.addFragment(new WeatherDetails());
        mFragmentPageAdapter.addFragment(new CurrentWeather());
        mFragmentPageAdapter.addFragment(new ForecastWeather());
        viewPager.setAdapter(mFragmentPageAdapter);
        viewPager.setCurrentItem(1);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                if (position==0){
                    try{
                        String json = sharedPreferences.getString("results","");
                        Gson gson = new Gson();
                        Results results = gson.fromJson(json,Results.class);
                        ab.setTitle(results.getChannel().getLocation().getCity()+","+results.getChannel().getLocation().getCountry());
                        ab.setSubtitle("Today details");
                    }catch (NullPointerException e){}
                }else if(position==1){
                    try{
                        String json = sharedPreferences.getString("results","");
                        Gson gson = new Gson();
                        Results results = gson.fromJson(json,Results.class);
                        ab.setTitle(results.getChannel().getLocation().getCity()+","+results.getChannel().getLocation().getCountry());
                        ab.setSubtitle("Current Temperature");
                    }catch (NullPointerException e){}
                }else {
                    try{
                        String json = sharedPreferences.getString("results","");
                        Gson gson = new Gson();
                        Results results = gson.fromJson(json,Results.class);
                        ab.setTitle(results.getChannel().getLocation().getCity()+","+results.getChannel().getLocation().getCountry());
                        List<Forecast> forecast= results.getChannel().getItem().getForecast();
                        int size = forecast.size()-1;
                        ab.setSubtitle("Next "+size+" Days Forecast");
                    }catch (NullPointerException e){}
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    // Set Network For Get JSON Data
    private void networkInitializer() {
        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl("https://query.yahooapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherapi = retrofit.create(WeatherApi.class);
    }

    // Get JSON Data From yahoo Weather Forecast...
    public void getWeatherData() {
        final Call<WeatherMain>weatherMainCall=weatherapi.getWeatherData("v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D\""+mLocation+"\")&format=json");

        weatherMainCall.enqueue(new Callback<WeatherMain>() {
            @Override
            public void onResponse(Call<WeatherMain> call, Response<WeatherMain> response) {
                weatherMain = response.body();
                if(weatherMain.getQuery().getCount()>0) {
                    try {
                        // Get Data From Api And Set As Results Oject
                        Results results = weatherMain.getQuery().getResults();
                        // Open   SharedPreferences As Editor Mode
                        SharedPreferences.Editor edit = sharedPreferences.edit();
                        // Converting Result Object To Json Format And Save on SharedPreferences
                        Gson gson = new Gson();
                        String json = gson.toJson(results);
                        edit.putString("results", json);
                        edit.apply();
                        edit.commit();

                        setViewPager(viewPager);
                    } catch (NullPointerException e) {}
                }
            }
            @Override
            public void onFailure(Call<WeatherMain> call, Throwable t) {ab.setTitle("Connection Fail");}
        });
    }
    // Menu Item Refresh Button
    public void reFresh(MenuItem item) {
        getWeatherData();
        setViewPager(viewPager);
    }
    // Add Location Button
    public void addLocation(MenuItem item) {
        Intent intent = new Intent(this,SearchAble.class);
        startActivity(intent);
        finish();
    }
    // Exit Application Menu Option
    public void exitapp(MenuItem item) {
        new AlertDialog.Builder(this)
                .setTitle("Exit Weather Pro")
                .setMessage("Do You Want Exit ?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {}
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }
}
