package com.mohan.datarecordapp.datamonitoring;

import android.os.Parcel;
import android.os.Parcelable;

import com.mohan.datarecordapp.utils.DataValidator;

public class GPSData implements Parcelable {
    public static final Creator<GPSData> CREATOR = new Creator<GPSData>() {
        @Override
        public GPSData createFromParcel(Parcel in) {
            return new GPSData(in);
        }

        @Override
        public GPSData[] newArray(int size) {
            return new GPSData[size];
        }
    };
    private static final String TAG = "GPSData";
    private float mXaxis;
    private float mYaxis;
    private float mZaxis;
    private String mTimeStamp;
    private int mAccuracy;
    private int mHeading;
    private double mLatitude;
    private double mLongitude;
    private int mSpeed;
    private double mAltitude;

    GPSData(float xAxis, float yAxis, float zAxis, String timeStamp, int accuracy,
            int heading, double latitude, int speed, double altitude, double longitude) {
        mXaxis = xAxis;
        mYaxis = yAxis;
        mZaxis = zAxis;
        mTimeStamp = timeStamp;
        mAccuracy = accuracy;
        mHeading = heading;
        mLatitude = latitude;
        mSpeed = speed;
        mAltitude = altitude;
        mLongitude = longitude;
    }

    private GPSData(Parcel in) {
        mXaxis = in.readFloat();
        mYaxis = in.readFloat();
        mZaxis = in.readFloat();
        mTimeStamp = in.readString();
        mAccuracy = in.readInt();
        mHeading = in.readInt();
        mLatitude = in.readDouble();
        mLongitude = in.readDouble();
        mSpeed = in.readInt();
        mAltitude = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(mXaxis);
        dest.writeFloat(mYaxis);
        dest.writeFloat(mZaxis);
        dest.writeString(mTimeStamp);
        dest.writeInt(mAccuracy);
        dest.writeInt(mHeading);
        dest.writeDouble(mLatitude);
        dest.writeDouble(mLongitude);
        dest.writeInt(mSpeed);
        dest.writeDouble(mAltitude);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("Data (");
        if (DataValidator.isValidData(mTimeStamp)) {
            sb.append("time=");
            sb.append(mTimeStamp);
        }
        if (DataValidator.isValidData(mLatitude)) {
            sb.append("; lat=");
            sb.append(mLatitude);
        }
        if (DataValidator.isValidData(mLongitude)) {
            sb.append("; lon=");
            sb.append(mLongitude);
        }
        if (DataValidator.isValidData(mAccuracy)) {
            sb.append("; acc=");
            sb.append(mAccuracy);
        }
        if (DataValidator.isValidData(mHeading)) {
            sb.append("; hea=");
            sb.append(mHeading);
        }
        if (DataValidator.isValidData(mSpeed)) {
            sb.append("; speed=");
            sb.append(mSpeed);
        }
        if (DataValidator.isValidData(mAltitude)) {
            sb.append("; altitude=");
            sb.append(mAltitude);
        }
        if (DataValidator.isValidData(mXaxis)) {
            sb.append("; xAxis=");
            sb.append(mXaxis);
        }
        if (DataValidator.isValidData(mYaxis)) {
            sb.append("; yAxis=");
            sb.append(mYaxis);
        }
        if (DataValidator.isValidData(mZaxis)) {
            sb.append("; zAxis=");
            sb.append(mZaxis);
        }
        sb.append(")");
        return sb.toString();
    }

    public float getXaxis() {
        return mXaxis;
    }

    public float getYaxis() {
        return mYaxis;
    }

    public float getZaxis() {
        return mZaxis;
    }

    public String getTimeStamp() {
        return mTimeStamp;
    }

    public int getAccuracy() {
        return mAccuracy;
    }

    public int getHeading() {
        return mHeading;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public int getSpeed() {
        return mSpeed;
    }

    public double getAltitude() {
        return mAltitude;
    }
}
