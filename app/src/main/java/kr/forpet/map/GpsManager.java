package kr.forpet.map;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;

public class GpsManager {
    private static GpsManager sGpsManager;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLocation;

    private GpsManager(Context context) {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        mLocation = new Location("");
        mLocation.setLatitude(37.557399);
        mLocation.setLongitude(126.924564);
    }

    public static GpsManager getInstance(Context context) {
        if (sGpsManager == null) {
            sGpsManager = new GpsManager(context);
        }

        return sGpsManager;
    }

    public void update(OnCompleteListener onCompleteListener) throws SecurityException {
        mFusedLocationProviderClient.getLastLocation().addOnCompleteListener((@NonNull Task<Location> task) -> {
            if (task.isSuccessful()) {
                mLocation = task.getResult();
                onCompleteListener.onComplete(task.getResult());
            } else {
                Log.d("GooglePlayServices", "Current location is null. Using defaults.");
                Log.e("GooglePlayServices", "Exception: %s", task.getException());
            }
        });
    }

    public Location getLocation() {
        return mLocation;
    }

    public interface OnCompleteListener {
        void onComplete(Location location);
    }
}
