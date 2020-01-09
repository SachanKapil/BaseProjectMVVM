package com.baseprojectmvvm.ui.home;

import androidx.lifecycle.MutableLiveData;

import com.baseprojectmvvm.base.NetworkCallback;
import com.baseprojectmvvm.data.DataManager;
import com.baseprojectmvvm.data.model.Event;
import com.baseprojectmvvm.data.model.FailureResponse;
import com.baseprojectmvvm.data.model.WrappedResponse;

class HomeRepo {

    /**
     * hit api for log out
     */
    MutableLiveData<Event<WrappedResponse<Object>>> hitLogOutApi() {

        final MutableLiveData<Event<WrappedResponse<Object>>> logoutRequestLiveData = new MutableLiveData<>();
        final WrappedResponse<Object> wrappedResponse = new WrappedResponse<>();

        DataManager.getInstance().hitLogOutApi().enqueue(new NetworkCallback<Object>() {
            @Override
            public void onSuccess(Object o) {
                wrappedResponse.setData(o);
                logoutRequestLiveData.setValue(new Event<>(wrappedResponse));
            }

            @Override
            public void onFailure(FailureResponse failureResponse) {
                wrappedResponse.setFailureResponse(failureResponse);
                logoutRequestLiveData.setValue(new Event<>(wrappedResponse));
            }

            @Override
            public void onError(Throwable t) {
                wrappedResponse.setFailureResponse(FailureResponse.getGenericError());
                logoutRequestLiveData.setValue(new Event<>(wrappedResponse));
            }
        });

        return logoutRequestLiveData;
    }
}
