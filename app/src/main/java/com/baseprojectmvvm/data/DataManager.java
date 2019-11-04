package com.baseprojectmvvm.data;


import android.content.Context;

import com.baseprojectmvvm.constant.AppConstants;
import com.baseprojectmvvm.data.api.ApiManager;
import com.baseprojectmvvm.data.preferences.PreferenceManager;

public class DataManager {

    private static DataManager instance;
    private ApiManager apiManager;
    private PreferenceManager mPrefManager;
//    private FirebaseDatabaseQueries mFirebaseQueries;


    private DataManager(Context context) {
        //Initializing SharedPreference object
        mPrefManager = PreferenceManager.getInstance(context);
//        mFirebaseQueries = FirebaseDatabaseQueries.getInstance(context);
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


//    public Call<CategoryListResponse> hitGetCategoryApi(int type) {
//        return apiManager.hitCategoryApi(type);
//    }

    public String getRefreshToken() {
        return mPrefManager.getString(AppConstants.PreferenceConstants.REFRESH_TOKEN);
    }

    public void saveRefreshToken(String refreshToken) {
        mPrefManager.putString(AppConstants.PreferenceConstants.REFRESH_TOKEN, refreshToken);
    }

    public void saveAccessToken(String accessToken) {
        mPrefManager.putString(AppConstants.PreferenceConstants.ACCESS_TOKEN, accessToken);
    }


    public String getAccessToken() {
        return mPrefManager.getString(AppConstants.PreferenceConstants.ACCESS_TOKEN);
    }


    public void clearPreferences() {
        mPrefManager.clearAllPrefs();
    }


    public String getDeviceToken() {
        return mPrefManager.getString(AppConstants.PreferenceConstants.DEVICE_TOKEN);
    }

    public void saveDeviceToken(String deviceToken) {
        mPrefManager.putString(AppConstants.PreferenceConstants.DEVICE_TOKEN, deviceToken);
    }

    public void saveUserId(Integer userId) {
        mPrefManager.putString(AppConstants.PreferenceConstants.USER_ID, String.valueOf(userId));
    }

    public String getUserId() {
        return mPrefManager.getString(AppConstants.PreferenceConstants.USER_ID);
    }

//    public User getUserDetails() {
//        User userDetail;
//        String details = mPrefManager.getString(AppConstants.PreferenceConstants.USER_DETAILS);
//        userDetail = new Gson().fromJson(details, User.class);
//        return userDetail;
//    }

}
