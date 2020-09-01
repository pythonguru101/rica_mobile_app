package com.example.mdevt;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class apiClient {

    private static Retrofit retrofit;
    private static final String BaseUrl = "https://5cf9bbd33ba8.ngrok.io/";

    public static Retrofit getapiClient(){
        if (retrofit==null){
            retrofit=new Retrofit.Builder().baseUrl(BaseUrl).addConverterFactory(
                    GsonConverterFactory.create()).build();


        }
        return retrofit;
    }
}
