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
import com.baseprojectmvvm.data.DataManager;
import com.baseprojectmvvm.data.model.FailureResponse;
import com.baseprojectmvvm.data.model.WrappedResponse;
import com.baseprojectmvvm.data.model.onboarding.User;
import com.baseprojectmvvm.databinding.FragmentLoginBinding;

public class LoginFragment extends BaseFragment<FragmentLoginBinding> {

    private FragmentLoginBinding binding;
    private ILoginHost loginHost;
    private LoginViewModel loginViewModel;

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
            loginHost = (ILoginHost) context;
        } else
            throw new IllegalStateException("host must implement ILoginHost");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DataManager.getInstance().saveDeviceId(getDeviceId());
        initViewModel();
        binding = getViewDataBinding();
        binding.setViewModel(loginViewModel);
        binding.setLoginFragment(this);
        initObservers();
    }

    private void initViewModel() {
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
    }

    private void initObservers() {

        //observing showProgressBar live data
        loginViewModel.getShowProgressBarLiveData().observe(this, isStart -> {
            if (isStart) {
                showProgressDialog();
            } else {
                hideProgressDialog();
            }
        });

        //observing login live data
        loginViewModel.getLoginLiveData().observe(getViewLifecycleOwner(), wrappedResponseEvent -> {
            if (wrappedResponseEvent != null && !wrappedResponseEvent.isAlreadyHandled()) {
                hideProgressDialog();
                WrappedResponse<User> objectWrappedResponse = wrappedResponseEvent.getContent();
                if (objectWrappedResponse.getFailureResponse() != null) {
                    onFailure(objectWrappedResponse.getFailureResponse());
                } else {
                    loginHost.openHomeActivity();
                }
            }
        });

        //observing validation live data
        loginViewModel.getValidationLiveData().observe(this, new Observer<FailureResponse>() {
            @Override
            public void onChanged(@Nullable FailureResponse failureResponse) {
                hideProgressDialog();
                if (failureResponse != null)
                    showSnackBar(failureResponse.getErrorMessage());
                //You can also handle validations differently on the basis of the codes here
                /*switch (failureResponse.getErrorCode()){
                    case AppConstants.UiValidationConstants.EMAIL_EMPTY:
                        showToastLong(failureResponse.getErrorMessage());
                        break;
                }*/
            }
        });
    }

    public void openSignUpFragment() {
        loginHost.openSignUpFragment();
    }

    public interface ILoginHost {
        void openSignUpFragment();

        void openHomeActivity();
    }
}
