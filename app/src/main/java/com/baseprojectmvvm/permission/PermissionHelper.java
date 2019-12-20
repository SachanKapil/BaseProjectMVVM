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
                boolean isGalleryStorage = shouldShowRequest(permissions[0]);

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //if permission granted
                    mGetPermissionListener.permissionGiven(AppConstants.PermissionConstants.REQ_CODE_GALLERY);
                } else if (isGalleryStorage) {
                    AppUtils.showAllowPermissionFromSettingDialog(mActivity, mActivity.getString(R.string.enable_storage_permission));
                }
                break;

            case AppConstants.PermissionConstants.REQ_CODE_CAMERA:

                //If user denies the CAMERA permission with don't ask again checkbox,then this variable will be true
                boolean isCamera = shouldShowRequest(permissions[0]);
                //If user denies the WRITE permission with don't ask again checkbox,then this variable will be true
                boolean isStorage = shouldShowRequest(permissions[1]);

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    //if permission granted
                    mGetPermissionListener.permissionGiven(AppConstants.PermissionConstants.REQ_CODE_CAMERA);

                } else if (isCamera && isStorage) {
                    AppUtils.showAllowPermissionFromSettingDialog(mActivity, mActivity.getString(R.string.enable_storage_camera_permission));
                } else if (isCamera) {
                    AppUtils.showAllowPermissionFromSettingDialog(mActivity, mActivity.getString(R.string.enable_camera_permission));
                } else if (isStorage) {
                    AppUtils.showAllowPermissionFromSettingDialog(mActivity, mActivity.getString(R.string.enable_storage_permission));
                }
                break;

            case AppConstants.PermissionConstants.REQ_CODE_CAMERA_GALLERY:

                //If user denies the READ external storage permission with don't ask again , then this variable will be true
                boolean isReadAllow = shouldShowRequest(permissions[0]);

                //If user denies the CAMERA permission with don't ask again checkbox,then this variable will be true
                boolean isCameraAllow = shouldShowRequest(permissions[1]);

                //If user denies the WRITE permission with don't ask again checkbox,then this variable will be true
                boolean isWriteAllow = shouldShowRequest(permissions[2]);

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    //if permission granted
                    mGetPermissionListener.permissionGiven(AppConstants.PermissionConstants.REQ_CODE_CAMERA);

                } else if (isReadAllow) {
                    AppUtils.showAllowPermissionFromSettingDialog(mActivity, mActivity.getString(R.string.enable_storage_permission));
                } else if (isCameraAllow) {
                    AppUtils.showAllowPermissionFromSettingDialog(mActivity, mActivity.getString(R.string.enable_camera_permission));
                } else if (isWriteAllow) {
                    AppUtils.showAllowPermissionFromSettingDialog(mActivity, mActivity.getString(R.string.enable_storage_permission));
                }
                break;
        }
    }

    private boolean shouldShowRequest(String permission) {
        return !ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permission) &&
                ContextCompat.checkSelfPermission(mActivity, permission) != PackageManager.PERMISSION_GRANTED;
    }

    /**
     * This interface is used to get the user permission callback to the activity or fragment who
     * implements it
     */
    public interface IGetPermissionListener {

        void permissionGiven(int requestCode);

    }

}
