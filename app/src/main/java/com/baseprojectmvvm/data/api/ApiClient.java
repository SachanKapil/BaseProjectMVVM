package com.baseprojectmvvm.data.api;

import com.baseprojectmvvm.data.model.BaseResponse;
import com.baseprojectmvvm.data.model.onboarding.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;

interface ApiClient {

    @POST("login")
    Call<BaseResponse<User>> login(@Body User user);


    @POST("signup")
    Call<BaseResponse<User>> signUp(@Body User user);

    @PUT("logout")
    Call<BaseResponse<Object>> logOut();

}