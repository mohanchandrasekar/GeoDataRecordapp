package com.mohan.datarecordapp.datamonitoring;

import android.location.Location;
import android.util.Log;

import com.mohan.datarecordapp.data.AccelerometerData;
import com.mohan.datarecordapp.data.GPSLocationData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * DataFactory is a Generic class for collect all the necessary data for ready to save in file
 */
public class DataFactory {
    private static final SimpleDateFormat SIMPLE_DATE_TIME_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());

    private static GPSLocationData mGPSLocationData;
    private static AccelerometerData mAccelerometerData = new AccelerometerData();;

    public DataFactory(Location location) {
        mGPSLocationData = new GPSLocationData(location);
        Log.e("Mohan", "DataFactory : " + location.getLatitude() + "Longitude"
                + location.getLongitude());
    }

    static GPSData buildData() {
        float xAxis = mAccelerometerData.getXAxis();
        float yAxis = mAccelerometerData.getYAxis();
        float zAxis = mAccelerometerData.getZaxis();
        String timeStamp = retrieveSystemTimestamp();
        int accuracy = mGPSLocationData.getAccuracy();
        int heading = mGPSLocationData.getBearing();
        double latitude = mGPSLocationData.getLatitude();
        double longitude = mGPSLocationData.getLongitude();
        int speed = mGPSLocationData.getSpeed();
        double altitude = mGPSLocationData.getAltitude();

        return new GPSData(xAxis, yAxis, zAxis, timeStamp, accuracy, heading, latitude, speed,
                altitude, longitude);
    }

    private static String retrieveSystemTimestamp() {
        return SIMPLE_DATE_TIME_FORMAT.format(new Date());
    }
}
