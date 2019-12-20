package com.baseprojectmvvm.ui.onboarding.splash;

import android.os.Handler;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.baseprojectmvvm.constant.AppConstants;
import com.baseprojectmvvm.data.DataManager;


public class SplashViewModel extends ViewModel {

    private static final long SPLASH_TIME_OUT = 1000;

    private MutableLiveData<String> splashLiveData = new MutableLiveData<>();

    // This method gives the splash live data object
    MutableLiveData<String> getSplashLiveData() {
        return splashLiveData;
    }

    // Method to start new activity after 1 second
    void showSplashScreen() {
        Handler mHandler = new Handler();
        mHandler.postDelayed(() -> {
            if (!DataManager.getInstance().getAccessToken().isEmpty())
                splashLiveData.setValue(AppConstants.SplashConstants.OPEN_HOME_SCREEN);
            else
                splashLiveData.setValue(AppConstants.SplashConstants.OPEN_ON_BOARDING_SCREEN);
        }, SPLASH_TIME_OUT);
    }
}
