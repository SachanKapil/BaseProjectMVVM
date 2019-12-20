package com.baseprojectmvvm.ui.onboarding.signup;


import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.baseprojectmvvm.R;
import com.baseprojectmvvm.constant.AppConstants;
import com.baseprojectmvvm.data.DataManager;
import com.baseprojectmvvm.data.model.Event;
import com.baseprojectmvvm.data.model.FailureResponse;
import com.baseprojectmvvm.data.model.WrappedResponse;
import com.baseprojectmvvm.data.model.onboarding.User;
import com.baseprojectmvvm.ui.onboarding.OnboardingRepo;
import com.baseprojectmvvm.util.ResourceUtil;

public class SignUpViewModel extends ViewModel {

    public User user = new User();
    private OnboardingRepo repo = new OnboardingRepo();

    private MutableLiveData<Boolean> showProgressBarLiveData = new MutableLiveData<>();
    private MutableLiveData<FailureResponse> validateLiveData = new MutableLiveData<>();
    private MutableLiveData<User> signUpLiveData = new MutableLiveData<>();
    private LiveData<Event<WrappedResponse<User>>> signUpResponseLiveData
            = Transformations.switchMap(signUpLiveData, request -> repo.hitSignUpApi(request));

    // This method gives the show progressbar live data object
    MutableLiveData<Boolean> getShowProgressBarLiveData() {
        return showProgressBarLiveData;
    }

    // This method gives the validation live data object
    MutableLiveData<FailureResponse> getValidationLiveData() {
        return validateLiveData;
    }

    // This method gives the sign up live data object
    LiveData<Event<WrappedResponse<User>>> getSignUpLiveData() {
        return signUpResponseLiveData;
    }


    //  Method used to hit sign up api after checking validations
    public void doSignUp() {
        showProgressBarLiveData.setValue(true);
        user.setDevice_token(DataManager.getInstance().getDeviceToken());
        user.setDevice_id(DataManager.getInstance().getDeviceId());
        user.setPlatform("1");
        if (checkValidation(user)) {
            signUpLiveData.setValue(user);
        }
    }

    private boolean checkValidation(User user) {
        if (user.getFirstName() == null || user.getFirstName().trim().isEmpty()) {
            validateLiveData.setValue(new FailureResponse(
                    AppConstants.UiValidationConstants.NAME_EMPTY, ResourceUtil.getInstance()
                    .getString(R.string.message_enter_name)
            ));
            return false;
        } else if (!user.getFirstName().trim().matches("[a-z A-Z]*")) {
            validateLiveData.setValue(new FailureResponse(
                    AppConstants.UiValidationConstants.INVALID_NAME, ResourceUtil.getInstance()
                    .getString(R.string.message_enter_valid_name)
            ));
            return false;
        } else if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            validateLiveData.setValue(new FailureResponse(
                    AppConstants.UiValidationConstants.EMAIL_EMPTY, ResourceUtil.getInstance()
                    .getString(R.string.message_enter_email)
            ));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(user.getEmail().trim()).matches()) {
            validateLiveData.setValue(new FailureResponse(
                    AppConstants.UiValidationConstants.INVALID_EMAIL, ResourceUtil.getInstance()
                    .getString(R.string.message_enter_valid_email)
            ));
            return false;
        } else if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            validateLiveData.setValue(new FailureResponse(
                    AppConstants.UiValidationConstants.PASSWORD_EMPTY, ResourceUtil.getInstance()
                    .getString(R.string.message_enter_password)
            ));
            return false;
        } else if (user.getPassword().trim().length() < 6) {
            validateLiveData.setValue(new FailureResponse(
                    AppConstants.UiValidationConstants.INVALID_PASSWORD, ResourceUtil.getInstance()
                    .getString(R.string.message_enter_valid_password)
            ));
            return false;
        } else if (user.getPhone() == null || user.getPhone().trim().isEmpty()) {
            validateLiveData.setValue(new FailureResponse(
                    AppConstants.UiValidationConstants.PHONE_EMPTY, ResourceUtil.getInstance()
                    .getString(R.string.message_enter_phone)
            ));
            return false;
        } else if (user.getPhone().trim().length() < 10) {
            validateLiveData.setValue(new FailureResponse(
                    AppConstants.UiValidationConstants.INVALID_PHONE, ResourceUtil.getInstance()
                    .getString(R.string.message_enter_valid_phone)
            ));
        }
        return true;
    }
}
