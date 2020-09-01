package com.example.mdevt;

import com.google.gson.annotations.SerializedName;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class testRetrofit {
    @SerializedName("title")
    private String Image;
    @SerializedName("response")
    private String Response;

    public testRetrofit(String response) {
        Response = response;
    }
    /* private static Retrofit retrofit;
        private static final String BASE_URL_API = "https://5cf9bbd33ba8.ngrok.io/";

        public static Retrofit getRetrofitInstance() {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60,TimeUnit.SECONDS).build();

            if (retrofit == null) {
                retrofit = new retrofit2.Retrofit.Builder()
                        .baseUrl(BASE_URL_API).client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }*/
    }

