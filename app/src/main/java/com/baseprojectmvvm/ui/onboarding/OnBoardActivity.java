package com.baseprojectmvvm.ui.onboarding;

import android.content.Intent;
import android.os.Bundle;

import com.baseprojectmvvm.R;
import com.baseprojectmvvm.base.BaseActivity;
import com.baseprojectmvvm.ui.home.HomeActivity;
import com.baseprojectmvvm.ui.onboarding.login.LoginFragment;
import com.baseprojectmvvm.ui.onboarding.signup.SignUpFragment;

public class OnBoardActivity extends BaseActivity implements LoginFragment.ILoginHost, SignUpFragment.ISignUpHost {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);
        showInitialFragment();
    }

    /**
     * Method to show initial fragment
     */
    private void showInitialFragment() {
        addFragment(R.id.frame_container, LoginFragment.getInstance(), LoginFragment.class.getSimpleName());
    }

    @Override
    public void openSignUpFragment() {
        addFragmentWithBackstack(R.id.frame_container, SignUpFragment.getInstance(),
                SignUpFragment.class.getSimpleName());
    }

    @Override
    public void openHomeActivity() {
        startActivity(new Intent(this, HomeActivity.class));
        finishAfterTransition();
    }
}
