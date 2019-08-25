package kr.forpet.view.main.model;

import android.content.Context;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.Task;

import java.util.List;

import kr.forpet.data.db.AppDatabase;
import kr.forpet.data.db.SQLiteHelper;
import kr.forpet.data.entity.ForpetShop;

public class MainModel {

    private AppDatabase db;

    private FusedLocationProviderClient mFusedLocationProviderClient;

    public void initAppDatabase(Context context) {
        db = SQLiteHelper.assetsToDisk(context);
    }

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

    public List<ForpetShop> getForpetShopList(LatLngBounds bounds, String catCode) {
        return db.forpetShopDAO().getByVisibleRegion(
                bounds.southwest.longitude,
                bounds.northeast.longitude,
                bounds.southwest.latitude,
                bounds.northeast.latitude,
                catCode
        );
    }
}