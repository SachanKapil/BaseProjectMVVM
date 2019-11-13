package com.baseprojectmvvm.ui.onboarding.signup;


import androidx.lifecycle.MutableLiveData;

import com.baseprojectmvvm.base.NetworkCallback;
import com.baseprojectmvvm.data.DataManager;
import com.baseprojectmvvm.data.model.Event;
import com.baseprojectmvvm.data.model.FailureResponse;
import com.baseprojectmvvm.data.model.WrappedResponse;
import com.baseprojectmvvm.data.model.onboarding.User;

public class SignUpRepo {

    /**
     * hit api for events listing
     */
    MutableLiveData<Event<WrappedResponse<User>>> hitSignUpApi(User user) {

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
            DataManager.getInstance().saveAccessToken(user.getAccesstoken());
            DataManager.getInstance().saveRefreshToken(user.getRefreshToken());
            DataManager.getInstance().saveUserDetails(user);
        }
    }
}
