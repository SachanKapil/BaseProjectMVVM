package com.baseprojectmvvm.ui.home;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;

import com.baseprojectmvvm.R;
import com.baseprojectmvvm.base.BaseActivity;
import com.baseprojectmvvm.data.model.WrappedResponse;
import com.baseprojectmvvm.databinding.ActivityHomeBinding;

public class HomeActivity extends BaseActivity<ActivityHomeBinding> {

    private ActivityHomeBinding binding;
    private HomeViewModel homeViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
        binding = getViewDataBinding();
        binding.setViewModel(homeViewModel);
        initObservers();
    }

    private void initViewModel() {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
    }

    private void initObservers() {

        //observing showProgressBar live data
        homeViewModel.getShowProgressBarLiveData().observe(this, isStart -> {
            if (isStart) {
                showProgressDialog();
            } else {
                hideProgressDialog();
            }
        });

        //observing logout live data
        homeViewModel.getLogoutLiveData().observe(this, wrappedResponseEvent -> {
            if (wrappedResponseEvent != null && !wrappedResponseEvent.isAlreadyHandled()) {
                hideProgressDialog();
                WrappedResponse<Object> objectWrappedResponse = wrappedResponseEvent.getContent();
                if (objectWrappedResponse.getFailureResponse() != null) {
                    onFailure(objectWrappedResponse.getFailureResponse());
                } else {
                    logout();
                }
            }
        });
    }
}
