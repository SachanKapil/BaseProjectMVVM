package com.baseprojectmvvm.ui.onboarding.login;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.baseprojectmvvm.R;
import com.baseprojectmvvm.base.BaseFragment;
import com.baseprojectmvvm.data.model.FailureResponse;
import com.baseprojectmvvm.data.model.WrappedResponse;
import com.baseprojectmvvm.data.model.onboarding.User;
import com.baseprojectmvvm.databinding.FragmentLoginBinding;

public class LoginFragment extends BaseFragment {

    private FragmentLoginBinding mBinding;
    private ILoginHost mLoginHost;
    private LoginViewModel mLoginViewModel;

    public static LoginFragment getInstance() {
        return new LoginFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ILoginHost) {
            mLoginHost = (ILoginHost) context;
        } else
            throw new IllegalStateException("host must implement ILoginHost");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        mBinding = (FragmentLoginBinding) getViewDataBinding();
        mBinding.setViewModel(mLoginViewModel);
        mBinding.setLoginFragment(this);
        initObservers();
    }

    private void initObservers() {

        //observing showProgressBar live data
        mLoginViewModel.getShowProgressBarLiveData().observe(this, isStart -> {
            if (isStart) {
                showProgressDialog();
            } else {
                hideProgressDialog();
            }
        });


        //observing login live data
        mLoginViewModel.getLoginLiveData().observe(getViewLifecycleOwner(), wrappedResponseEvent -> {
            if (wrappedResponseEvent != null && !wrappedResponseEvent.isAlreadyHandled()) {
                hideProgressDialog();
                WrappedResponse<User> objectWrappedResponse = wrappedResponseEvent.getContent();
                if (objectWrappedResponse.getFailureResponse() != null) {
                    onFailure(objectWrappedResponse.getFailureResponse());
                } else {
                    User user = objectWrappedResponse.getData();
                    showToastLong(getString(R.string.message_login_success));
                    mLoginHost.openHomeActivity();
                }
            }
        });

        //observing validation live data
        mLoginViewModel.getValidationLiveData().observe(this, new Observer<FailureResponse>() {
            @Override
            public void onChanged(@Nullable FailureResponse failureResponse) {
                hideProgressDialog();
                if (failureResponse != null)
                    showToastLong(failureResponse.getErrorMessage());
                //You can also handle validations differently on the basis of the codes here
                /*switch (failureResponse.getErrorCode()){
                    case AppConstants.UIVALIDATIONS.EMAIL_EMPTY:
                        showToastLong(failureResponse.getErrorMessage());
                        break;
                }*/
            }
        });
    }

    public void openSignUpFragment() {
        mLoginHost.openSignUpFragment();
    }

    public interface ILoginHost {
        void openSignUpFragment();

        void openHomeActivity();
    }
}
