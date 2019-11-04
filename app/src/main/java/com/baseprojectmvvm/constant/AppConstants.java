package com.baseprojectmvvm.constant;


import com.baseprojectmvvm.BuildConfig;

public class AppConstants {

    public static final String BASE_URL = BuildConfig.API_BASE_URL;


    public class NetworkingConstants {
        public static final int EMPTY_DATA_ERROR_CODE = 451;
        public static final int INTERNAL_SERVER_ERROR_CODE = 500;
        public static final int NO_INTERNET_CONNECTION = 9;
        public static final int ACCOUNT_BLOCKED_CODE = 403;
        public static final int UNAUTHORIZED = 401;
    }

    public class PermissionConstants {
        public static final int REQ_CODE_GALLERY = 100;
        public static final int REQ_CODE_CAMERA = 101;
        public static final int REQ_CODE_CAMERA_VIDEO = 102;
    }

    public class PreferenceConstants {
        public static final String REFRESH_TOKEN = "refresh_token";
        public static final String ACCESS_TOKEN = "access_token";
        public static final String USER_DETAILS = "user_detail";
        public static final String DEVICE_TOKEN = "device_token";
        public static final String USER_ID = "user_id";
    }

}
