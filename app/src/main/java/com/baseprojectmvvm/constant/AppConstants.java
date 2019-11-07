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

    public class DateTimeConstants{
            public static final String DATE = "yyyy-MM-dd";
            public static final String DATE_FULL = "EEEE, d MMMM yyyy";
            public static final String DATE_SIMPLE = "d MMMM yyyy";
            public static final String WEEKDAY = "EEEE";
            public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
            public static final String TWEL_HOUR_FORMAT_TIME = "hh:mm:ss";
            public static final String TWENTY_FOUR_HOUR_FORMAT_TIME = "HH:mm:ss";
            public static final String TWENTY_FOUR_HOUR_FORMAT_TIME_AM = "hh:mm a";
            //Todo:change this into hh:mm
            public static final String HOUR_FORMAT_TIME = "hh:mm:ss";
            public static final String HOUR_MINUTE_TIME = "hh:mm";
            public static final String SERVER_TO = "dd-MM-yyyy";
            public static final String AM_PM = "a";
            public static final String DATE_FORMAT = "dd-MM-yyyy";
    }
}
