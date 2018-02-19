package com.ice.ehsanur.weatherpro.searchpojo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface SearchApi {
    @GET
    Call<MyPojo>getPojoSearch(@Url String url);
}

