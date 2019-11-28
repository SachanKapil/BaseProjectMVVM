package com.baseprojectmvvm.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.baseprojectmvvm.R;
import com.baseprojectmvvm.constant.AppConstants;
import com.baseprojectmvvm.data.model.FailureResponse;


public abstract class BaseFragment<MyDataBinding extends ViewDataBinding> extends Fragment {

    private MyDataBinding mViewDataBinding;
    private View mRootView;
    private BaseActivity mActivity;

    @LayoutRes
    public abstract int getLayoutId();


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            this.mActivity = (BaseActivity) context;
        }
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        mRootView = mViewDataBinding.getRoot();
        return mRootView;
    }

    public MyDataBinding getViewDataBinding() {
        return mViewDataBinding;
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
        if (mActivity != null) {
            mActivity.showToastLong(message);
        }
    }

    public void showToastShort(CharSequence message) {
        if (mActivity != null) {
            mActivity.showToastShort(message);
        }
    }

    public void showSnackBar(String message) {
        if (mActivity != null) {
            mActivity.showSnackBar(message);
        }
    }

    public void showProgressDialog() {
        if (mActivity != null)
            mActivity.showProgressDialog();
    }

    public void hideProgressDialog() {
        if (mActivity != null)
            mActivity.hideProgressDialog();
    }

    public void showUnknownRetrofitError() {
        if (mActivity != null) {
            mActivity.showUnknownRetrofitError();
        }
    }

    public void showNoNetworkError() {
        if (mActivity != null) {
            mActivity.showNoNetworkError();
        }
    }

    public String getDeviceId() {
        if (mActivity != null) {
            return mActivity.getDeviceId();
        } else {
            return "";
        }
    }

    public void hideKeyboard() {
        if (mActivity != null) {
            mActivity.hideKeyboard();
        }
    }

    public void showKeyboard() {
        if (mActivity != null) {
            mActivity.showKeyboard();
        }
    }

    public void popFragment() {
        if (mActivity != null) {
            mActivity.popFragment();
        }
    }

    @Override
    public void onStop() {
        hideProgressDialog();
        super.onStop();
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

}
