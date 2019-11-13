package com.baseprojectmvvm.data.api;

import androidx.annotation.NonNull;

import com.baseprojectmvvm.BuildConfig;
import com.baseprojectmvvm.constant.AppConstants;
import com.baseprojectmvvm.data.DataManager;
import com.baseprojectmvvm.data.model.BaseResponse;
import com.baseprojectmvvm.data.model.onboarding.User;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    private static ApiManager instance;
    private static String versionNumber = BuildConfig.VERSION_NAME;
    private final ApiClient apiClient;
    private OkHttpClient.Builder httpClient;

    private ApiManager() {
        httpClient = getHttpClient();
        apiClient = getRetrofitService();
    }

    public static synchronized ApiManager getInstance() {
        if (instance == null) {
            instance = new ApiManager();
        }
        return instance;
    }

    private OkHttpClient.Builder getHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request.Builder requestBuilder;
                        requestBuilder = original.newBuilder()

                                .method(original.method(), original.body());
//                        if (CacheManager.getInstance().getUserCreadential() != null) {
//                            requestBuilder.header("username",
//                                    CacheManager.getInstance().getUserCreadential().getUsername())
//                                    .header("password",
//                                            CacheManager.getInstance().getUserCreadential().getPassword());
//                        }
                        requestBuilder.header("Authorization", "Bearer " + DataManager.getInstance().getAccessToken())
                                .header("Accept", "application/json")
                                .header("platform", "2")
                                .header("versionnumber", versionNumber);

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
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

    private ApiClient getRetrofitService() {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()));
        Retrofit retrofit = retrofitBuilder.client(httpClient.build()).build();
        return retrofit.create(ApiClient.class);
    }

    public Call<BaseResponse<User>> hitLoginApi(User user) {
        return apiClient.login(user);
    }

    public Call<BaseResponse<User>> hitSignUpApi(User user) {
        return apiClient.signUp(user);
    }

}