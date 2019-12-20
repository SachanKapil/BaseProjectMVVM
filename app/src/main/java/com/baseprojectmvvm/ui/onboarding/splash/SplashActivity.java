package com.baseprojectmvvm.ui.onboarding.splash;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;

import com.baseprojectmvvm.R;
import com.baseprojectmvvm.base.BaseActivity;
import com.baseprojectmvvm.constant.AppConstants;
import com.baseprojectmvvm.data.DataManager;
import com.baseprojectmvvm.databinding.ActivitySplashBinding;
import com.baseprojectmvvm.ui.home.HomeActivity;
import com.baseprojectmvvm.ui.onboarding.OnBoardActivity;


public class SplashActivity extends BaseActivity<ActivitySplashBinding> {

    private SplashViewModel mSplashViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSplashViewModel = ViewModelProviders.of(this).get(SplashViewModel.class);
        initObservers();
        mSplashViewModel.showSplashScreen();

    }

    private void initObservers() {

        //observing showProgressBar live data
        mSplashViewModel.getSplashLiveData().observe(this, nextScreenType -> {

            switch (nextScreenType) {
                case AppConstants.SplashConstants.OPEN_HOME_SCREEN:
                    openHomeActivity();
                    break;
                case AppConstants.SplashConstants.OPEN_ON_BOARDING_SCREEN:
                    openOnboardActivity();
                    break;
            }
        });
    }

    private void openOnboardActivity() {
        startActivity(new Intent(this, OnBoardActivity.class));
        finishAfterTransition();
    }

    private void openHomeActivity() {
        startActivity(new Intent(this, HomeActivity.class));
        finishAfterTransition();
    }
}
