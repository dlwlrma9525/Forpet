package kr.forpet.view.main.model;

import android.content.Context;

import androidx.room.Room;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.Task;

import java.util.List;

import kr.forpet.R;
import kr.forpet.data.db.AppDatabase;
import kr.forpet.data.entity.ForpetShop;

public class MainModel {

    private AppDatabase db;

    private FusedLocationProviderClient mFusedLocationProviderClient;

    public void initAppDatabase(Context context) {
        db = Room.databaseBuilder(context, AppDatabase.class, context.getString(R.string.db_name)).build();
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

    public List<ForpetShop> getForpetShopList() {
        return db.forpetShopDAO().getAll();
    }

    public List<ForpetShop> getForpetShopList(LatLngBounds bounds, String catCode) {
        return db.forpetShopDAO().getByBounds(
                bounds.southwest.longitude,
                bounds.northeast.longitude,
                bounds.southwest.latitude,
                bounds.northeast.latitude,
                catCode
        );
    }
}
