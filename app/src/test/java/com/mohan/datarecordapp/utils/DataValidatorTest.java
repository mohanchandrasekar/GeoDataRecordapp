package com.mohan.datarecordapp.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.mohan.datarecordapp.datamonitoring.GPSData;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class DataValidatorTest {
    DataValidator mDataValidator;

    @Before
    public void setup() {
        mDataValidator = new DataValidator();
    }

    @Test
    public void verify_validator() {
        //Act
        boolean validInt = DataValidator.isValidData(11);
        boolean validFloat = DataValidator.isValidData(11.2f);
        boolean validDouble = DataValidator.isValidData(11.0);
        boolean validString = DataValidator.isValidData("test");

        //Aseert
        assertTrue(validInt);
        assertTrue(validFloat);
        assertTrue(validDouble);
        assertTrue(validString);
    }

    @Test
    public void verify_mandatory_data() {
        //Act
        GPSData gpsData = new GPSData(0f, 0f, 0f, "", 1, 2, 12.0121, 1, 0.0, 21.020202);
        boolean mandatoryDataAvailable = DataValidator.isMandatoryDataAvailable(gpsData);

        //Assert
        assertTrue(mandatoryDataAvailable);
    }

    @Test
    public void verify_invalidateData() {
        //Act
        boolean validInt = DataValidator.isValidData(Integer.MIN_VALUE);
        boolean validFloat = DataValidator.isValidData(Float.MIN_VALUE);
        boolean validDouble = DataValidator.isValidData(Double.MIN_VALUE);
        boolean validString = DataValidator.isValidData(null);

        //Assert
        assertFalse(validInt);
        assertFalse(validFloat);
        assertFalse(validDouble);
        assertFalse(validString);
    }

    @Test
    public void verify_is_not_mandatory_data() {
        //Act
        GPSData gpsData = new GPSData(0f, 0f, 0f, "", 1, 2, 0.0, 1, 0.0, 0.0);
        boolean mandatoryDataAvailable = DataValidator.isMandatoryDataAvailable(gpsData);

        //Assert
        assertFalse(mandatoryDataAvailable);
    }
}