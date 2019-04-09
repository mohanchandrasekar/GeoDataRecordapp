package com.mohan.datarecordapp.datamonitoring;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * DataProcessor is responsible for create a csv file and store gps data
 */
class DataProcessor {
    private static final String TAG = "DataPreprocessor";
    private static final String FILE_NAME = "gpsdata.csv";
    private FileWriter mFileWriter;

    /**
     * Writing gps data in csv file
     */
    void saveDataAndSendToFile(GPSData gpsData) throws IOException {
        createCsvFile(gpsData);
        Log.d(TAG, "GPS Data final : " + gpsData);
    }

    /**
     * Writing gps data in csv file
     *
     * @param gpsData which is contain the gps and accelerator data
     * @throws IOException throw if any exception while writing the file
     */
    private void createCsvFile(GPSData gpsData) throws IOException {
        try {
            File root = Environment.getExternalStorageDirectory();
            File file = new File(root, FILE_NAME);
            Log.e(TAG, "root : " + root);
            mFileWriter = new FileWriter(file, true);
            Log.e("Mohan", "File length: " + file.length());
            mFileWriter.append('\n');
            mFileWriter.append(gpsData.toString());

        } catch (IOException e) {
            Log.e(TAG, "Exception caught when writing " + e.getMessage());
        } finally {
            mFileWriter.flush();
            mFileWriter.close();
        }
    }
}
