package com.baseprojectmvvm.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.baseprojectmvvm.data.model.Event;
import com.baseprojectmvvm.data.model.WrappedResponse;

public class HomeViewModel extends ViewModel {

    private HomeRepo repo = new HomeRepo();

    private MutableLiveData<Boolean> showProgressBarLiveData = new MutableLiveData<>();

    private MutableLiveData<Object> logoutLiveData = new MutableLiveData<>();
    private LiveData<Event<WrappedResponse<Object>>> logoutResponseLiveData
            = Transformations.switchMap(logoutLiveData, request -> repo.hitLogOutApi());

    // This method gives the show progressbar live data object
    MutableLiveData<Boolean> getShowProgressBarLiveData() {
        return showProgressBarLiveData;
    }


    // This method gives the login live data object
    LiveData<Event<WrappedResponse<Object>>> getLogoutLiveData() {
        return logoutResponseLiveData;
    }

    public void logout() {
        logoutLiveData.setValue(new Object());
    }
}
