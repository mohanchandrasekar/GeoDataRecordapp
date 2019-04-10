package com.mohan.datarecordapp.data;

import android.location.Location;

/**
 * GPSLocationData class is for collect all possible location data.
 */
public class GPSLocationData {

    private static final String TAG = "GPSLocationData";
    private Location mLocation;

    public GPSLocationData(Location location) {
        mLocation = location;
    }

    /**
     * GPS Time
     *
     * @return return the gps time
     */
    public long getTime() {
        return mLocation == null ? 0 : mLocation.getTime();
    }

    /**
     * if location is null return the default double value for validation otherwise expected
     * value will return
     *
     * @return GPS latitude
     */
    public double getLatitude() {
        return mLocation == null ? Double.MIN_VALUE
                : mLocation.getLatitude();
    }

    /**
     * if location is null return the default double value for validation otherwise expected
     * value will return
     *
     * @return GPS longitude
     */
    public double getLongitude() {
        return mLocation == null ? Double.MIN_VALUE
                : mLocation.getLongitude();
    }

    /**
     * if location is null return the default double value for validation otherwise expected
     * value will return
     *
     * @return GPS accuracy
     */
    public int getAccuracy() {
        return mLocation == null ? Integer.MIN_VALUE
                : (int) mLocation.getAccuracy();
    }

    /**
     * if location is null return the default double value for validation otherwise expected
     * value will return
     *
     * @return GPS altitude
     */
    public double getAltitude() {
        return mLocation == null ? Integer.MIN_VALUE
                : (int) mLocation.getAltitude();
    }

    /**
     * if location is null return the default double value for validation otherwise expected
     * value will return
     *
     * @return GPS heading
     */
    public int getBearing() {
        return mLocation == null ? Integer.MIN_VALUE
                : (int) calculateBearingData();
    }

    /**
     * if location is null return the default double value for validation otherwise expected
     * value will return
     *
     * @return GPS speed
     */
    public int getSpeed() {
        return mLocation == null ? Integer.MIN_VALUE
                : (int) ((mLocation.getSpeed() * 3600) / 1000);
    }

    /**
     * calculation for location bearing data
     * excepeted calculation in future
     *
     * @return bearing data
     */
    private float calculateBearingData() {
        return mLocation.getBearing();
    }

}
