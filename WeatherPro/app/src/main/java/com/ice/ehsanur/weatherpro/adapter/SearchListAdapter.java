package com.ice.ehsanur.weatherpro.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ice.ehsanur.weatherpro.R;
import com.ice.ehsanur.weatherpro.searchpojo.RESULT;

import java.util.ArrayList;

public class SearchListAdapter extends ArrayAdapter {
    ArrayList<RESULT>results;
    Context context;
    public SearchListAdapter(Context context,ArrayList<RESULT>results) {
        super(context, R.layout.search_result_row, results);
        this.results=results;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.search_result_row,parent,false);
        RESULT result = results.get(position);

        TextView cityname = (TextView) convertView.findViewById(R.id.showSearchName);
        TextView country = (TextView) convertView.findViewById(R.id.showSearchCountry);

        try {
            cityname.setText(result.getName());
            country.setText(result.getC());
        }catch (NullPointerException e){}
        return convertView;
    }
}
