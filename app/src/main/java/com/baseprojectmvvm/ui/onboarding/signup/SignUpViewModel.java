package com.baseprojectmvvm.ui.onboarding.signup;


import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.baseprojectmvvm.R;
import com.baseprojectmvvm.constant.AppConstants;
import com.baseprojectmvvm.data.model.Event;
import com.baseprojectmvvm.data.model.FailureResponse;
import com.baseprojectmvvm.data.model.WrappedResponse;
import com.baseprojectmvvm.data.model.onboarding.User;
import com.baseprojectmvvm.util.ResourceUtil;

public class SignUpViewModel extends ViewModel {

    private SignUpRepo signUpRepo = new SignUpRepo();

    private MutableLiveData<FailureResponse> validateLiveData = new MutableLiveData<>();

    private MutableLiveData<User> signUpLiveData = new MutableLiveData<>();
    private LiveData<Event<WrappedResponse<User>>> signUpResponseLiveData
            = Transformations.switchMap(signUpLiveData, request -> signUpRepo.hitSignUpApi(request));

    LiveData<Event<WrappedResponse<User>>> getSignUpLiveData() {
        return signUpResponseLiveData;
    }

    /**
     * Method used to hit sign up api after checking validations
     *
     * @param user contains all the params of the request
     */
    void signUpButtonClick(User user) {
        if (checkValidation(user)) {
            signUpLiveData.setValue(user);
        }
    }

    private boolean checkValidation(User user) {
        if (user.getFirstName().isEmpty()) {
            validateLiveData.setValue(new FailureResponse(
                    AppConstants.UIVALIDATIONS.NAME_EMPTY, ResourceUtil.getInstance()
                    .getString(R.string.message_enter_name)
            ));
            return false;
        } else if (!user.getFirstName().matches("[a-z A-Z]*")) {
            validateLiveData.setValue(new FailureResponse(
                    AppConstants.UIVALIDATIONS.INVALID_NAME, ResourceUtil.getInstance()
                    .getString(R.string.message_enter_valid_name)
            ));
            return false;
        } else if (user.getEmail().isEmpty()) {
            validateLiveData.setValue(new FailureResponse(
                    AppConstants.UIVALIDATIONS.EMAIL_EMPTY, ResourceUtil.getInstance()
                    .getString(R.string.message_enter_email)
            ));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(user.getEmail()).matches()) {
            validateLiveData.setValue(new FailureResponse(
                    AppConstants.UIVALIDATIONS.INVALID_EMAIL, ResourceUtil.getInstance()
                    .getString(R.string.message_enter_valid_email)
            ));
            return false;
        } else if (user.getPassword().isEmpty()) {
            validateLiveData.setValue(new FailureResponse(
                    AppConstants.UIVALIDATIONS.PASSWORD_EMPTY, ResourceUtil.getInstance()
                    .getString(R.string.message_enter_password)
            ));
            return false;
        } else if (user.getPassword().length() < 6) {
            validateLiveData.setValue(new FailureResponse(
                    AppConstants.UIVALIDATIONS.INVALID_PASSWORD, ResourceUtil.getInstance()
                    .getString(R.string.message_enter_valid_password)
            ));
            return false;
        } else if (user.getPhone().isEmpty()) {
            validateLiveData.setValue(new FailureResponse(
                    AppConstants.UIVALIDATIONS.PHONE_EMPTY, ResourceUtil.getInstance()
                    .getString(R.string.message_enter_phone)
            ));
            return false;
        } else if (user.getPhone().length() < 10) {
            validateLiveData.setValue(new FailureResponse(
                    AppConstants.UIVALIDATIONS.INVALID_PHONE, ResourceUtil.getInstance()
                    .getString(R.string.message_enter_valid_phone)
            ));
        }
        return true;
    }

    public MutableLiveData<FailureResponse> getValidationLiveData() {
        return validateLiveData;
    }
}
