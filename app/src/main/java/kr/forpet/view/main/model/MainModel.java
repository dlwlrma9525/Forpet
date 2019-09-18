package kr.forpet.view.main.model;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.Task;

import java.util.List;

import kr.forpet.data.db.AppDatabase;
import kr.forpet.data.db.SQLiteHelper;
import kr.forpet.data.entity.Shop;

public class MainModel {

    private AppDatabase mAppDatabase;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    public void initGooglePlayService(Context context) {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
    }

    public void loadAppDatabase(Context context) {
        mAppDatabase = SQLiteHelper.getAppDatabase(context);
    }

    public Task<Location> getMyLocation() throws SecurityException {
        try {
            return mFusedLocationProviderClient.getLastLocation();
        } catch (SecurityException e) {
            throw e;
        }
    }

    public Shop getShop(String hashCode) {
        return mAppDatabase.shopDAO().getByHashCode(hashCode);
    }

    public List<Shop> getShopList(LatLngBounds bounds, String categoryGroupCode) {
        List<Shop> shopList = mAppDatabase.shopDAO().getByVisibleRegion(
                bounds.southwest.longitude,
                bounds.northeast.longitude,
                bounds.southwest.latitude,
                bounds.northeast.latitude,
                categoryGroupCode
        );

        for (Shop shop : shopList) {
            if (shop.getCategoryGroupCode().equals(Shop.CategoryGroupCode.PHARM.toString()))
                shop.setShopPharmacy(mAppDatabase.shopPharmacyDAO().getByHashCode(shop.getForpetHash()));

            shop.setShopOpenTimeList(mAppDatabase.shopOpenTimeDAO().getByHashCode(shop.getForpetHash()));
        }

        return shopList;
    }
}