package com.baseprojectmvvm.constant;


public interface AppConstants {

    interface UiValidationConstants {
        int EMAIL_EMPTY = 101;
        int PASSWORD_EMPTY = 102;
        int INVALID_PASSWORD = 103;
        int INVALID_EMAIL = 104;
        int NAME_EMPTY = 105;
        int INVALID_NAME = 106;
        int PHONE_EMPTY = 107;
        int INVALID_PHONE = 108;
    }

    interface NetworkingConstants {
        int EMPTY_DATA_ERROR_CODE = 451;
        int ACCOUNT_BLOCKED_CODE = 403;
        int UNAUTHORIZED = 401;
    }

    interface PermissionConstants {
        int REQ_CODE_GALLERY = 100;
        int REQ_CODE_CAMERA = 101;
        int REQ_CODE_CAMERA_GALLERY = 102;
    }

    interface PreferenceConstants {
        String REFRESH_TOKEN = "refresh_token";
        String ACCESS_TOKEN = "access_token";
        String USER_DETAILS = "user_detail";
        String DEVICE_TOKEN = "device_token";
        String DEVICE_ID = "device_id";
        String USER_ID = "user_id";
        String USER_NAME = "user_name";
    }

    interface SplashConstants {
        String OPEN_ON_BOARDING_SCREEN = "open_on_boarding_screen";
        String OPEN_HOME_SCREEN = "open_home_screen";
    }

    interface BundleConstants {
        String FROM_WHICH_FRAGMENT = "from_which_fragment";
        String DATA = "data";
    }

    interface DateTimeConstants {
        String DATE = "yyyy-MM-dd";
        String DATE_FULL = "EEEE, d MMMM yyyy";
        String DATE_SIMPLE = "d MMMM yyyy";
        String WEEKDAY = "EEEE";
        String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
        String TWEL_HOUR_FORMAT_TIME = "hh:mm:ss";
        String TWENTY_FOUR_HOUR_FORMAT_TIME = "HH:mm:ss";
        String TWENTY_FOUR_HOUR_FORMAT_TIME_AM = "hh:mm a";
        String HOUR_FORMAT_TIME = "hh:mm:ss";
        String HOUR_MINUTE_TIME = "hh:mm";
        String SERVER_TO = "dd-MM-yyyy";
        String AM_PM = "a";
        String DATE_FORMAT = "dd-MM-yyyy";
    }
}
