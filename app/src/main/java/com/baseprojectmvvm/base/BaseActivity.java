package com.baseprojectmvvm.base;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.baseprojectmvvm.R;
import com.baseprojectmvvm.constant.AppConstants;
import com.baseprojectmvvm.customview.loadindicator.LoadingDialog;
import com.baseprojectmvvm.data.model.FailureResponse;
import com.baseprojectmvvm.util.AppUtils;
import com.baseprojectmvvm.util.ResourceUtil;

public abstract class BaseActivity<MyDataBinding extends ViewDataBinding> extends AppCompatActivity {


    private LoadingDialog mProgressDialog;
    private MyDataBinding mViewDataBinding;

    @LayoutRes
    public abstract int getLayoutId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performDataBinding();
    }

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
    }

    public MyDataBinding getViewDataBinding() {
        return mViewDataBinding;
    }


    public void addFragment(int layoutResId, BaseFragment fragment, String tag) {
        if (getSupportFragmentManager().findFragmentByTag(tag) == null)
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up,
                            R.anim.slide_in_down, R.anim.slide_out_down)
                    .add(layoutResId, fragment, tag)
                    .commit();
    }

    public void addFragmentWithBackStack(int layoutResId, BaseFragment fragment, String tag) {
        if (getSupportFragmentManager().findFragmentByTag(tag) == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up,
                            R.anim.slide_in_down, R.anim.slide_out_down)
                    .add(layoutResId, fragment, tag)
                    .addToBackStack(tag)
                    .commit();
        }
    }

    public void addFragmentWithStateLoss(int layoutResId, BaseFragment fragment, String tag) {
        if (getSupportFragmentManager().findFragmentByTag(tag) == null)
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up,
                            R.anim.slide_in_down, R.anim.slide_out_down)
                    .add(layoutResId, fragment, tag)
                    .commitAllowingStateLoss();
    }

    public void addFragmentWithBackStackWithStateLoss(int layoutResId, BaseFragment fragment, String tag) {
        if (getSupportFragmentManager().findFragmentByTag(tag) == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up,
                            R.anim.slide_in_down, R.anim.slide_out_down)
                    .add(layoutResId, fragment, tag)
                    .addToBackStack(tag)
                    .commitAllowingStateLoss();
        }
    }

    public void replaceFragment(int layoutResId, BaseFragment fragment, String tag) {
        if (getSupportFragmentManager().findFragmentByTag(tag) == null)
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up,
                            R.anim.slide_in_down, R.anim.slide_out_down)
                    .replace(layoutResId, fragment, tag)
                    .commit();
    }


    public void replaceFragmentWithBackStack(int layoutResId, BaseFragment fragment, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up,
                        R.anim.slide_in_down, R.anim.slide_out_down)
                .replace(layoutResId, fragment, tag)
                .addToBackStack(tag)
                .commit();
    }

    public void replaceFragmentWithBackStackWithStateLoss(int layoutResId, BaseFragment fragment, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up,
                        R.anim.slide_in_down, R.anim.slide_out_down)
                .replace(layoutResId, fragment, tag)
                .addToBackStack(tag)
                .commitAllowingStateLoss();
    }

    private Fragment getCurrentFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
            Fragment currentFragment = fragmentManager.findFragmentByTag(fragmentTag);
            return currentFragment;
        } else
            return null;
    }

//    public void changeFragment(int layoutResId, BaseFragment myFragment, String tag) {
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentByTag(tag);
//        if (fragment != null) {
//            fragmentTransaction.show(fragment);
//        } else {
//            fragment = myFragment;
//            fragmentTransaction
//                    .add(layoutResId, fragment, tag);
//        }
//
//        if (activeFragment != null && activeFragment != fragment) {
//            fragmentTransaction.hide(activeFragment);
//        }
//        activeFragment = fragment;
//
//        // Set fragment as primary navigator for child manager back stack to be handled by system
//        fragmentTransaction.setPrimaryNavigationFragment(fragment);
//        fragmentTransaction.setReorderingAllowed(true);
//        fragmentTransaction.commit();
//    }


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
//        finish();
    }

    public void showToastLong(CharSequence message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void showToastShort(CharSequence message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showSnackBar(String message) {
        AppUtils.showSnackBar(mViewDataBinding.getRoot(), message);
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new LoadingDialog(this);
        }

        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    public String getDeviceId() {
        return AppUtils.getUniqueDeviceId(this);
    }


    public void showUnknownRetrofitError() {
        hideProgressDialog();
        showToastLong(ResourceUtil.getInstance().getString(R.string.message_something_went_wrong));
    }

    public void showNoNetworkError() {
        hideProgressDialog();
        showToastLong(ResourceUtil.getInstance().getString(R.string.message_no_internet));
    }

    public void hideKeyboard() {
        AppUtils.hideKeyboard(this);
    }

    public void showKeyboard() {
        AppUtils.showKeyboard(this);
    }

    public void popFragment() {
        if (getSupportFragmentManager() != null) {
            getSupportFragmentManager().popBackStackImmediate();
        }
    }


    /**
     * hides keyboard onClick anywhere besides edit text
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}
