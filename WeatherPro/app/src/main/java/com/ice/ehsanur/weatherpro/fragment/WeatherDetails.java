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
import com.ice.ehsanur.weatherpro.WeatherMainSources.Forecast;
import com.ice.ehsanur.weatherpro.WeatherMainSources.Results;
import com.ice.ehsanur.weatherpro.tools.Utility;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherDetails extends Fragment {

    Utility utility = new Utility();

    public WeatherDetails() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_details, container, false);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("weather", Context.MODE_PRIVATE);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageDeails);
        TextView localCity = (TextView) view.findViewById(R.id.locationCityTV);
        TextView temp = (TextView) view.findViewById(R.id.detailsTempereture);
        TextView humidity = (TextView) view.findViewById(R.id.detailsHumidity);
        TextView visibility = (TextView) view.findViewById(R.id.detailsVisibility);
        TextView prassure = (TextView) view.findViewById(R.id.detailsPressure);
        TextView weatherMoreDetails = (TextView) view.findViewById(R.id.weatherMoreDetails);

        Results results= new Results();
        String json ="";
        try {
            json = sharedPreferences.getString("results", "");
            Gson gson = new Gson();
            results = gson.fromJson(json, Results.class);

            imageView.setImageResource(utility.setImageResource(results.getChannel().getItem().getCondition().getCode()));
            localCity.setText(results.getChannel().getLocation().getCity());
            temp.setText(results.getChannel().getItem().getCondition().getTemp());
            humidity.setText(results.getChannel().getAtmosphere().getHumidity()+"%");
            visibility.setText(results.getChannel().getAtmosphere().getVisibility()+"mi");
            prassure.setText(results.getChannel().getAtmosphere().getPressure()+"in");

            ArrayList<Forecast>forecast = (ArrayList<Forecast>) results.getChannel().getItem().getForecast();
            Forecast forecast1 = forecast.get(0);
            weatherMoreDetails.setText(
                    "Today - "+
                            results.getChannel().getItem().getCondition().getText()+
                            " With High "+forecast1.getHigh()+(char) 0x00B0+
                            " And Low "+forecast1.getLow()+(char) 0x00B0+" "+
                            " Wind chill Direction "+results.getChannel().getWind().getDirection()+
                            " With Speed "+results.getChannel().getWind().getSpeed()+"mPh.\n"+
                            "Sunrise "+results.getChannel().getAstronomy().getSunrise()+
                            " And Sunset "+results.getChannel().getAstronomy().getSunset()
            );
        }catch (NullPointerException e){}
        return view;
    }

}
