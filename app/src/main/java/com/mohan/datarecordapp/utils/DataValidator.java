package com.mohan.datarecordapp.utils;

import android.text.TextUtils;

import com.mohan.datarecordapp.datamonitoring.GPSData;

public class DataValidator {

    public static final String DEFAULT_STRING = null;
    public static final int DEFAULT_INT = Integer.MIN_VALUE;
    public static final double DEFAULT_DOUBLE = Double.MIN_VALUE;
    public static final float DEFAULT_FLOAT = Float.MIN_VALUE;
    public static final short DEFAULT_SHORT = Short.MIN_VALUE;


    public static boolean isValidData(String data) {
        return !TextUtils.isEmpty(data);
    }

    public static boolean isValidData(int data) {
        if (data == DEFAULT_INT) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isValidData(double data) {
        if (data != DEFAULT_DOUBLE) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidData(float data) {
        if (data != DEFAULT_FLOAT) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidData(short data) {
        if (data != DEFAULT_SHORT) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isMandatoryDataAvailable(GPSData vehicleData) {
        if ((vehicleData.getLatitude() != 0)
                && (vehicleData.getLongitude() != 0)) {
            return true;
        } else {
            return false;
        }
    }
}
