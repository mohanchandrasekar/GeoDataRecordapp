package com.mohan.datarecordapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mohan.datarecordapp.DataStoreApplication;

import java.io.IOException;

public class PeriodicUpdateReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			DataStoreApplication.getDataMonitorEventCatcher().onPeriodicUpdate();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
