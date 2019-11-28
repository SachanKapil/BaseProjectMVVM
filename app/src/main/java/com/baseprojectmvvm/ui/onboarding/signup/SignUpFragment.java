package com.baseprojectmvvm.ui.onboarding.signup;


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
import com.baseprojectmvvm.databinding.FragmentSignUpBinding;
import com.baseprojectmvvm.ui.onboarding.OnBoardActivity;


public class SignUpFragment extends BaseFragment<FragmentSignUpBinding> {

    private FragmentSignUpBinding mBinding;
    private SignUpViewModel mSignUpViewModel;
    private ISignUpHost mSignUpHost;


    public static SignUpFragment getInstance() {
        return new SignUpFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_sign_up;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ISignUpHost) {
            mSignUpHost = (ISignUpHost) context;
        } else
            throw new IllegalStateException("Host must implement ISignUpHost");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DataManager.getInstance().saveDeviceId(getDeviceId());
        mSignUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);
        mBinding = getViewDataBinding();
        mBinding.setViewModel(mSignUpViewModel);
        initObservers();
    }


    private void initObservers() {

        //observing showProgressBar live data
        mSignUpViewModel.getShowProgressBarLiveData().observe(this, isStart -> {
            if (isStart) {
                showProgressDialog();
            } else {
                hideProgressDialog();
            }
        });

        //observing login live data
        mSignUpViewModel.getSignUpLiveData().observe(getViewLifecycleOwner(), wrappedResponseEvent -> {
            if (wrappedResponseEvent != null && !wrappedResponseEvent.isAlreadyHandled()) {
                hideProgressDialog();
                WrappedResponse<User> objectWrappedResponse = wrappedResponseEvent.getContent();
                if (objectWrappedResponse.getFailureResponse() != null) {
                    onFailure(objectWrappedResponse.getFailureResponse());
                } else {
                    User user = objectWrappedResponse.getData();
                    showToastLong(getString(R.string.message_login_success));
                    mSignUpHost.openHomeActivity();
                }
            }
        });

        //observing validation live data
        mSignUpViewModel.getValidationLiveData().observe(this, new Observer<FailureResponse>() {
            @Override
            public void onChanged(@Nullable FailureResponse failureResponse) {
                hideProgressDialog();
                if (failureResponse != null)
                    showSnackBar(failureResponse.getErrorMessage());
                //You can also handle validations differently on the basis of the codes here
                /*switch (failureResponse.getErrorCode()){
                    case AppConstants.UIVALIDATIONS.EMAIL_EMPTY:
                        showToastLong(failureResponse.getErrorMessage());
                        break;
                }*/
            }
        });
    }

    /**
     * This interface is used to interact with the host {@link OnBoardActivity}
     */
    public interface ISignUpHost {

        void openHomeActivity();
    }
}
