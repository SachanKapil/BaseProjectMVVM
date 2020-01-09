package com.baseprojectmvvm.data;


import android.content.Context;

import com.baseprojectmvvm.constant.AppConstants;
import com.baseprojectmvvm.data.api.ApiManager;
import com.baseprojectmvvm.data.model.BaseResponse;
import com.baseprojectmvvm.data.model.onboarding.User;
import com.baseprojectmvvm.data.preferences.PreferenceManager;
import com.google.gson.Gson;

import retrofit2.Call;

public class DataManager {

    private static DataManager instance;
    private ApiManager apiManager;
    private PreferenceManager mPrefManager;


    private DataManager(Context context) {
        //Initializing SharedPreference object
        mPrefManager = PreferenceManager.getInstance(context);
    }

    /**
     * Returns the single instance of {@link DataManager} if
     * {@link #init(Context)} is called first
     *
     * @return instance
     */
    public static DataManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Call init() before getInstance()");
        }
        return instance;
    }

    /**
     * Method used to create an instance of {@link DataManager}
     *
     * @param context of the application passed from the {@link com.baseprojectmvvm.App}
     * @return instance if it is null
     */
    public synchronized static DataManager init(Context context) {
        if (instance == null) {
            instance = new DataManager(context);
        }
        return instance;
    }

    /**
     * Method to initialize {@link ApiManager} class
     */
    public void initApiManager() {
        apiManager = ApiManager.getInstance();
    }

    public String getRefreshToken() {
        return mPrefManager.getString(AppConstants.PreferenceConstants.REFRESH_TOKEN);
    }

    public void saveRefreshToken(String refreshToken) {
        mPrefManager.putString(AppConstants.PreferenceConstants.REFRESH_TOKEN, refreshToken);
    }

    public String getAccessToken() {
        return mPrefManager.getString(AppConstants.PreferenceConstants.ACCESS_TOKEN);
    }

    public void saveAccessToken(String accessToken) {
        mPrefManager.putString(AppConstants.PreferenceConstants.ACCESS_TOKEN, accessToken);
    }

    public void saveUserDetails(User user) {
        String userDetail = new Gson().toJson(user);
        mPrefManager.putString(AppConstants.PreferenceConstants.USER_DETAILS, userDetail);
    }

    public User getUserDetails() {
        String userDetail = mPrefManager.getString(AppConstants.PreferenceConstants.USER_DETAILS);
        User user = new Gson().fromJson(userDetail, User.class);
        return user;
    }

    public String getDeviceToken() {
        String deviceToken = "12345";
        if (!mPrefManager.getString(AppConstants.PreferenceConstants.DEVICE_TOKEN).isEmpty())
            deviceToken = mPrefManager.getString(AppConstants.PreferenceConstants.DEVICE_TOKEN);
        return deviceToken;
    }

    public void saveDeviceToken(String deviceToken) {
        mPrefManager.putString(AppConstants.PreferenceConstants.DEVICE_TOKEN, deviceToken);
    }

    public String getDeviceId() {
        return mPrefManager.getString(AppConstants.PreferenceConstants.DEVICE_ID);
    }

    public void saveDeviceId(String deviceId) {
        mPrefManager.putString(AppConstants.PreferenceConstants.DEVICE_ID, deviceId);
    }

    public void clearPreferences() {
        mPrefManager.clearAllPrefs();
    }

    public Call<BaseResponse<User>> hitLoginApi(User user) {
        return apiManager.hitLoginApi(user);
    }

    public Call<BaseResponse<User>> hitSignUpApi(User user) {
        return apiManager.hitSignUpApi(user);
    }

    public Call<BaseResponse<Object>> hitLogOutApi() {
        return apiManager.hitLogOutApi();
    }

}
