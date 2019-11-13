package com.baseprojectmvvm;

import android.app.Application;
import android.content.Context;

import com.baseprojectmvvm.data.DataManager;

//@ReportsCrashes(mailTo = "kapil.sachan@appinventiv.com", mode = ReportingInteractionMode.TOAST, resToastText = R.string.crash_toast_text)
public class App extends Application {

    private static Context appContext;

    public static Context getAppContext() {
        return appContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base
        );

        // The following line triggers the initialization of ACRA
//        ACRA.init(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        DataManager dataManager = DataManager.init(appContext);
        dataManager.initApiManager();
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
//        FirebaseDatabase.getInstance().getReference().keepSynced(true);
    }
}
