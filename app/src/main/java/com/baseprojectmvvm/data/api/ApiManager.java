package com.baseprojectmvvm.data.api;

import androidx.annotation.NonNull;

import com.baseprojectmvvm.BuildConfig;
import com.baseprojectmvvm.data.DataManager;
import com.baseprojectmvvm.data.model.BaseResponse;
import com.baseprojectmvvm.data.model.onboarding.User;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    private static ApiManager instance;
    private final ApiClient apiClient;
    private final ApiClient apiRegisteredClient;
    private OkHttpClient.Builder httpClient;
    private OkHttpClient.Builder httpRegisteredClient;

    private ApiManager() {
        httpClient = getHttpClient();
        httpRegisteredClient = getHttpRegisteredClient();
        apiClient = getApiClient();
        apiRegisteredClient = getApiRegisteredClient();
    }

    public static synchronized ApiManager getInstance() {
        if (instance == null) {
            instance = new ApiManager();
        }
        return instance;
    }

    private ApiClient getApiClient() {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()));
        Retrofit retrofit = retrofitBuilder.client(httpClient.build()).build();
        return retrofit.create(ApiClient.class);
    }

    private ApiClient getApiRegisteredClient() {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()));
        Retrofit retrofit = retrofitBuilder.client(httpRegisteredClient.build()).build();
        return retrofit.create(ApiClient.class);
    }


    private OkHttpClient.Builder getHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request.Builder requestBuilder;
                    requestBuilder = original.newBuilder()
                            .method(original.method(), original.body());
                    requestBuilder.header("Authorization", "Bearer " + DataManager.getInstance().getAccessToken())
                            .header("Accept", "application/json");

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                })
                .addInterceptor(getLoggingInterceptor())
                .readTimeout(35000, TimeUnit.MILLISECONDS)
                .writeTimeout(20000, TimeUnit.MILLISECONDS);
    }

    private OkHttpClient.Builder getHttpRegisteredClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request.Builder requestBuilder;
                    requestBuilder = original.newBuilder()
                            .method(original.method(), original.body());
                    requestBuilder
                            .header("Authorization", "Bearer " + DataManager.getInstance().getAccessToken())
                            .header("Accept", "application/json");

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                })
                .addInterceptor(getLoggingInterceptor())
                .readTimeout(35000, TimeUnit.MILLISECONDS)
                .writeTimeout(20000, TimeUnit.MILLISECONDS);
    }

    @NonNull
    private HttpLoggingInterceptor getLoggingInterceptor() {
        if (BuildConfig.DEBUG)
            return new HttpLoggingInterceptor(new CustomHttpLogger())
                    .setLevel(HttpLoggingInterceptor.Level.BODY);
        else return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE);
    }


    public Call<BaseResponse<User>> hitLoginApi(User user) {
        return apiClient.login(user);
    }

    public Call<BaseResponse<User>> hitSignUpApi(User user) {
        return apiClient.signUp(user);
    }

    public Call<BaseResponse<Object>> hitForgotPasswordApi(String email) {
        return apiRegisteredClient.forgotPassword(email);
    }

    public Call<BaseResponse<Object>> hitLogOutApi() {
        return apiRegisteredClient.logOut();
    }
}