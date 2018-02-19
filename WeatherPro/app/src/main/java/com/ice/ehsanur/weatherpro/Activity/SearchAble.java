package com.ice.ehsanur.weatherpro.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.ice.ehsanur.weatherpro.R;
import com.ice.ehsanur.weatherpro.adapter.SearchListAdapter;
import com.ice.ehsanur.weatherpro.searchpojo.MyPojo;
import com.ice.ehsanur.weatherpro.searchpojo.RESULT;
import com.ice.ehsanur.weatherpro.searchpojo.SearchApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchAble extends AppCompatActivity {
    ImageButton searchButton ;
    EditText getText;
    ListView resultList;
    SearchListAdapter searchListAdapter;
    SearchApi searchApi;
    MyPojo myPojo;
    ArrayList<RESULT> mSearchResult = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_able);

        searchButton = (ImageButton) findViewById(R.id.searchButton);
        getText = (EditText) findViewById(R.id.serachEdit);
        resultList = (ListView) findViewById(R.id.serachShowResutlList);

        networkInitializerForSearch();  // Initialize NetWork For Search City And Country
    }
// Set Initialize Using Retrofit
    public void networkInitializerForSearch(){
        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl("http://autocomplete.wunderground.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        searchApi = retrofit.create(SearchApi.class);
    }
// Get Search Data From Api
    public void getSearchData(String data){
        String query=data;
        final Call<MyPojo> myPojoCall=searchApi.getPojoSearch("aq?query="+query);
        myPojoCall.enqueue(new Callback<MyPojo>() {
            @Override
            public void onResponse(Call<MyPojo> call, Response<MyPojo> response) {
                myPojo = response.body();
                try {
//  Get Search Result In ArrayList Object
                    mSearchResult = (ArrayList<RESULT>) myPojo.getRESULTS();
                }catch (NullPointerException e){}
// Set Result On Search List View
                searchListAdapter= new SearchListAdapter(SearchAble.this,mSearchResult);
                resultList.setAdapter(searchListAdapter);
 // Set On Item Click Listener
                resultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        clickListItem(position,mSearchResult); // Method For OnItem Click
                    }
                });
            }

            @Override
            public void onFailure(Call<MyPojo> call, Throwable t) {}
        });
    }
 // OnClick Item Options
    public void clickListItem(int position, ArrayList<RESULT>results){
        RESULT result= results.get(position);
        Intent intent = new Intent(this,WeatherActivity.class);
        intent.putExtra("location",result.getName());
        startActivity(intent);
        finish();
    }
// Action Search Button
    public void searchButton(View view) {
        String data = getText.getText().toString();
        if (data.isEmpty() || data.trim().equals("")){
            getText.setError("Enter Your City");
        }else{
            getSearchData(data);
        }
    }

}
