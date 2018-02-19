package com.ice.ehsanur.weatherpro.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ice.ehsanur.weatherpro.R;
import com.ice.ehsanur.weatherpro.WeatherMainSources.Forecast;
import com.ice.ehsanur.weatherpro.tools.Utility;

import java.util.ArrayList;



public class DayForcastAdapter extends ArrayAdapter{
    Utility utility =new Utility();
    ArrayList<Forecast>mforecast;
    Context context;
    public DayForcastAdapter(Context context,ArrayList<Forecast>forecast) {
        super(context,R.layout.day_forecast_row, forecast);
        this.mforecast=forecast;
        this.context=context;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(context).inflate(R.layout.day_forecast_row,parent,false);

            ImageView dayImage = (ImageView) convertView.findViewById(R.id.forcastDayImage);
            TextView dayTV = (TextView) convertView.findViewById(R.id.weekDayTextView);
            TextView dayConditionTV = (TextView) convertView.findViewById(R.id.weeklydayConditionText);
            TextView dayHighTemp = (TextView) convertView.findViewById(R.id.weeklyHighTemp);
            TextView dayLowTemp = (TextView) convertView.findViewById(R.id.weeklyLowTemp);

            Forecast forecast = mforecast.get(position);
            try{
                dayImage.setImageResource(utility.setImageResource(forecast.getCode()));
                dayTV.setText(forecast.getDay());
                dayConditionTV.setText(forecast.getText());
                dayHighTemp.setText(forecast.getHigh());
                dayLowTemp.setText(forecast.getLow());
            }catch (NullPointerException e){}
            return convertView;
    }
}
