package com.mohan.datarecordapp;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import android.location.Location;

import com.mohan.datarecordapp.data.GPSLocationData;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class GPSLocationDataTest {
    GPSLocationData mGPSLocationData;
    private Location mMockLocation;

    @Before
    public void setup() {
        mMockLocation = Mockito.mock(Location.class);
        when(mMockLocation.getAccuracy()).thenReturn(10f);
        when(mMockLocation.getBearing()).thenReturn(12f);
        when(mMockLocation.getAltitude()).thenReturn(13.0);
        when(mMockLocation.getLatitude()).thenReturn(13.3232);
        when(mMockLocation.getLongitude()).thenReturn(22.22222);
    }

    @Test
    public void verify_accuracy() {
        //Act
        mGPSLocationData = new GPSLocationData(mMockLocation);

        //Assert
        assertEquals(10f, (float) mGPSLocationData.getAccuracy(), 0f);
    }

    @Test
    public void verify_bearing() {
        //Act
        mGPSLocationData = new GPSLocationData(mMockLocation);

        //Assert
        assertEquals(12f, (float) mGPSLocationData.getBearing(), 0f);
    }

    @Test
    public void verify_altitude() {
        //Act
        mGPSLocationData = new GPSLocationData(mMockLocation);

        //Assert
        assertEquals(13.0, mGPSLocationData.getAltitude(), 0);
    }

    @Test
    public void verify_latitude() {
        //Act
        mGPSLocationData = new GPSLocationData(mMockLocation);

        //Assert
        assertEquals(13.3232, mGPSLocationData.getLatitude(), 0);
    }

    @Test
    public void verify_longitude() {
        //Act
        mGPSLocationData = new GPSLocationData(mMockLocation);

        //Assert
        assertEquals(22.22222, mGPSLocationData.getLongitude(), 0);
    }

}
