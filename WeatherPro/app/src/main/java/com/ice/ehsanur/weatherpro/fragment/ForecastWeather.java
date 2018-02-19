package com.ice.ehsanur.weatherpro.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ice.ehsanur.weatherpro.R;
import com.ice.ehsanur.weatherpro.WeatherMainSources.Forecast;
import com.ice.ehsanur.weatherpro.WeatherMainSources.Results;
import com.ice.ehsanur.weatherpro.adapter.DayForcastAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ForecastWeather extends Fragment {

    DayForcastAdapter dayForcastAdapter;
    ListView listView;

    public ForecastWeather() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast_weather, container, false);
        listView = (ListView) view.findViewById(R.id.forcastListView);
// Active SharedPreferences In  this Fragment
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("weather", Context.MODE_PRIVATE);

        String json="";
        Results results= new Results();
        ArrayList<Forecast>forecasts = new ArrayList<>(); // ArrayList Object For Forecasts
        try {
 // Convert Json To ArrayList Object For Forecast
            json = sharedPreferences.getString("results", "");
            Gson gson = new Gson();
            results = gson.fromJson(json, Results.class);
            forecasts = (ArrayList<Forecast>) results.getChannel().getItem().getForecast();
            forecasts.remove(0);
        }catch (NullPointerException e){}
 // Set Forecast Weather On ListView Using Adapter
        dayForcastAdapter = new DayForcastAdapter(getActivity(),forecasts);
        listView.setAdapter(dayForcastAdapter);
        return view;
    }

}
