package com.baseprojectmvvm.base;

import android.content.Context;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.baseprojectmvvm.R;
import com.baseprojectmvvm.constant.AppConstants;
import com.baseprojectmvvm.customview.loadindicator.LoadingDialog;
import com.baseprojectmvvm.data.model.FailureResponse;
import com.baseprojectmvvm.util.AppUtils;
import com.baseprojectmvvm.util.ResourceUtil;

public class BaseActivity extends AppCompatActivity {


    private LoadingDialog mProgressDialog;
    private BaseFragment activeFragment;


    public void addFragment(int layoutResId, BaseFragment fragment, String tag) {
        if (getSupportFragmentManager().findFragmentByTag(tag) == null)
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up,
                            R.anim.slide_in_down, R.anim.slide_out_down)
                    .add(layoutResId, fragment, tag)
                    .commit();
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

    public void addFragmentWithBackstack(int layoutResId, BaseFragment fragment, String tag) {
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

    public void addFragmentWithBackstackWithStateLoss(int layoutResId, BaseFragment fragment, String tag) {
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



    public void replaceFragmentWithBackstack(int layoutResId, BaseFragment fragment, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up,
                        R.anim.slide_in_down, R.anim.slide_out_down)
                .replace(layoutResId, fragment, tag)
                .addToBackStack(tag)
                .commit();
    }

    public void replaceFragmentWithBackstackWithStateLoss(int layoutResId, BaseFragment fragment, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up,
                        R.anim.slide_in_down, R.anim.slide_out_down)
                .replace(layoutResId, fragment, tag)
                .addToBackStack(tag)
                .commitAllowingStateLoss();
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
        showToastShort(failureResponse.getErrorMessage());
        if (failureResponse != null) {
            if (failureResponse.getErrorCode() == AppConstants.NetworkingConstants.UNAUTHORIZED) {
                logout();
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

    /**
     * Method used to get unique device id
     */
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

    public void doLogout() {
//        PrefManager.getInstance().clearAllPrefsExceptTutorials();
//        startActivity(new Intent(this, OnBoardActivity.class).putExtra("from", "home"));
//        finish();
        instaClearData();
    }

    private void instaClearData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().removeAllCookies(null);
        } else {
            CookieManager.getInstance().removeAllCookie();
        }
    }


    /**
     * hides keyboard onClick anywhere besides edit text
     *
     * @param ev
     * @return
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
