package com.baseprojectmvvm.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.baseprojectmvvm.R;
import com.baseprojectmvvm.constant.AppConstants;
import com.baseprojectmvvm.data.model.FailureResponse;


public class BaseFragment extends Fragment {


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        if (view != null) {
            ViewCompat.requestApplyInsets(view);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onFailure(FailureResponse failureResponse) {
        if (failureResponse != null) {
            if (failureResponse.getErrorCode() == AppConstants.NetworkingConstants.UNAUTHORIZED) {
                showToastShort(getString(R.string.message_session_expired));
                logout();
            } else if (failureResponse.getErrorCode() == AppConstants.NetworkingConstants.EMPTY_DATA_ERROR_CODE) {
                showToastShort(getString(R.string.message_no_data_found));
            } else if (failureResponse.getErrorCode() == AppConstants.NetworkingConstants.ACCOUNT_BLOCKED_CODE) {
                showToastShort(getString(R.string.message_account_blocked));
                logout();
            } else {
                showToastShort(failureResponse.getErrorMessage());
            }
        }
    }

    public void logout() {
//        DataManager.getInstance().clearPreferences();
//        Intent intent = new Intent(App.getAppContext(), WelcomeActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//        getActivity().finish();
    }

    public void showToastLong(CharSequence message) {
        ((BaseActivity) getActivity()).showToastLong(message);
    }

    public void showToastShort(CharSequence message) {
        ((BaseActivity) getActivity()).showToastShort(message);
    }

    public void showProgressDialog() {
        if (getActivity() != null)
            ((BaseActivity) getActivity()).showProgressDialog();
    }

    public void hideProgressDialog() {
        if (getActivity() != null)
            ((BaseActivity) getActivity()).hideProgressDialog();
    }

    public void showUnknownRetrofitError() {
        ((BaseActivity) getActivity()).showUnknownRetrofitError();
    }

    public String getDeviceId() {
        return ((BaseActivity) getActivity()).getDeviceId();
    }

    public void hideKeyboard() {
        ((BaseActivity) getActivity()).hideKeyboard();
    }

    public void showKeyboard() {
        ((BaseActivity) getActivity()).showKeyboard();
    }

    public void showNoNetworkError() {
        ((BaseActivity) getActivity()).showNoNetworkError();
    }

    public void popFragment() {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).popFragment();
        }
    }

    @Override
    public void onStop() {
        hideProgressDialog();
        super.onStop();
    }

}
