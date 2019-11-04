package com.baseprojectmvvm.permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import com.baseprojectmvvm.R;
import com.baseprojectmvvm.constant.AppConstants;
import com.baseprojectmvvm.util.AppUtils;

import java.util.Objects;

public class PermissionHelper {

    /**
     * A {@link Activity} object to get the context of the Activity from where it is called
     */
    private Activity mActivity;

    /**
     * A {@link IGetPermissionListener} object to sends call back to the Activity which implements it
     */
    private IGetPermissionListener mGetPermissionListener;

    public PermissionHelper(IGetPermissionListener getPermissionListener) {
        this.mGetPermissionListener = getPermissionListener;
    }

    /**
     * Method to check any permission. It will return true if permission granted
     * otherwise false
     *
     * @param requestCode is the code given for which permission is to be taken
     * @param context     of the activity
     * @param permissions that we want to take from the user
     * @return true if permission granted otherwise false
     */
    public boolean hasPermission(Activity context, String[] permissions, int requestCode) {
        mActivity = context;
        boolean isAllGranted = true;
        //check for all devices
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return true;
        } else {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                }
            }
            //check if all granted or not
            if (!isAllGranted) {
                ActivityCompat.requestPermissions(context, permissions, requestCode);
                return false;
            } else
                return true;
        }
    }

    /**
     * Method to check any permission. It will return true if permission granted
     * otherwise false
     *
     * @param requestCode is the code given for which permission is to be taken
     * @param fragment    of the activity
     * @param permissions that we want to take from the user
     * @return true if permission granted otherwise false
     */
    public boolean hasPermission(@NonNull Fragment fragment, String[] permissions, int requestCode) {
        boolean isAllGranted = true;
        mActivity = fragment.getActivity();
        //check for all devices
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return true;
        } else {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(Objects.requireNonNull(fragment.getContext()), permission) != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                }
            }
            //check if all granted or not
            if (!isAllGranted) {
                fragment.requestPermissions(permissions, requestCode);
                return false;
            } else
                return true;
        }
    }


    /**
     * This method is used to set the result when {@link ActivityCompat.OnRequestPermissionsResultCallback}
     * gets the callback of the permission
     *
     * @param requestCode  code at which particular permission was asked
     * @param permissions  name of the permission taken from the user
     * @param grantResults gives the result whether user grants or denies the permission
     */
    public void setPermissionResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case AppConstants.PermissionConstants.REQ_CODE_GALLERY:
                //If user denies the READ external storage permission with don't ask again , then this variable will be true
                boolean isStorage = !ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permissions[0]) &&
                        ContextCompat.checkSelfPermission(mActivity, permissions[0]) != PackageManager.PERMISSION_GRANTED;
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //if permission granted
                    mGetPermissionListener.permissionGiven(AppConstants.PermissionConstants.REQ_CODE_GALLERY);
                } else if (isStorage) {
                    //Open settings if isStorage is true
                    AppUtils.showAllowPermissionFromSettingDialog(mActivity, mActivity.getString(R.string.enable_storage_permission));
                }
                break;

            case AppConstants.PermissionConstants.REQ_CODE_CAMERA:
                //If user denies the CAMERA permission with don't ask again checkbox,then this variable will be true
                boolean isRationalCamera = !ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permissions[0]) &&
                        ContextCompat.checkSelfPermission(mActivity, permissions[0]) != PackageManager.PERMISSION_GRANTED;
                //If user denies the WRITE permission with don't ask again checkbox,then this variable will be true
                boolean isRationalStorage = !ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permissions[1]) &&
                        ContextCompat.checkSelfPermission(mActivity, permissions[1]) != PackageManager.PERMISSION_GRANTED;

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    //if permission granted
                    mGetPermissionListener.permissionGiven(AppConstants.PermissionConstants.REQ_CODE_CAMERA);

                } else if (isRationalCamera) {
                    AppUtils.showAllowPermissionFromSettingDialog(mActivity, mActivity.getString(R.string.enable_camera_permission));
                } else if (isRationalStorage) {
                    AppUtils.showAllowPermissionFromSettingDialog(mActivity, mActivity.getString(R.string.enable_storage_permission));
                }
                break;

            case AppConstants.PermissionConstants.REQ_CODE_CAMERA_VIDEO:
                boolean isCamera = !ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permissions[0]) &&
                        ContextCompat.checkSelfPermission(mActivity, permissions[0]) != PackageManager.PERMISSION_GRANTED;
                boolean isExternalStorage = !ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permissions[2]) &&
                        ContextCompat.checkSelfPermission(mActivity, permissions[2]) != PackageManager.PERMISSION_GRANTED;
                boolean isRecordAudio = !ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permissions[3]) &&
                        ContextCompat.checkSelfPermission(mActivity, permissions[3]) != PackageManager.PERMISSION_GRANTED;
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED && grantResults[3] == PackageManager.PERMISSION_GRANTED) {
                    mGetPermissionListener.permissionGiven(AppConstants.PermissionConstants.REQ_CODE_CAMERA_VIDEO);
                } else if (isCamera) {
                    AppUtils.showAllowPermissionFromSettingDialog(mActivity, mActivity.getString(R.string.enable_camera_permission));
                } else if (isExternalStorage) {
                    AppUtils.showAllowPermissionFromSettingDialog(mActivity, mActivity.getString(R.string.enable_storage_permission));
                } else if (isRecordAudio) {
                    AppUtils.showAllowPermissionFromSettingDialog(mActivity, mActivity.getString(R.string.enable_audio_permission));
                }

                break;
        }
    }


    /**
     * This interface is used to get the user permission callback to the activity or fragment who
     * implements it
     */
    public interface IGetPermissionListener {

        void permissionGiven(int requestCode);

        void permissionDenied();
    }

}
