package com.visualnavigate;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v13.app.ActivityCompat;
import android.util.Log;

public class GetLocationService extends Service implements LocationListener {

    LocationManager locationManager;
    String mprovider;

    public GetLocationService() {
    }

    @Override
    public void onCreate() {
        locationManager = (LocationManager) getSystemService(this.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        mprovider = locationManager.getBestProvider(criteria, false);

        if (mprovider != null && !mprovider.equals("")) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.e("location", "missing location permission");
                return;
            }
            Location location = locationManager.getLastKnownLocation(mprovider);
            locationManager.requestLocationUpdates(mprovider, 1500, 1, this);

            if (location != null) {
                onLocationChanged(location);

                httpHelper.logRequest("onCreate-getProvider", location.getProvider());
                httpHelper.logRequest("onCreate-accuracy", String.valueOf(location.getAccuracy()));
            }


            //else
            //    Toast.makeText(getBaseContext(), "No Location Provider Found Check Your Code", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onLocationChanged(Location location) {

        httpHelper.logRequest("onLocationChanged-getProvider",  location.getProvider());
        httpHelper.logRequest("onLocationChanged-accuracy",  String.valueOf(location.getAccuracy()));

        Global.locationProvider = location.getProvider();
        Global.accuracy = location.getAccuracy();
        Global.lastLatitude = location.getLatitude();
        Global.lastLongitude = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
