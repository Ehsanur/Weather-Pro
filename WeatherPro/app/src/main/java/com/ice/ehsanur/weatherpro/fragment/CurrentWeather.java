package com.ice.ehsanur.weatherpro.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ice.ehsanur.weatherpro.R;
import com.ice.ehsanur.weatherpro.WeatherMainSources.Results;
import com.ice.ehsanur.weatherpro.tools.Utility;
import com.google.gson.Gson;

public class CurrentWeather extends Fragment {
    ImageView currentWeatherIV;
    TextView currentTemp,tempUnit,conditionText,updateTime;
    Utility utility=new Utility();

    public CurrentWeather() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_current_weather,container,false);
        currentWeatherIV = (ImageView) view.findViewById(R.id.currentWeatherImage);

        currentTemp = (TextView) view.findViewById(R.id.showCurrentTemp);
        conditionText = (TextView) view.findViewById(R.id.currentWeatherConditionText);
        tempUnit = (TextView) view.findViewById(R.id.showCurrentTempUnit);
        updateTime = (TextView) view.findViewById(R.id.updatetime);

        String json="";
        Results results= new Results(); // Make New Object for Results
        try {
// Get SharedPreferences Active
            SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("weather", Context.MODE_PRIVATE);
// Get data From SharedPreferences And Convert Json to Result Object Format
            json = sharedPreferences.getString("results", "");
            Gson gson = new Gson();
            results = gson.fromJson(json,Results.class);
// Set text On Current Weather View
            currentTemp.setText(results.getChannel().getItem().getCondition().getTemp());
            conditionText.setText(results.getChannel().getItem().getCondition().getText());
            currentWeatherIV.setImageResource(utility.setImageResource(results.getChannel().getItem().getCondition().getCode()));
            updateTime.setText("Last Update : "+results.getChannel().getLastBuildDate());
        }catch (NullPointerException e){}
        return view;
    }
}
