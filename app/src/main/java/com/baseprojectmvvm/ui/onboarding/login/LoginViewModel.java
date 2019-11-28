package com.baseprojectmvvm.ui.onboarding.login;

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
import com.baseprojectmvvm.util.ResourceUtil;

public class LoginViewModel extends ViewModel {

    public User user = new User();
    private LoginRepo mLoginRepo = new LoginRepo();

    private MutableLiveData<Boolean> showProgressBarLiveData = new MutableLiveData<>();

    private MutableLiveData<FailureResponse> validateLiveData = new MutableLiveData<>();

    private MutableLiveData<User> loginLiveData = new MutableLiveData<>();
    private LiveData<Event<WrappedResponse<User>>> loginResponseLiveData
            = Transformations.switchMap(loginLiveData, request -> mLoginRepo.hitLoginApi(request));

    // This method gives the show progressbar live data object
    MutableLiveData<Boolean> getShowProgressBarLiveData() {
        return showProgressBarLiveData;
    }

    // This method gives the validation live data object
    MutableLiveData<FailureResponse> getValidationLiveData() {
        return validateLiveData;
    }


    // This method gives the login live data object
    LiveData<Event<WrappedResponse<User>>> getLoginLiveData() {
        return loginResponseLiveData;
    }


    // Method used to hit login api after checking validations
    public void doLogIn() {
        showProgressBarLiveData.setValue(true);
        user.setDevice_token(DataManager.getInstance().getDeviceToken());
        user.setDevice_id(DataManager.getInstance().getDeviceId());
        user.setPlatform("1");
        if (checkValidation(user)) {
            loginLiveData.setValue(user);
        }
    }


    //  Method to check validation
    private boolean checkValidation(User user) {
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            validateLiveData.setValue(new FailureResponse(
                    AppConstants.UIVALIDATIONS.EMAIL_EMPTY, ResourceUtil.getInstance()
                    .getString(R.string.message_enter_email)));
            return false;

        } else if (!Patterns.EMAIL_ADDRESS.matcher(user.getEmail().trim()).matches()) {
            validateLiveData.setValue(new FailureResponse(
                    AppConstants.UIVALIDATIONS.INVALID_EMAIL, ResourceUtil.getInstance()
                    .getString(R.string.message_enter_valid_email)));
            return false;

        } else if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            validateLiveData.setValue(new FailureResponse(
                    AppConstants.UIVALIDATIONS.PASSWORD_EMPTY, ResourceUtil.getInstance()
                    .getString(R.string.message_enter_password)
            ));
            return false;

        } else if (user.getPassword().trim().length() < 6) {
            validateLiveData.setValue(new FailureResponse(
                    AppConstants.UIVALIDATIONS.INVALID_PASSWORD, ResourceUtil.getInstance()
                    .getString(R.string.message_enter_valid_password)
            ));
            return false;
        }
        return true;
    }

}
