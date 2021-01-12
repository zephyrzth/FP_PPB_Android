package com.example.sejamu;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("list")
    Call<GetItem> listItem();

    @GET("show/{id}")
    Call<PostPutDelItem> showItem(@Path("id") int id);

    @FormUrlEncoded
    @POST("store")
    Call<PostPutDelItem> storeItem(@Field("judul") String judul,
                                   @Field("harga") int harga,
                                   @Field("filename") String filename,
                                   @Field("photofile") String photofile);

    @FormUrlEncoded
    @POST("login")
    Call<PostPutDelItem> storeItem(@Field("email") String email,
                                   @Field("nama") String nama,
                                   @Field("password") String password,
                                   @Field("token") String token);
}

