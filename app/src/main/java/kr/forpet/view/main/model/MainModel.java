package kr.forpet.view.main.model;

import android.content.Context;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;

public class MainModel {

    private FusedLocationProviderClient mFusedLocationProviderClient;

    public void initGooglePlayService(Context context) {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
    }

    public Task getMyLocation() throws SecurityException {
        try {
            return mFusedLocationProviderClient.getLastLocation();
        } catch (SecurityException e) {
            throw e;
        }
    }
}
