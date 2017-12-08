/*package com.example.hp.gep;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
/**
 * Created by HP on 20/11/2017.
 */
/*
public class BackUserLoc extends AsyncTask<String,Void,String> implements ConnectionCallbacks,
        OnConnectionFailedListener {
    final String TAG = userLoc.class.getSimpleName();

    final  int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    Location mLastLocation;

    // Google client to interact with Google API
    GoogleApiClient mGoogleApiClient;

    // boolean flag to toggle periodic location updates
    boolean mRequestingLocationUpdates = false;

    LocationRequest mLocationRequest;

    // Location updates intervals in sec
    int UPDATE_INTERVAL = 10000; // 10 sec
    int FATEST_INTERVAL = 5000; // 5 sec
    int DISPLACEMENT = 10; // 10 meters




    Context context;
    AlertDialog alertDialog;
    //constructor
    BackUserLoc(Context ctx){

        context=ctx;
    }


    @Override
    protected String doInBackground(String... strings) {
       // String email= params[0];


        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();
        }

        return null;
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(context);
        /*if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode,getApplicationContext(),
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(context,
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
            }
            return false;
        }*/
      /*  return true;

    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
        displayLocation();
    }

    private void displayLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mLastLocation = LocationServices.FusedLocationApi
                    .getLastLocation(mGoogleApiClient);

            if (mLastLocation != null) {
                double latitude = mLastLocation.getLatitude();
                double longitude = mLastLocation.getLongitude();



                Background background=new Background(context);
                String loc_lat = String.valueOf(latitude);
                String loc_long = String.valueOf(longitude);
                background.execute(loc_lat,loc_long);

            }




    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }


}
*/