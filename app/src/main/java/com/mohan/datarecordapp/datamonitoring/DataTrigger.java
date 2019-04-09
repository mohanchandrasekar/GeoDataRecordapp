package com.mohan.datarecordapp.datamonitoring;


import static com.mohan.datarecordapp.utils.Utils.DATA_SENDING_FREQUENCY;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.mohan.datarecordapp.DataStoreApplication;
import com.mohan.datarecordapp.receiver.PeriodicUpdateReceiver;
import com.mohan.datarecordapp.utils.Utils;

/**
 * DataTrigger is to activate the periodic trigger
 */
public class DataTrigger {

    private AlarmManager alarmManager;

    public void onActivateMonitoring() {
        startTimer();
    }

    public void onStopMonitoring() {
        stopTimer();
    }

    public void resetMonitoring() {
        resetTimer();
    }

    private void startTimer() {
        alarmManager =
                (AlarmManager) DataStoreApplication.getApplicationInstance().getSystemService(
                        Context.ALARM_SERVICE);
        resetTimer();
    }

    private void resetTimer() {
        if (alarmManager == null) {
            startTimer();
            return;
        }
        Intent intent = new Intent(DataStoreApplication.getApplicationInstance(),
                PeriodicUpdateReceiver.class);
        intent.setAction(Utils.ACTION_PERIODIC_UPDATE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                DataStoreApplication.getApplicationInstance(), 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + DATA_SENDING_FREQUENCY,
                DATA_SENDING_FREQUENCY, pendingIntent);
    }

    private void stopTimer() {
        Intent intent = new Intent(DataStoreApplication.getApplicationInstance(),
                PeriodicUpdateReceiver.class);
        intent.setAction(Utils.ACTION_PERIODIC_UPDATE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                DataStoreApplication.getApplicationInstance(), 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }

}
