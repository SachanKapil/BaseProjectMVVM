package com.baseprojectmvvm.ui.onboarding.login;

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

public class LoginViewModel extends ViewModel {

    private LoginRepo mLoginRepo = new LoginRepo();

    private MutableLiveData<FailureResponse> validateLiveData = new MutableLiveData<>();

    private MutableLiveData<User> loginLiveData = new MutableLiveData<>();
    private LiveData<Event<WrappedResponse<User>>> loginResponseLiveData
            = Transformations.switchMap(loginLiveData, request -> mLoginRepo.hitLoginApi(request));


    /**
     * This method gives the login live data object to {@link LoginFragment}
     *
     * @return {@link #validateLiveData}
     */
    LiveData<Event<WrappedResponse<User>>> getLoginLiveData() {
        return loginResponseLiveData;
    }

    /**
     * This method gives the validation live data object to {@link LoginFragment}
     *
     * @return {@link #validateLiveData}
     */
    MutableLiveData<FailureResponse> getValidationLiveData() {
        return validateLiveData;
    }

    /**
     * Method used to hit login api after checking validations
     *
     * @param user contains all the params of the request
     */
    void loginButtonClicked(User user) {
        if (checkValidation(user)) {
            loginLiveData.setValue(user);
        }
    }

    /**
     * Method to check validation
     */
    private boolean checkValidation(User user) {
        if (user.getEmail().isEmpty()) {
            validateLiveData.setValue(new FailureResponse(
                    AppConstants.UIVALIDATIONS.EMAIL_EMPTY, ResourceUtil.getInstance()
                    .getString(R.string.message_enter_email)));
            return false;

        } else if (!Patterns.EMAIL_ADDRESS.matcher(user.getEmail()).matches()) {
            validateLiveData.setValue(new FailureResponse(
                    AppConstants.UIVALIDATIONS.INVALID_EMAIL, ResourceUtil.getInstance()
                    .getString(R.string.message_enter_valid_email)));
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
        }
        return true;
    }

}
