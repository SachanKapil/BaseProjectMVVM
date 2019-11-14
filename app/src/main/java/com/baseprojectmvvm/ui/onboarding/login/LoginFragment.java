package com.baseprojectmvvm.ui.onboarding.login;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.baseprojectmvvm.R;
import com.baseprojectmvvm.base.BaseFragment;
import com.baseprojectmvvm.data.model.FailureResponse;
import com.baseprojectmvvm.data.model.WrappedResponse;
import com.baseprojectmvvm.data.model.onboarding.User;
import com.baseprojectmvvm.ui.onboarding.OnBoardActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link BaseFragment} subclass.
 */
public class LoginFragment extends BaseFragment {


    @BindView(R.id.et_email)
    AppCompatEditText etEmail;
    @BindView(R.id.et_password)
    AppCompatEditText etPassword;
    private Unbinder unbinder;

    /**
     * A {@link ILoginHost} object to interact with the host{@link OnBoardActivity}
     * if any action has to be performed from the host.
     */
    private ILoginHost mLoginHost;

    /**
     * A {@link LoginViewModel} object to handle all the actions and business logic of login
     */
    private LoginViewModel mLoginViewModel;

    public static LoginFragment getInstance() {
        return new LoginFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ILoginHost) {
            mLoginHost = (ILoginHost) context;
        } else
            throw new IllegalStateException("host must implement ILoginHost");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        initObservers();
    }

    private void initObservers() {

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

    @OnClick({R.id.bt_login, R.id.bt_sign_up})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                showProgressDialog();
                mLoginViewModel.loginButtonClicked(new User(etEmail.getText().toString().trim(),
                        etPassword.getText().toString().trim(), getDeviceId(), "1", "12345"));
                break;
            case R.id.bt_sign_up:
                mLoginHost.openSignUpFragment();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * This interface is used to interact with the host {@link OnBoardActivity}
     */
    public interface ILoginHost {
        void openSignUpFragment();

        void openHomeActivity();
    }
}
