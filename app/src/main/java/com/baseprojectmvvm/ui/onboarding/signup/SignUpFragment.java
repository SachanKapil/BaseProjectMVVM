package com.baseprojectmvvm.ui.onboarding.signup;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
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
public class SignUpFragment extends BaseFragment {


    @BindView(R.id.iv_image)
    AppCompatImageView ivImage;
    @BindView(R.id.et_name)
    AppCompatEditText etFname;
    @BindView(R.id.et_email)
    AppCompatEditText etEmail;
    @BindView(R.id.et_password)
    AppCompatEditText etPassword;
    @BindView(R.id.et_phone)
    AppCompatEditText etPhone;
    private Unbinder unbinder;


    /**
     * A {@link SignUpViewModel} object to handle all the actions and business logic of sign up
     */
    private SignUpViewModel mSignUpViewModel;

    /**
     * A {@link ISignUpHost} object to interact with the host{@link OnBoardActivity}
     * if any action has to be performed from the host.
     */
    private ISignUpHost mSignUpHost;

    /**
     * This method is used to get the instance of this fragment
     *
     * @return new instance of {@link SignUpFragment}
     */
    public static SignUpFragment getInstance() {
        return new SignUpFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ISignUpHost) {
            mSignUpHost = (ISignUpHost) context;
        } else
            throw new IllegalStateException("Host must implement ISignUpHost");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSignUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);

        initObservers();
    }


    private void initObservers() {

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

    @OnClick(R.id.btn_signup)
    public void onViewClicked() {
        showProgressDialog();
        mSignUpViewModel.signUpButtonClick(new User(etFname.getText().toString().trim(),
                etEmail.getText().toString().trim(), etPassword.getText().toString().trim(), etPhone.getText().toString(),
                getDeviceId(), "12345", "1"));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * This interface is used to interact with the host {@link OnBoardActivity}
     */
    public interface ISignUpHost {

        void openHomeActivity();
    }
}
