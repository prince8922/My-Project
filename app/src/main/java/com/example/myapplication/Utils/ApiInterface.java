package com.example.myapplication.Utils;

import com.example.myapplication.GetData.GetLoginData;
import com.example.myapplication.GetData.GetSignupData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("signup.php")
    Call<GetSignupData> getSignupData(
            @Field("name") String name,
            @Field("email") String email,
            @Field("contact") String contact,
            @Field("dob") String dob,
            @Field("password") String password,
            @Field("gender") String gender,
            @Field("city") String city);

    @FormUrlEncoded
    @POST("login.php")
    Call<GetLoginData> getLoginData(
            @Field("email") String email,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("updateProfile.php")
    Call<GetSignupData> updateProfileData(
            @Field("id") String id,
            @Field("name") String name,
            @Field("email") String email,
            @Field("contact") String contact,
            @Field("dob") String dob,
            @Field("password") String password,
            @Field("gender") String gender,
            @Field("city") String city);
}
