package com.example.a5l3.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://pixabay.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public  PixabayApi getApi(){
        return retrofit.create(PixabayApi.class);
    }
}
