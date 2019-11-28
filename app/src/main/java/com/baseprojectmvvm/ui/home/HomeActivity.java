package com.baseprojectmvvm.ui.home;

import android.os.Bundle;

import com.baseprojectmvvm.R;
import com.baseprojectmvvm.base.BaseActivity;
import com.baseprojectmvvm.databinding.ActivityHomeBinding;

public class HomeActivity extends BaseActivity<ActivityHomeBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
