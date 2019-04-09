package com.mohan.datarecordapp.data;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.mohan.datarecordapp.DataStoreApplication;

/**
 * AccelerometerData is to collect information about x ,y z axis and accelerator
 */
public class AccelerometerData implements SensorEventListener {
    private static final String TAG = "AccelerometerData";
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private float mXAxis = 0;
    private float mYAxis = 0;
    private float mZaxis = 0;

    public AccelerometerData() {
        mSensorManager =
                (SensorManager) DataStoreApplication.getApplicationInstance().getSystemService(
                        Context.SENSOR_SERVICE);

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {

            mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mSensorManager.registerListener(this, mAccelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        mXAxis = (int) sensorEvent.values[0];
        mYAxis = (int) sensorEvent.values[1];
        mZaxis = (int) sensorEvent.values[2];
        Log.d(TAG, "Accelerrometer :" + mXAxis + " Y Axis :" + mYAxis + "Z Axis: " + mZaxis);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * register the sensor listener to get the accelerator value like x axis , y axis and z axis
     */
    public void registerSensorLister() {
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * Unregister the sensor listener
     */
    public void unRegisterListener() {
        mSensorManager.unregisterListener(this);
    }

    /**
     * Accelerator value
     *
     * @return the xAxis
     */
    public float getXAxis() {
        return mXAxis;
    }

    /**
     * Accelerator value
     *
     * @return the yAxis
     */
    public float getYAxis() {
        return mYAxis;
    }

    /**
     * Accelerator value
     *
     * @return the zAxis
     */
    public float getZaxis() {
        return mZaxis;
    }
}
