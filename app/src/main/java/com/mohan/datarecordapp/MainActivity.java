package com.mohan.datarecordapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.mohan.datarecordapp.data.AccelerometerData;
import com.mohan.datarecordapp.data.GPSLocationData;
import com.mohan.datarecordapp.datamonitoring.DataFactory;
import com.mohan.datarecordapp.datamonitoring.DataMonitorEventCatcher;
import com.mohan.datarecordapp.service.LocationService;
import com.mohan.datarecordapp.utils.ServiceLifeCycle;
import com.mohan.datarecordapp.utils.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ServiceLifeCycle,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {
    private static final String TAG = "MainActivity";
    private static final int REQUEST_WRITE_STORAGE = 112;
    private AccelerometerData mAccelerometerData;
    private Button mStartButton;
    private Button mStopButton;
    private DataMonitorEventCatcher mDataMonitorEventCatcher;
    private RecyclerView mRecyclerView;
    private List<String[]> mResultDataList;
    private DataLoaderAsyncTask mDataLoaderAsyncTask;
    private DataAdapter mDataAdapter;
    private boolean isRunning = true;
    private boolean isStarted = false;
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager mLocationManager;

    private LocationRequest mLocationRequest;
    private com.google.android.gms.location.LocationListener listener;
    private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */

    @Override
    public void startService() {
        startService(new Intent(this, LocationService.class));
    }

    @Override
    public void stopService() {
        stopService(new Intent(this, LocationService.class));
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        startLocationUpdates();

        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLocation == null) {
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
    }

    @Override
    public void onLocationChanged(Location location) {
        new DataFactory(mLocation);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        enableGPS();
        initilizeView();
        actionUI();

        checkWritePermission();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAccelerometerData.unRegisterListener();
        mDataLoaderAsyncTask.cancel(true);
        stopService();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAccelerometerData.unRegisterListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAccelerometerData.registerSensorLister();
    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                REQUEST_WRITE_STORAGE);
    }

    private void initilizeView() {
        mStartButton = findViewById(R.id.start);
        mStopButton = findViewById(R.id.stop);
        mRecyclerView = findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(
                getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private String[] readFileData(String path) throws FileNotFoundException {
        String[] data = new String[0];
        File file = new File(path);
        if (file.exists()) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            try {
                String csvLine;
                while ((csvLine = br.readLine()) != null) {
                    data = csvLine.split(",");
                    Log.e(TAG, "Result: " + data);
                }
            } catch (IOException ex) {
                throw new RuntimeException("Error in reading CSV file: " + ex.getMessage());
            }
        } else {
            Toast.makeText(DataStoreApplication.getApplicationInstance(), "file not exists",
                    Toast.LENGTH_SHORT).show();
        }
        return data;
    }


    private void init() {
        new GPSLocationData(mLocation);
        mDataLoaderAsyncTask = new DataLoaderAsyncTask();
        mResultDataList = new ArrayList<>();
        if (!Utils.isServiceRunning(LocationService.class.getName())) {
            startService();
        }
        mDataMonitorEventCatcher = new DataMonitorEventCatcher();
        mAccelerometerData = new AccelerometerData();
    }

    private void checkWritePermission() {
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission to record denied");

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(
                        "Permission to access the SD-CARD is required for this app to Download "
                                + "PDF.")
                        .setTitle("Permission required");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        Log.i(TAG, "Clicked");
                        makeRequest();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            } else {
                makeRequest();
            }
        }

    }

    private void actionUI() {
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataMonitorEventCatcher.onStartEvent();
                isStarted = true;
            }
        });

        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mDataMonitorEventCatcher.saveMonitoredDataIfPossible();
                    DataStoreApplication.getDataMonitorEventCatcher().disableAllTriggers();

                    if (isRunning && isStarted) {
                        isStarted = false;
                        new DataLoaderAsyncTask().execute();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * initilize the locationManager to get the GPS Data with runtime permission
     */
    private void enableGPS() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        mLocation = getLastKnownLocation();
    }

    private Location getLastKnownLocation() {
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(
                LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
            }
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    private boolean checkLocation() {
        if (!isLocationEnabled()) {
            showAlert();
        }
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private class DataLoaderAsyncTask extends AsyncTask<String, String, String[]> {
        private String mPath;

        DataLoaderAsyncTask() {
        }

        @Override
        protected String[] doInBackground(String... strings) {
            String[] result = new String[0];
            try {
                result = readFileData(mPath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            File root = Environment.getExternalStorageDirectory();
            mPath = root + "/gpsdata.csv";
            isRunning = false;
        }

        @Override
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);
            mResultDataList.add(result);
            Collections.reverse(mResultDataList);
            if (mResultDataList.size() >= 200) {
                mResultDataList.clear();
            }
            isRunning = true;
            mDataAdapter = new DataAdapter(mResultDataList, getApplicationContext());
            mRecyclerView.setAdapter(mDataAdapter);
        }
    }
}

