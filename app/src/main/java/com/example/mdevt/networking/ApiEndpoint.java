package com.example.mdevt.networking;

import com.example.mdevt.networking.Rica;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;

interface ApiEndpoint {
    @GET("rica_user/")
    Call<Rica>  login(@Header ("Authorization")String token , @Body Rica rica );
}
