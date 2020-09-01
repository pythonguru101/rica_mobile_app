package com.example.mdevt.networking.image;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UploadApi {

    @Multipart
    @POST("sub_agent_signup/")
    Call<RequestBody> uploadImage(@Header("Authorization") String token,
                                  @Part MultipartBody.Part idPart,
                                  @Part MultipartBody.Part addressPart,
                                  @Part("mobile_number") RequestBody mobile_number,
                                  @Part ("name") RequestBody name,
                                  @Part  ("surname") RequestBody surname,
                                  @Part ("id_passport_expiry") RequestBody id_passport_expiry,
                                  @Part ("id_passport_number") RequestBody id_passport_number,
                                  @Part ("address1") RequestBody address1,
                                  @Part ("address2") RequestBody address2,
                                  @Part ("address3") RequestBody address3,
                                  @Part ("postal_code") RequestBody postal_code
    );
}
