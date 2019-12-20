package com.baseprojectmvvm.ui.onboarding;

import androidx.lifecycle.MutableLiveData;

import com.baseprojectmvvm.base.NetworkCallback;
import com.baseprojectmvvm.data.DataManager;
import com.baseprojectmvvm.data.model.Event;
import com.baseprojectmvvm.data.model.FailureResponse;
import com.baseprojectmvvm.data.model.WrappedResponse;
import com.baseprojectmvvm.data.model.onboarding.User;

public class OnboardingRepo {

    /**
     * hit api for log in
     */
    public MutableLiveData<Event<WrappedResponse<User>>> hitLoginApi(User user) {

        final MutableLiveData<Event<WrappedResponse<User>>> sendLoginRequestLiveData = new MutableLiveData<>();
        final WrappedResponse<User> wrappedResponse = new WrappedResponse<>();

        DataManager.getInstance().hitLoginApi(user).enqueue(new NetworkCallback<User>() {
            @Override
            public void onSuccess(User o) {
                saveUserToPreference(o);
                wrappedResponse.setData(o);
                sendLoginRequestLiveData.setValue(new Event<>(wrappedResponse));
            }

            @Override
            public void onFailure(FailureResponse failureResponse) {
                wrappedResponse.setFailureResponse(failureResponse);
                sendLoginRequestLiveData.setValue(new Event<>(wrappedResponse));
            }

            @Override
            public void onError(Throwable t) {
                wrappedResponse.setFailureResponse(FailureResponse.getGenericError());
                sendLoginRequestLiveData.setValue(new Event<>(wrappedResponse));
            }
        });

        return sendLoginRequestLiveData;
    }

    /**
     * hit api for sign up
     */
    public MutableLiveData<Event<WrappedResponse<User>>> hitSignUpApi(User user) {

        final MutableLiveData<Event<WrappedResponse<User>>> sendSignUpRequestLiveData = new MutableLiveData<>();
        final WrappedResponse<User> wrappedResponse = new WrappedResponse<>();

        DataManager.getInstance().hitSignUpApi(user).enqueue(new NetworkCallback<User>() {
            @Override
            public void onSuccess(User o) {
                saveUserToPreference(o);
                wrappedResponse.setData(o);
                sendSignUpRequestLiveData.setValue(new Event<>(wrappedResponse));
            }

            @Override
            public void onFailure(FailureResponse failureResponse) {
                wrappedResponse.setFailureResponse(failureResponse);
                sendSignUpRequestLiveData.setValue(new Event<>(wrappedResponse));
            }

            @Override
            public void onError(Throwable t) {
                wrappedResponse.setFailureResponse(FailureResponse.getGenericError());
                sendSignUpRequestLiveData.setValue(new Event<>(wrappedResponse));
            }
        });

        return sendSignUpRequestLiveData;
    }

    private void saveUserToPreference(User user) {
        if (user != null) {
            DataManager.getInstance().saveAccessToken(user.getAccessToken());
            DataManager.getInstance().saveRefreshToken(user.getRefreshToken());
            DataManager.getInstance().saveUserDetails(user);
        }
    }

    public void saveDeviceToken(String deviceToken) {
        //save device token to shared preference using data manager

    }
}
