package com.mohan.datarecordapp.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import com.mohan.datarecordapp.DataStoreApplication;

import java.util.List;

public class Utils {
    public static final String ACTION_PERIODIC_UPDATE = "com.mohan.data.action.periodic_update";
    public static final int DATA_SENDING_FREQUENCY = 6000;
    private static final String TAG = "Utils";

    public static boolean isServiceRunning(String serviceClassName) {
        final ActivityManager activityManager =
                (ActivityManager) DataStoreApplication.getApplicationInstance().getSystemService(
                        Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningServiceInfo> services =
                activityManager.getRunningServices(Integer.MAX_VALUE);

        for (ActivityManager.RunningServiceInfo runningServiceInfo : services) {
            if (runningServiceInfo.service.getClassName().equals(serviceClassName)) {
                Log.d(TAG, "Service is Running" + serviceClassName);
                return true;
            }
        }
        return false;
    }
}
