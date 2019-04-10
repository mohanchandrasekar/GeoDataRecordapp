package com.mohan.datarecordapp;

import android.app.Application;
import android.util.Log;

import com.mohan.datarecordapp.datamonitoring.DataMonitorEventCatcher;

public class DataStoreApplication extends Application {

    private static final String TAG = "DataStoreApplication";

    private static Application mApplicationInstance;
    private static DataMonitorEventCatcher mDataMonitorEventCatcher;

    public static DataMonitorEventCatcher getDataMonitorEventCatcher() {
        return mDataMonitorEventCatcher;
    }

    public static Application getApplicationInstance() {
        return mApplicationInstance;
    }

    public static void dataMonitoringCanStart() {
        if (mDataMonitorEventCatcher == null) {
            mDataMonitorEventCatcher = new DataMonitorEventCatcher();
        }
        mDataMonitorEventCatcher.activateAllTriggers();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationInstance = this;
        Log.d(TAG, "mApplicationInstance : " + mApplicationInstance);
    }
}


