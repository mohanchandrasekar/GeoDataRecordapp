package com.mohan.datarecordapp.datamonitoring;

import android.util.Log;

import com.mohan.datarecordapp.DataStoreApplication;
import com.mohan.datarecordapp.utils.DataValidator;

import java.io.IOException;

/**
 * DataMonitorEventCatcher class for catching the event and trigger the event to colelct the data
 */
public class DataMonitorEventCatcher {

    private static final String TAG = "DataMonitorEventCatcher";
    private DataProcessor mDataProcessor;
    private DataTrigger mDataTrigger = new DataTrigger();

    public DataMonitorEventCatcher() {
        mDataProcessor = new DataProcessor();
    }

    /**
     * when a user start click, then event will start looking the for initilzation and start for
     * event
     */
    public void onStartEvent() {
        Log.d(TAG, "onStartEvent");
        DataStoreApplication.dataMonitoringCanStart();
    }

    /**
     * Store the data in csv file when stop button click
     */
    public void onStopEvent() throws IOException {
        Log.d(TAG, "onStopEvent");
        saveMonitoredDataIfPossible();

        DataStoreApplication.getDataMonitorEventCatcher().disableAllTriggers();
    }

    /**
     * Trigger when GPSChanged
     */
    public void onGPSChanged() {
        Log.d(TAG, "onGPSChanged");

    }

    /**
     * periodically trigger every 6 sec
     * @throws IOException
     */
    public void onPeriodicUpdate() throws IOException {
        Log.d(TAG, "onPeriodicUpdate");
        saveMonitoredDataIfPossible();
    }

    /**
     * activate the trigger to collect the data
     */
    public void activateAllTriggers() {
        Log.d(TAG, "activateAllTriggers");
        mDataTrigger.onActivateMonitoring();
    }

    /**
     * disable the trigger to stop saving the data
     */
    public void disableAllTriggers() {
        mDataTrigger.onStopMonitoring();
    }

    /**
     * save the data if trigger happened
     * @throws IOException
     */
    public void saveMonitoredDataIfPossible() throws IOException {
        Log.d(TAG, "saveMonitoredDataToDataBaseIfValidated");
        GPSData gpsData = DataFactory.buildData();
        if (DataValidator.isMandatoryDataAvailable(gpsData)) {
            mDataProcessor.saveDataAndSendToFile(gpsData);
        }
    }
}
